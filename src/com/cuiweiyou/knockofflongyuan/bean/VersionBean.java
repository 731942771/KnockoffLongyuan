package com.cuiweiyou.knockofflongyuan.bean;

/**
 * 版本数据封装
 * 
 * @author Administrator
 */
public class VersionBean {

	String updateurl;
	String update;
	String ver;
	String summary;

	public VersionBean() {
		super();
	}

	/**
	 * @param updateurl apk文件地址
	 * @param update	更新状态码：0-未变，1-新版
	 * @param ver		版本号
	 * @param summary	版本号
	 */
	public VersionBean(String updateurl, String update, String ver, String summary) {
		super();
		this.updateurl = updateurl;
		this.update = update;
		this.ver = ver;
		this.summary = summary;
	}

	public String getUpdateurl() {
		return updateurl;
	}

	public void setUpdateurl(String updateurl) {
		this.updateurl = updateurl;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "VersionBean [updateurl=" + updateurl + ", update=" + update
				+ ", ver=" + ver + ", summary=" + summary + "]";
	}

}
