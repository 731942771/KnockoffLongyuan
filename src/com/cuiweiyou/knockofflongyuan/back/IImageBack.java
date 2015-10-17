package com.cuiweiyou.knockofflongyuan.back;

import android.graphics.Bitmap;

/**
 * 图片回调
 * @author Administrator
 */
public interface IImageBack {
	/** 
	 * ImgLoadUtil子线程得到图片后回调图片给Adapter
	 * @param bmp 图片数据
	 * @param tag 对应图片控件的tag
	 */
	public void setImage(Bitmap bmp, String tag);

}
