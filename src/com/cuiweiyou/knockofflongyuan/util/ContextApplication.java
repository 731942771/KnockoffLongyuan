package com.cuiweiyou.knockofflongyuan.util;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class ContextApplication extends Application {
	
	public static Context context;
	
	/** 全局公共消息处理器 **/
	public static Handler hmatyHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Toast.makeText(context, msg.obj.toString(), 0).show();
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
	}

}
