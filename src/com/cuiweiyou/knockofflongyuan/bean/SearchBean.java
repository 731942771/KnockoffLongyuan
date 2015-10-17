package com.cuiweiyou.knockofflongyuan.bean;

/**
 * 搜索数据封装
 * 
 * @author Administrator
 */
public class SearchBean {

	String hotWordSearchID;
	String hotWordName;

	public SearchBean() {
		super();
	}
	
	/**
	 * 搜索数据封装
	 */
	public SearchBean(String hotWordSearchID, String hotWordName) {
		super();
		this.hotWordSearchID = hotWordSearchID;
		this.hotWordName = hotWordName;
	}

	public String getHotWordSearchID() {
		return hotWordSearchID;
	}

	public void setHotWordSearchID(String hotWordSearchID) {
		this.hotWordSearchID = hotWordSearchID;
	}

	public String getHotWordName() {
		return hotWordName;
	}

	public void setHotWordName(String hotWordName) {
		this.hotWordName = hotWordName;
	}

	@Override
	public String toString() {
		return "SearchBean [hotWordSearchID=" + hotWordSearchID
				+ ", hotWordName=" + hotWordName + "]";
	}

}
