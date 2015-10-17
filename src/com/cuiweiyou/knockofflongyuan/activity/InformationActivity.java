package com.cuiweiyou.knockofflongyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.back.IInformationListBack;
import com.cuiweiyou.knockofflongyuan.bean.InformationBean;
import com.cuiweiyou.knockofflongyuan.task.InformationAsyncTask;
import com.cuiweiyou.knockofflongyuan.util.ColSQLiteUtil;
import com.cuiweiyou.knockofflongyuan.util.ContextApplication;

/**
 * 资讯祥页
 * @author Administrator
 */
public class InformationActivity extends Activity implements IInformationListBack, OnTouchListener {
	
	/** 手指按下的x位置 **/
	private float rawXDown;
	/** 手指按下的y位置 **/
	private float rawYDown;
	/**
	 * 默认带滚动条，但如果父容器是ScrollView，wv的高度会完全展开，由SV去滚动
	 * SV不能直接处理onClick事件。通过onTouch事件ACTION_DOWN变相处理
	 */
	private WebView mWebView;
	/** 工具条显示状态 **/
	private boolean isShow = false;
	/** 本文是否收藏了 **/
	private boolean isCollectioned = false;
	/** 触发touch move事件的最小距离 **/
	private int touchSlop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information);
		
		String title = getIntent().getStringExtra("title");
		String infUrl = getIntent().getStringExtra("infUrl");

		((TextView) findViewById(R.id.tv_title_infaty)).setText(title);

		mWebView = (WebView) findViewById(R.id.wv_inf_infaty);
		//mWebView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
		//mWebView.getSettings().setJavaScriptEnabled(true);// 支持js
//		mWebView.getSettings().setUseWideViewPort(true);
//		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
		

		// 视图的各种特性的常量记录对象（UI中所使用到的标准常量）得到触发移动事件的最短距离
		int a;
		touchSlop = ViewConfiguration.get(ContextApplication.context).getScaledTouchSlop();
		
		new InformationAsyncTask(this).execute(infUrl);
	}

	
	@Override
	public void setIbList(final InformationBean ib) {
		
		findViewById(R.id.pb_isLoading_infaty).setVisibility(View.GONE);
		
		if(ib == null){
			findViewById(R.id.iv_warning_infaty).setVisibility(View.VISIBLE);
			Toast.makeText(ContextApplication.context, "本条资讯没有数据", 1).show();
			return;
		}

		((TextView)findViewById(R.id.tv_magazinename_infaty)).setText(ib.getMagazineName());
		((TextView)findViewById(R.id.tv_issue_infaty)).setText("第 " + ib.getIssue() + " 期");
		
		final String mimeType = "text/html"; 
		final String encoding = "utf-8";
		
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append(ib.getContent());
		sb.append("</html>");
		final String data = sb.toString();
		
		// 2.监听webview要打开地址->再次添加给webview
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 获取载入地址
//				mWebView.loadUrl(ib.getContent());
				
//				mWebView.loadData(URLEncoder.encode(data, encoding), mimeType, encoding);	// 乱码
				mWebView.loadDataWithBaseURL(null, data, mimeType, encoding, null); 

				return true;
			}
		});

		// 3.监听网页打开进度
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);

				// 加载完成进度
				if (newProgress == 100)
					findViewById(R.id.pb_isLoading_infaty).setVisibility(View.GONE);
			}

		});

		// 4.请求网络页面
//		mWebView.loadData(URLEncoder.encode(data, encoding), mimeType, encoding);
		mWebView.loadDataWithBaseURL(null, data, mimeType, encoding, null);
		
		// 通过ontouch中的me类型获取点击事件
		mWebView.setOnTouchListener(this);
		
		////////////////////////////////
		////////////////////////////////
		// 看看是不是已经收藏在数据库了
		ColSQLiteUtil util = new ColSQLiteUtil(ContextApplication.context, null, null, 1);
		if(util.query(ib.getTitleID())){
			isCollectioned = true;
			findViewById(R.id.iv_col_bar_infaty).setBackgroundResource(R.drawable.fav_selected);
		}
		
		// 返回
		findViewById(R.id.iv_back_bar_infaty).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				findViewById(R.id.ll_bar_infaty).setVisibility(View.GONE);
				finish();
			}
		});
		
		// 收藏
		findViewById(R.id.iv_col_bar_infaty).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				ColSQLiteUtil util = new ColSQLiteUtil(ContextApplication.context, null, null, 1);
				
				Intent i = new Intent();
				i.setAction("collection");	// 本频道是 c o l l e c t i o n ~
				
				i.putExtra("title", ib.getTitle());
				i.putExtra("titleid", ib.getTitleID());
				
				if(!isCollectioned){
					// 向数据库插入本收藏
					findViewById(R.id.iv_col_bar_infaty).setBackgroundResource(R.drawable.fav_selected);
					util.insert(ib.getTitle(), ib.getTitleID());
					isCollectioned = true;
					
					i.putExtra("do", "true");
					
					Toast.makeText(ContextApplication.context, "收藏成功", 0).show();
				} else {
					findViewById(R.id.iv_col_bar_infaty).setBackgroundResource(R.drawable.fav);
					util.delete(ib.getTitleID());
					isCollectioned = false;
					
					i.putExtra("do", "false");
					
					Toast.makeText(ContextApplication.context, "已经取消收藏", 0).show();
				}
				
				//sendBroadcast(i);
				sendStickyBroadcast(i);
			}
		});
	} // end setIbList()

	/**
	 * SV不能直接处理onClick事件。通过onTouch事件ACTION_DOWN变相处理
	 * WV也是
	 */
	@Override
	public boolean onTouch(View v, MotionEvent ev) {

		switch(ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				rawXDown = ev.getRawX();
				rawYDown = ev.getRawY();
			break;
			case MotionEvent.ACTION_UP:
				float rawXUP = ev.getRawX();
				float rawYUP = ev.getRawY();
				
				// 如果x轴上的滑动距离未触发move事件，交给系统处理去吧
				if(Math.abs(rawXUP - rawXDown) >= touchSlop )
					return false;
				// y轴
				if(Math.abs(rawYUP - rawYDown) >= touchSlop )
					return false;
				
				if(!isShow){
					findViewById(R.id.ll_bar_infaty).setVisibility(View.VISIBLE);
					isShow = true;
				} else {
					findViewById(R.id.ll_bar_infaty).setVisibility(View.GONE);
					isShow = false;
				}
			break;
		}

		return true;
	}

}
