package com.cuiweiyou.knockofflongyuan.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;

import com.cuiweiyou.knockofflongyuan.back.IImageBack;

/**
 * 远程图片加载子线程
 * @author Administrator
 */
public class ImgLoadUtil {
	
	/** 最多同时启动5个子线程 **/
	int max_threads = 5;
	/** 线程池 **/
	ExecutorService pool_threads;
	/** 最大占用内存 **/
	int max_monory = (int) (Runtime.getRuntime().maxMemory() / 1024);
	/** 内存缓存区 **/
	LruCache<String, Bitmap> lru = new LruCache<String, Bitmap>(max_monory) {
		protected int sizeOf(String key, Bitmap value) {
			return value.getByteCount();
		};
	};

	/**
	 * 加载远程图片<br/>
	 * 第一级：内存<br/>
	 * 第二级：SD卡<br/>
	 * 第三级：网络
	 * @param context			上下文
	 * @param imgurl			图片名称
	 * @param back				回调器
	 * @param flag				确定是哪个界面/控件请求图片
	 * @return Bitmap图片数据
	 */
	public void downloadImg(final Context context, final String imgurl, final IImageBack back, final String from) {

		if (imgurl == null)	// 判断图片名称是否有效
			return;
		
		/** 第一级，从内存缓存提取图片 **/
		Bitmap bitmap = lru.get(imgurl);
		if (bitmap != null){
			back.setImage(bitmap, imgurl);
			return;
		}

		/** 第2级，从SD卡提取图片 **/
		Bitmap bmp = new FileUtil().getCacheBitmap(context, imgurl);
		if(bmp != null){
			back.setImage(bmp, imgurl);
			return;
		}
		
		/** 这是个很重要的 **/
		final Handler hand = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				Bitmap bitmapxx = (Bitmap) msg.obj;
				String url = msg.getData().getString("url");
				
				back.setImage(bitmapxx, url);
			}
		};
		
		/** 第3级，从网络下载图片，返回的同时保存到SD卡和内存缓存 **/
		pool_threads = Executors.newFixedThreadPool(max_threads);	// 初始化线程池
		Thread thread_download = new Thread() {
			@Override
			public void run() {
				
				try {
					HttpURLConnection conn;
					
					if("journal".equals(from)){
						int last = imgurl.lastIndexOf("/");				// 最后一个 / 的索引
						String root = imgurl.substring(0, last); 		// 没有中文的一段
						String st = imgurl.substring(last); 			// 后面的有中文的图片名
						String u_path = null;
						try {
							String name = URLEncoder.encode(st, "utf-8"); // URL编码，解决中文问题
							u_path = root + name;
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						
						conn = (HttpURLConnection) new URL(u_path).openConnection();
					} else {
						conn = (HttpURLConnection) new URL(imgurl).openConnection();
					}
					
					// 远程地址前缀+图片名
					conn.setConnectTimeout(1000 * 60);
					conn.setReadTimeout(1000 * 50);
					conn.setRequestMethod("GET");
					conn.setDoInput(true);
					conn.connect();

					//
					 //* 4.0之后在主线程里面执行Http请求会报错：<br/>
					 //* E/AndroidRuntime(9011):
					 //* android.os.NetworkOnMainThreadException<br/>
					 //* 因此，为了避免此错误，同时避免界面图片加载的卡顿，应该在子线程中执行。
					 //
					if (conn.getResponseCode() == 200) {
						
						InputStream is = conn.getInputStream();

						Options op = new Options();
						op.inDensity = 2;	// 压缩

						Bitmap urlBmp = BitmapFactory.decodeStream(is, null, op);
						
						// load-1.放入缓存
						if(urlBmp != null){
							
							// 内存缓存
							lru.put(imgurl, urlBmp);
							
							// SD卡缓存
							new FileUtil().setCacheBitmap(context, urlBmp, imgurl);

							// load-1.回调发回
							//back.setImage(urlBmp, imgurl);
							/** 这个很重要 **/
							Message msg = hand.obtainMessage();
							
							Bundle bund = new Bundle();
							bund.putString("url",imgurl);
							
							msg.setData(bund);
							msg.obj = urlBmp;
							
							hand.sendMessage(msg);
						}
					} else {
						return;
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		pool_threads.execute(thread_download);
	}
}
