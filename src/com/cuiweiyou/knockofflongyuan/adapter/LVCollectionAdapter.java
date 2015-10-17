package com.cuiweiyou.knockofflongyuan.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.bean.CollectionBean;

/**
 * 收藏条目LIV适配器
 * @author Administrator
 */
public class LVCollectionAdapter extends BaseAdapter {

	/** 上下文 **/
	private Context context;
	/** 条目对象集合 **/
	private List<CollectionBean> list;

	/**
	 * 收藏条目LIV适配器
	 * @param context 上下文
	 * @param list 条目对象集合
	 * @author Administrator
	 */
	public LVCollectionAdapter(Context context, List<CollectionBean> list){
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list != null && list.size() > 0 ? list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ItemLVCollectionHolderView mHolderView;
		
		if(convertView == null){
			convertView = View.inflate(context, R.layout.item_lv_collection, null);
			
			mHolderView = new ItemLVCollectionHolderView();
			mHolderView.mTVTitle = (TextView) convertView.findViewById(R.id.tv_col_colfmg);
			
			convertView.setTag(mHolderView);
			
		} else {
			mHolderView = (ItemLVCollectionHolderView) convertView.getTag();
		}
		
		CollectionBean cb = list.get(position);
		mHolderView.mTVTitle.setText(cb.getTitle());

		return convertView;
	}
	
	/**
	 * 添加单个条目并刷新
	 */
	public void addItemAndNotifyChanged(CollectionBean bean){
		list.add(bean);
		notifyDataSetChanged();
	}
	
	/**
	 * 添加多个条目并刷新
	 */
	public void addItemAndNotifyChanged(List<CollectionBean> list){
		this.list.addAll(list);
		notifyDataSetChanged();
	}
	
	/**
	 * 删除条目并刷新
	 */
	public void delItemAndNotifyChanged(CollectionBean bean, Handler handler){
		// 集合的remove默认是判断地址
		// list.remove(bean);
		
		for (int i = 0; i < list.size(); i++) {
			CollectionBean cb = list.get(i);
			if(cb.getTitleID().equals( bean.getTitleID())){
				list.remove(i);
				break;
			}
		}
		
		if(list.size()<1){
			Message msg = handler.obtainMessage();
			handler.sendEmptyMessage(1);
		}
		
		notifyDataSetChanged();
	}

	/**
	 * 条目控制器
	 * @author Administrator
	 */
	class ItemLVCollectionHolderView {
		/** 标题 **/
		TextView mTVTitle;
	}

}
