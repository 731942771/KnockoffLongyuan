package com.cuiweiyou.knockofflongyuan.back;

import java.util.List;

import com.cuiweiyou.knockofflongyuan.bean.CollectionBean;

/**
 * 收藏集合回调
 * @author Administrator
 */
public interface ICollectionListBack {

	/** CollectionAsyncTask执行结束后，ColFmg的回调方法：发回收藏集合 **/
	public void setColsList(List<CollectionBean> list);
}
