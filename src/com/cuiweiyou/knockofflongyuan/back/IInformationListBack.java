package com.cuiweiyou.knockofflongyuan.back;

import com.cuiweiyou.knockofflongyuan.bean.InformationBean;

/**
 * 资讯正文数据回调
 * @author Administrator
 */
public interface IInformationListBack {

	/** AsyncTask执行结束后，资讯正文ben **/
	public void setIbList(InformationBean ib);
}
