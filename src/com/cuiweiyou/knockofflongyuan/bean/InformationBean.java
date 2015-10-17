package com.cuiweiyou.knockofflongyuan.bean;

/**
 * 主界面资讯条目
 * 
 * @author Administrator
 */
public class InformationBean {

	private String magazineName; // 类别：": "旅游",
	private String year; // 发布日期年: "2015",
	private String issue; // 期：": "8",
	private String title; // 文章标题：": "永泰龟城：戈壁深处的黄土孤城",
	private String titleID; // ID,
	private String content; // 正文

	public InformationBean() {
		super();
	}

	public InformationBean(String magazineName, String titleID, String year, String issue, String title, String content ) {
		super();
		this.magazineName = magazineName;
		this.titleID = titleID;
		this.year = year;
		this.issue = issue;
		this.title = title;
		this.content = content;
	}

	public String getMagazineName() {
		return magazineName;
	}

	public void setMagazineName(String magazineName) {
		this.magazineName = magazineName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleID() {
		return titleID;
	}

	public void setMagazineGUID(String titleID) {
		this.titleID = titleID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "InformationBean [magazineName=" + magazineName
				+ ", titleID=" + titleID + ", year=" + year
				+ ", issue=" + issue + ", title=" + title + ", content="
				+ content + "]";
	}


}
