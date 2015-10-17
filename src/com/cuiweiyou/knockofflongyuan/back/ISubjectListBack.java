package com.cuiweiyou.knockofflongyuan.back;

import java.util.List;

import com.cuiweiyou.knockofflongyuan.bean.SubjectBean;

/**
 * 主题集合回调
 * @author Administrator
 */
public interface ISubjectListBack {

	/** AsyncTask执行结束后，HmAty的回调方法：发回主题集合 **/
	public void setSbList(List<SubjectBean> sbList);
}
