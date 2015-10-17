package com.cuiweiyou.knockofflongyuan.back;

import java.util.List;

import com.cuiweiyou.knockofflongyuan.bean.ArticleBean;

/**
 * 首页资讯集合回调
 * @author Administrator
 */
public interface IArticleListBack {

	/** InformationAsyncTask执行结束后，HmFmg的回调方法：发回主题集合 **/
	public void setABList(List<ArticleBean> list);
}
