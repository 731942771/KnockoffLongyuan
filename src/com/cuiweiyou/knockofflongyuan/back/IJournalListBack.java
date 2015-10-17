package com.cuiweiyou.knockofflongyuan.back;

import java.util.List;

import com.cuiweiyou.knockofflongyuan.bean.JournalBean;

/**
 * 杂志页面回调
 * @author Administrator
 */
public interface IJournalListBack {

	/** AsyncTask执行结束后，JrlAty的回调方法：发回杂志集合 **/
	public void setJrlList(List<JournalBean> srhList);
}
