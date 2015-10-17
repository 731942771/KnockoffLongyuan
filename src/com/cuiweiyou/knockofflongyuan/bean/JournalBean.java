package com.cuiweiyou.knockofflongyuan.bean;

/**
 * 杂志数据封装
 * 
 * @author Administrator
 */
public class JournalBean {

	String magazineName; // 标题
	String magazineGUID; // ID
	String year; // 出版年
	String issue; // 期
	String iconList; // 标图

	public JournalBean() {
		super();
	}

	public JournalBean(String magazineName, String magazineGUID, String year,
			String issue, String iconList) {
		super();
		this.magazineName = magazineName;
		this.magazineGUID = magazineGUID;
		this.year = year;
		this.issue = issue;
		this.iconList = iconList;
	}

	public String getMagazineName() {
		return magazineName;
	}

	public void setMagazineName(String magazineName) {
		this.magazineName = magazineName;
	}

	public String getMagazineGUID() {
		return magazineGUID;
	}

	public void setMagazineGUID(String magazineGUID) {
		this.magazineGUID = magazineGUID;
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

	public String getIconList() {
		return iconList;
	}

	public void setIconList(String iconList) {
		this.iconList = iconList;
	}

	@Override
	public String toString() {
		return "JournalBean [magazineName=" + magazineName + ", magazineGUID="
				+ magazineGUID + ", year=" + year + ", issue=" + issue
				+ ", iconList=" + iconList + "]";
	}

}
