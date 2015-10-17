package com.cuiweiyou.knockofflongyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cuiweiyou.knockofflongyuan.R;

/**
 * 运行应用后进入的第一个Activity<br/>
 * 一般用于Logo/广告展示、网络状态判断/小数据请求、版本更新判断
 * @author Administrator
 */
public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		// 可以在主UI中延时
		// 延时1秒后，执行run方法，根据Intent跳转界面
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
				finish();
			}
		}, 3000);
	}

}
