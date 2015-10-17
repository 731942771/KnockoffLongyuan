package com.cuiweiyou.knockofflongyuan.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.bean.SearchBean;

/**
 * SearchFmg的GV适配器
 * @author Administrator
 */
public class GVSearchAdapter extends BaseAdapter {

	/** 上下文 **/
	private Context context;
	/** 条目对象集合 **/
	private List<SearchBean> list;

	/**
	 * HomeActivity中bottom层的主题LIV适配器
	 * @param context 上下文
	 * @param list 条目对象集合
	 * @author Administrator
	 */
	public GVSearchAdapter(Context context, List<SearchBean> list){
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
		ItemGVSearchHolderView mHolderView = null;
		
		if(convertView == null){
			convertView = View.inflate(context, R.layout.item_gv_srhfmg, null);
			
			mHolderView = new ItemGVSearchHolderView();
			mHolderView.mTVWord = (TextView) convertView.findViewById(R.id.tv_keyword_srhfmg);
			
			convertView.setTag(mHolderView);
			
		} else {
			mHolderView = (ItemGVSearchHolderView) convertView.getTag();
		}
		
		SearchBean sb = list.get(position);
		mHolderView.mTVWord.setText(sb.getHotWordName());
		
		return convertView;
	}

	/**
	 * 条目控制器
	 * @author Administrator
	 */
	class ItemGVSearchHolderView {
		/** 条目标题文本 **/
		TextView mTVWord;
	}

}
