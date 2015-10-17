package com.cuiweiyou.knockofflongyuan.model;

/**
 * 网络请求地址封装
 * @author Administrator
 */
public class UrlModel {

	private UrlModel(){}
	
	/** 首页bottom界面的主题请求地址。固定 **/
	public static String subjectUrl = 
			"http://m.qikan.com/DragonEssenceApp/EssentialCategoryList.ashx?" +
			"authToken=oVAhCo1ZczRFX%2bKdVjKvlWaV%2fx%2bGcT%2fN%2fWkW%2b3k03yI%3d&" +
			"appsystem=1";
	
	
	
	/** 首页top界面的资讯列表请求地址。须拼接。也是下拉刷新、双击刷新的地址 <br/>
	 * "pagesize=<b>30</b>&" +	// 每次请求的资讯数量 <br/>
	 * "categoryid=<b>1</b>&" +	// 主题，为1指“全部”。对应subjectUrl请求回的json解析出的“CategoryCode” <br/>
	 * "pageindex=<b>1</b>";　　 // 分页
	 **/
	public static String  articleListUrl = 
			"http://m.qikan.com/DragonEssenceApp/GetEssentialArticleList.ashx?" +
			"authToken=oVAhCo1ZczRFX%2bKdVjKvlWaV%2fx%2bGcT%2fN%2fWkW%2b3k03yI%3d&" +
			"updatedate=1&" +	// 
			"ordertype=1&" +	// 
			"appsystem=1&";
	

	
	/**
	 * 首页资讯列表上拉加载地址<br/>
	 * 须要拼接其后的：<br/>
	 * 		"pagesize=<b>30</b>&" +                           // 每页条目数量                 <br/>
	 * 		"categoryid=<b>1</b>&" +                           // 主题id            <br/>
	 * 		"pageindex=<b>2</b>&" +                           // 分页，第几次上拉          <br/>
	 * 		"updatedate=<b>2015%2F09%2F17+17%3A06%3A57</b>"; // 刷新时刻。URL编码 2015/09/17 17:06:57
	 */
	public static String articleListUpdateUrl = 
			"http://m.qikan.com/DragonEssenceApp/GetEssentialArticleList.ashx?" +
			"authToken=oVAhCo1ZczRFX%2bKdVjKvlWaV%2fx%2bGcT%2fN%2fWkW%2b3k03yI%3d&" +
			"appsystem=1&" +
			"ordertype=2&" ;
	
	
	
	/** 首页top界面的资讯正文请求地址 <br/>
	 * 　　　　// 关键。本条资讯的id，对应informationListUrl请求回的json解析出的“TitleID”<br/>
	 * "titleid=<b>1D42B880-E598-4AF5-9144-D1D49B351640</b>&" + <br/>
	 * "fromtype=<b>2</b>&" +　　　// 权限<br/>
	 * "categoryid=<b>9</b>";　　　// 所属主题，对应subjectUrl请求回的json解析出的“CategoryCode”<br/>
	 **/
	public static String informationUrl = 
			"http://m.qikan.com/DragonEssenceApp/GetMagazineArticle.ashx?" +
			"authToken=oVAhCo1ZczRFX%2bKdVjKvlWaV%2fx%2bGcT%2fN%2fWkW%2b3k03yI%3d&" +
			"ver=2.0.8&" +
			"appsystem=1&";
	
	
	
	/** 搜索页面请求地址，固定**/
	public static String searchUrl = 
			"http://m.qikan.com/DragonEssenceApp/GetHotWords.ashx?" +
			"authToken=oVAhCo1ZczRFX%2bKdVjKvlWaV%2fx%2bGcT%2fN%2fWkW%2b3k03yI%3d&" +
			"appsystem=1;";

	
	
	/** 杂志页面，商店，请求地址，固定**/
	public static String journalShopUrl = 
			"http://m.qikan.com/DragonEssenceApp/GetMagazineList.ashx?" +
			"authToken=oVAhCo1ZczRFX%2bKdVjKvlWaV%2fx%2bGcT%2fN%2fWkW%2b3k03yI%3d&" +
			"pageindex=1&" +
			"pagesize=30&" +
			"categoryid=&" +
			"updatedate=&" +
			"ordertype=1&" +
			"appsystem=1";

}
