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
import com.cuiweiyou.knockofflongyuan.bean.ArticleBean;
import com.cuiweiyou.knockofflongyuan.util.ContextApplication;
import com.cuiweiyou.knockofflongyuan.util.ImgLoadUtil;

public class LVArticleAdapter extends BaseAdapter {

	/** 上下文 **/
	private Context context;
	/** 条目对象集合 **/
	private List<ArticleBean> list;
	private ImgLoadUtil ilu;
	private Bitmap defBmp;

	/**
	 * HomeActivity中bottom层的主题LIV适配器
	 * @param context 上下文
	 * @param list 条目对象集合
	 * @author Administrator
	 */
	public LVArticleAdapter(Context context, List<ArticleBean> list){
		this.context = context;
		this.list = list;
		
		ilu = new ImgLoadUtil();
		
		defBmp = BitmapFactory.decodeResource(ContextApplication.context.getResources(), R.drawable.splash_logo);
	}
	
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
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
		final ItemLVArticleHolderView mHolderView;
		
		if(convertView == null){
			convertView = View.inflate(context, R.layout.item_lv_article, null);
			
			mHolderView = new ItemLVArticleHolderView();
			mHolderView.mTVTitle = (TextView) convertView.findViewById(R.id.tv_title_information);
			mHolderView.mTVAuthor = (TextView) convertView.findViewById(R.id.tv_author_information);
			mHolderView.mTVDate = (TextView) convertView.findViewById(R.id.tv_date_information);
			mHolderView.mIVImg = (ImageView) convertView.findViewById(R.id.iv_img_information);
			
			convertView.setTag(mHolderView);
			
		} else {
			mHolderView = (ItemLVArticleHolderView) convertView.getTag();
		}
		
		ArticleBean ab = list.get(position);
		mHolderView.mTVTitle.setText(ab.getTitle());
		mHolderView.mTVAuthor.setText(ab.getAuthor());
		mHolderView.mTVDate.setText(ab.getPubStartDate());
		mHolderView.mIVImg.setImageBitmap(defBmp);

		// 网络延迟更新图片
		final String imgURl = ab.getArticleImgList();	// 远程图片地址
		mHolderView.mIVImg.setTag(imgURl);				// 将地址作为图片的tab
		
		// 子线程加载图片。
		ilu.downloadImg(
				context, 
				imgURl,
				new IImageBack(){
				@Override
				public void setImage(Bitmap bmp, String tag) {
					
					if(tag!=null && tag.equals(mHolderView.mIVImg.getTag())) {
						// ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
						// ?
						mHolderView.mIVImg.setImageBitmap(bmp);
					}
				}
			}, 
			"home");
		
		return convertView;
	}

	/**
	 * 条目控制器
	 * @author Administrator
	 */
	class ItemLVArticleHolderView {
		 /** 标题ID**/
		 TextView mTVTitleID;
		/** 标题 **/
		TextView mTVTitle;
		/** 作者 **/
		TextView mTVAuthor;
		/** 来源类型 **/
		TextView mTVIntroduction;
		/** 日期 **/
		TextView mTVDate;
		/** 图片 **/
		ImageView mIVImg;
	}

}
