package com.cuiweiyou.knockofflongyuan.back;

import java.util.List;

import com.cuiweiyou.knockofflongyuan.bean.SearchBean;

/**
 * 搜索页面回调
 * @author Administrator
 */
public interface ISearchListBack {

	/** AsyncTask执行结束后，SrhAty的回调方法：发回既定的热词集合 **/
	public void setSrhList(List<SearchBean> srhList);
}
