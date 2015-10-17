package com.cuiweiyou.knockofflongyuan.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.bean.SubjectBean;

/**
 * HomeActivity中bottom层的主题LIV适配器
 * @author Administrator
 */
public class LVSubjectAdapter extends BaseAdapter {

	/** 上下文 **/
	private Context context;
	/** 条目对象集合 **/
	private List<SubjectBean> list;
	/** 已选中的item索引 **/
	private int selectedIndex = -1;

	/**
	 * HomeActivity中bottom层的主题LIV适配器
	 * @param context 上下文
	 * @param list 条目对象集合
	 * @author Administrator
	 */
	public LVSubjectAdapter(Context context, List<SubjectBean> list){
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
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
		ItemLVSubjectHolderView mHolderView = null;
		
		if(convertView == null){
			convertView = View.inflate(context, R.layout.item_lv_subject, null);
			
			mHolderView = new ItemLVSubjectHolderView();
			mHolderView.mTVName = (TextView) convertView.findViewById(R.id.tv_title_itemlvsubject);
			mHolderView.mTVState = (TextView) convertView.findViewById(R.id.tv_btn_state_itemlvsubject);	// 状态杆
			
			convertView.setTag(mHolderView);
			
		} else {
			mHolderView = (ItemLVSubjectHolderView) convertView.getTag();
		}
		
		SubjectBean sb = list.get(position);
		mHolderView.mTVName.setText(sb.getCategoryName());
		// 如果是选中的，记录这个位置，同时隐藏
		if(sb.isSelected()){
			setSelectedIndex(position);
			mHolderView.mTVState.setVisibility(View.INVISIBLE);
		} 
		// 否则，显示
		else {
			mHolderView.mTVState.setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}
	
	/**
	 * 获取已经选中的条目索引
	 * @return id
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * 保存被选中的条目索引
	 * @param selectedIndex id
	 */
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	/**
	 * 条目控制器
	 * @author Administrator
	 */
	class ItemLVSubjectHolderView {
		/** 条目标题文本 **/
		TextView mTVName;
		/** 选择状态 **/
		TextView mTVState;
	}

}
