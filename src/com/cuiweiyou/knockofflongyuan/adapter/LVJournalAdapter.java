package com.cuiweiyou.knockofflongyuan.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.back.IImageBack;
import com.cuiweiyou.knockofflongyuan.bean.JournalBean;
import com.cuiweiyou.knockofflongyuan.util.ImgLoadUtil;

/**
 * 杂志LIV适配器
 * @author Administrator
 */
public class LVJournalAdapter extends BaseAdapter {

	/** 上下文 **/
	private Context context;
	/** 条目对象集合 **/
	private List<JournalBean> list;
	/** 主UI的消息处理器，更新图片控件 **/
	private ImgLoadUtil ilu;
	private Bitmap defBmp;

	/**
	 * 杂志LIV适配器
	 * @param context 上下文
	 * @param list 条目对象集合
	 * @author Administrator
	 */
	public LVJournalAdapter(Context context, List<JournalBean> list){
		this.context = context;
		this.list = list;
		
		ilu = new ImgLoadUtil();
		defBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
	}
	
	@Override
	public int getCount() {
		return list.size()>0 ? list.size() : 0;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ItemLVJournalHolderView mHolderView;
		
		if(convertView == null){
			convertView = View.inflate(context, R.layout.item_lv_journal, null);
			
			mHolderView = new ItemLVJournalHolderView();
			mHolderView.mIVIco = (ImageView) convertView.findViewById(R.id.iv_ico_shop_jrlfmg);
			mHolderView.mTVTitle = (TextView) convertView.findViewById(R.id.tv_title_shop_jrlfmg);
			
			convertView.setTag(mHolderView);
			
		} else {
			mHolderView = (ItemLVJournalHolderView) convertView.getTag();
		}
		
		JournalBean jb = list.get(position);
		mHolderView.mTVTitle.setText(jb.getMagazineName());
		mHolderView.mIVIco.setImageBitmap(defBmp);
		
		String imgurl = jb.getIconList();
		mHolderView.mIVIco.setTag(imgurl);
		
		// 子线程加载图片。
		ilu.downloadImg(
				context, 
				imgurl, 
				new IImageBack() {
					@Override
					public void setImage(Bitmap bmp, String tag) {
						if(tag != null && mHolderView.mIVIco.getTag().equals(tag)){
							mHolderView.mIVIco.setImageBitmap(bmp);
						}
					}
				}, 
				"journal");
	
		return convertView;
	}

	/**
	 * 条目控制器
	 * @author Administrator
	 */
	class ItemLVJournalHolderView {
		/** 条目IV **/
		ImageView mIVIco;
		/** 标题 **/
		TextView mTVTitle;
	}

}
