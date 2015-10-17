package com.cuiweiyou.knockofflongyuan.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

/**
 * 网络状态判断工具<br/>
 * &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;
 * @author Administrator
 */
public class IntentStateUtil {

	public String getNetState(){
		// 连接管理器-连接状态服务
		ConnectivityManager manager = (ConnectivityManager) ContextApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		// Wifi网络信息对象
		NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		// 状态对象
		State state = info.getState();
		if(state != null){
			if(state.equals(State.CONNECTED)){
				//wifi可用
				return "wifi";
			}
		}
		
		// 移动网络
		if(manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState().equals(State.CONNECTED)){
			return "mobile";
		}
		
		return null;
	}

}
