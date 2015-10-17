package com.cuiweiyou.knockofflongyuan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Message;

/**
 * 网络请求工具类<br/>
 * 注意注册权限 INTERNET<br/>
 * 网络请求是延时操作，在AsyncTask中调用执行
 * @author Administrator
 */
public class HttpRequestUtil {
	
	/**
	 * 执行网络请求
	 * @param url 地址
	 * @return json数据字串
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public String requestJson(String url) throws IOException{
		
		if(new IntentStateUtil().getNetState() != null){
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(60 * 1000);
			conn.setReadTimeout(60 * 1000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			
			if(conn.getResponseCode() == 200){
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String buffer;
				StringBuilder sb = new StringBuilder();
				while((buffer = br.readLine()) != null){
					sb.append(buffer);
				}
				
				br.close();
				
				new FileUtil().setCacheJson(sb.toString(), url);
				
				return sb.toString();
			} 
		}else {
			// RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
			//Toast.makeText(ContextApplication.context, "请求网络数据失败，使用本地数据", 0).show();
			
			Message msg = ContextApplication.hmatyHandler.obtainMessage();
			msg.obj = "请求网络数据失败，使用本地数据";
			ContextApplication.hmatyHandler.sendMessage(msg);
			
			return  new FileUtil().getCacheJson(url);
		}
		
		return null;
	}
}
