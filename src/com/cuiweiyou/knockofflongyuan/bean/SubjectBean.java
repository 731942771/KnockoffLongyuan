package com.cuiweiyou.knockofflongyuan.bean;

/**
 * HmAty的bottom层主题条目
 * 
 * @author Administrator
 */
public class SubjectBean {

	String categoryCode; // 主题索引
	String categoryName; // 标题
	boolean isSelected = false;

	/**
	 * HmAty的bottom层主题条目
	 * @author Administrator
	 */
	public SubjectBean() {
	}

	/**
	 * @param categoryCode  索引
	 * @param categoryName  标题
	 */
	public SubjectBean(String categoryCode, String categoryName, boolean isSelected) {
		super();
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.isSelected = isSelected;
	}

	public String getCategoryCode() {
		return this.categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/** 集合还添加一个最顶层的“全部”，网络请求的json中没有对应key **/
	public boolean isSelected(){
		return this.isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		return "SubjectBean [CategoryCode=" + categoryCode + ", CategoryName=" + categoryName + ", isSelected=" + isSelected + "]";
	}
}
