package com.cuiweiyou.knockofflongyuan.bean;

/**
 * 收藏条目Bean
 * @author Administrator
 */
public class CollectionBean {
	String titleID; // 文章ID。"1D42B880-E598-4AF5-9144-D1D49B351640",
	String title; // 标题。"永泰龟城：戈壁深处的黄土孤城",

	public CollectionBean() {
		super();
	}

	/**
	 * @param titleID 文章id
	 * @param title 标题
	 */
	public CollectionBean(String titleID, String title) {
		super();
		this.titleID = titleID;
		this.title = title;
	}

	public String getTitleID() {
		return titleID;
	}

	public void setTitleID(String titleID) {
		this.titleID = titleID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "ArticleBean [titleID=" + titleID + ", title=" + title + "]";
	}

}
