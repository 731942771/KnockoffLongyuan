package com.cuiweiyou.knockofflongyuan.bean;

/**
 * 首页显示的文章资讯简要Bean
 * @author Administrator
 */
public class ArticleBean {
	String titleID; // 文章ID。"1D42B880-E598-4AF5-9144-D1D49B351640",
	String title; // 标题。"永泰龟城：戈壁深处的黄土孤城",
	String author; // 作者。"",
	String introduction; // 来源类型。
	String pubStartDate; // 发布日期。"2015-9-9 11:29:11",
	String articleImgList; // 展示图片。"http://uploadfile.qikan.com.cn/Files/Public/Compilation/Uploads/2015/09/09/bf4682be-0369-424a-ad32-d95cf698a672.jpg",

	public ArticleBean() {
		super();
	}

	/**
	 * @param titleID 文章id
	 * @param title 标题
	 * @param author 作者
	 * @param introduction 来源类型
	 * @param pubStartDate 发布日期
	 * @param articleImgList 展示图片
	 */
	public ArticleBean(String titleID, String title, String author, String introduction, String pubStartDate, String articleImgList) {
		super();
		this.titleID = titleID;
		this.title = title;
		this.author = author;
		this.introduction = introduction;
		this.pubStartDate = pubStartDate;
		this.articleImgList = articleImgList;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getPubStartDate() {
		return pubStartDate;
	}

	public void setPubStartDate(String pubStartDate) {
		this.pubStartDate = pubStartDate;
	}

	public String getArticleImgList() {
		return articleImgList;
	}

	public void setArticleImgList(String articleImgList) {
		this.articleImgList = articleImgList;
	}

	@Override
	public String toString() {
		return "ArticleBean [titleID=" + titleID + ", title=" + title
				+ ", author=" + author + ", introduction=" + introduction
				+ ", pubStartDate=" + pubStartDate + ", articleImgList="
				+ articleImgList + "]";
	}

}
