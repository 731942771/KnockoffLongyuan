package com.cuiweiyou.knockofflongyuan.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.activity.InformationActivity;
import com.cuiweiyou.knockofflongyuan.adapter.LVCollectionAdapter;
import com.cuiweiyou.knockofflongyuan.back.ICollectionListBack;
import com.cuiweiyou.knockofflongyuan.bean.CollectionBean;
import com.cuiweiyou.knockofflongyuan.model.UrlModel;
import com.cuiweiyou.knockofflongyuan.task.CollectionAsyncTask;

/**
 * 收藏fmg，使用本地数据
 * @author Administrator
 */
public class CollectionFragment extends Fragment implements ICollectionListBack {

	/** fmg布局 **/
	private View mView;
	/** 收藏列表 **/
	private ListView mLV;
	/** 收藏条目集合 **/
	private List<CollectionBean> list;
	/** 列表适配器 **/
	private LVCollectionAdapter adapter;
	/** 条目清空消息处理 **/
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(1 == msg.what)
				mView.findViewById(R.id.iv_warning_colfmg).setVisibility(View.VISIBLE);
		};
	};
	/** 收音机。接收从InfAty发来的收藏广播信息 **/
	private BroadcastReceiver br = new BroadcastReceiver() {

		/**
		 * 如果系统一直运行中，多次运行此应用：
		 * 本地打开此应用，首先会接收到上次应用运行时发出的延时粘滞广播
		 * 就算被接收过了，粘滞广播仍然一直驻留在系统里
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			
			String title = intent.getStringExtra("title");
			String titleid = intent.getStringExtra("titleid");
			
			if(title == null || title.length()<1)
				return;
			if(titleid == null || titleid.length()<1)
				return;
			
			/* 系统：原生5.1.1。 不重启的情况下，非第一次启动运行应用都会受到上次最后发出的广播内容
			 * 接收到广播一般早于异步数据回调
			 * 既然每次启动应用都这样，那么就根据adaper初始化情况操作：
			 * null说明回调尚未结束
			 * 非null说明回调已经结束
			 */
			if(adapter == null)	{// 适配器是回调之后初始化的
				list = new ArrayList<CollectionBean>();
				adapter = new LVCollectionAdapter(getActivity(), list);
				mLV.setAdapter(adapter);
				
				return;
			}
			
			mView.findViewById(R.id.iv_warning_colfmg).setVisibility(View.GONE);
			
			CollectionBean cb = new CollectionBean(titleid, title);
			
			if("true".equals(intent.getStringExtra("do"))){
				adapter.addItemAndNotifyChanged(cb);
			} else {
				adapter.delItemAndNotifyChanged(cb, handler);
			}
		}
	};
	
	/**
	 * 当Fmg关联Aty时
	 */
	@Override
	public void onAttach(Activity activity) {
		
		// 注册收音机
		IntentFilter ifr = new IntentFilter("collection");	// action：频道
		
		/** 一旦注册，br的onReceive就接收一次，不管其它aty有没有发广播
		 * 大概是应对粘滞广播 
		 */
		activity.registerReceiver(br, ifr);
		
		super.onAttach(activity);
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.view_collection_fmg, container, false);
		
		mLV = (ListView) mView.findViewById(R.id.lv_col_colfmg);
		mLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CollectionBean item = (CollectionBean) adapter.getItem(position);
				String title = item.getTitle();
				String titleID = item.getTitleID();
				String infUrl = UrlModel.informationUrl + "titleid=" + titleID + "&fromtype=2"; //&categoryid=9";

				// 进入正文Aty
				Intent intent=new Intent(getActivity(), InformationActivity.class);
				intent.putExtra("title", title);
				intent.putExtra("infUrl", infUrl);
				startActivity(intent);  
			}
		});
		
		new CollectionAsyncTask(this).execute();
		
		return mView;
	}

	/**
	 * asyncTask发回
	 */
	@Override
	public void setColsList(List<CollectionBean> colList) {
		mView.findViewById(R.id.pb_isLoading_colfmg).setVisibility(View.GONE);
		
		if(colList == null || colList.size() < 1){
			mView.findViewById(R.id.iv_warning_colfmg).setVisibility(View.VISIBLE);
			return;
		} 
		
		if(adapter != null){
			adapter.addItemAndNotifyChanged(colList);
		}
		else{
			adapter = new LVCollectionAdapter(getActivity(), colList);
			mLV.setAdapter(adapter);
		}
	}
	
	/**
	 * 和Aty的生命周期绑定的
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		// 拆除收音机
		getActivity().unregisterReceiver(br);
	}

}
