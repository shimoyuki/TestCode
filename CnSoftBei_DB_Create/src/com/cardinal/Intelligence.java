package com.cardinal;

import java.util.Date;

public class Intelligence {
	private int iId;
	private String iTitle = "";
	private String iAbs = "";
	private String iUrl = "";
	private Date iDate;
	private String iExpertName = "";
	private String iExpertOrg = "";
	
	public Intelligence() {

	}
	
	public Intelligence(String iTitle, String iAbs, String iUrl, Date iDate,
			String iExpertName, String iExpertOrg) {
		super();
		this.iTitle = iTitle;
		this.iAbs = iAbs;
		this.iUrl = iUrl;
		this.iDate = iDate;
		this.iExpertName = iExpertName;
		this.iExpertOrg = iExpertOrg;
	}
	
	public int getiId() {
		return iId;
	}
	public void setiId(int iId) {
		this.iId = iId;
	}
	public String getiTitle() {
		return iTitle;
	}
	public void setiTitle(String iTitle) {
		this.iTitle = iTitle;
	}
	public String getiAbs() {
		return iAbs;
	}
	public void setiAbs(String iAbs) {
		this.iAbs = iAbs;
	}
	public String getiUrl() {
		return iUrl;
	}
	public void setiUrl(String iUrl) {
		this.iUrl = iUrl;
	}
	public Date getiDate() {
		return iDate;
	}
	public void setiDate(Date iDate) {
		this.iDate = iDate;
	}
	public String getiExpertName() {
		return iExpertName;
	}
	public void setiExpertName(String iExpertName) {
		this.iExpertName = iExpertName;
	}
	public String getiExpertOrg() {
		return iExpertOrg;
	}
	public void setiExpertOrg(String iExpertOrg) {
		this.iExpertOrg = iExpertOrg;
	}
	public void setIntelligence(Intelligence i) {
		this.iTitle = i.iTitle;
		this.iAbs = i.iAbs;
		this.iUrl = i.iUrl;
		this.iDate = i.iDate;
		this.iExpertName = i.iExpertName;
		this.iExpertOrg = i.iExpertOrg;
	}
}
