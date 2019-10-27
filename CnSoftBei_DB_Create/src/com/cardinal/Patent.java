package com.cardinal;

import java.util.Date;

public class Patent {
	private int pId;
	private String pTitle = "";
	private Date pDate;
	private String pAbs = "";
	private String pApplicant = "";
	private String pInventor = "";
	private String pExpertName = "";
	private String pExpertOrg = "";
	private String pPatentAgency = "";
	
	public Patent() {

	}
	
	public Patent(String pTitle, Date pDate, String pAbs, String pApplicant,
			String pInventor, String pExpertName, String pExpertOrg,
			String pPatentAgency) {
		super();
		this.pTitle = pTitle;
		this.pDate = pDate;
		this.pAbs = pAbs;
		this.pApplicant = pApplicant;
		this.pInventor = pInventor;
		this.pExpertName = pExpertName;
		this.pExpertOrg = pExpertOrg;
		this.pPatentAgency = pPatentAgency;
	}

	public String getpAbs() {
		return pAbs;
	}

	public String getpApplicant() {
		return pApplicant;
	}

	public Date getpDate() {
		return pDate;
	}
	public String getpExpertName() {
		return pExpertName;
	}
	public String getpExpertOrg() {
		return pExpertOrg;
	}
	public int getpId() {
		return pId;
	}
	public String getpInventor() {
		return pInventor;
	}
	public String getpPatentAgency() {
		return pPatentAgency;
	}
	public String getpTitle() {
		return pTitle;
	}
	public void setpAbs(String pAbs) {
		this.pAbs = pAbs;
	}
	public void setpApplicant(String pApplicant) {
		this.pApplicant = pApplicant;
	}
	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}
	public void setpExpertName(String pExpertName) {
		this.pExpertName = pExpertName;
	}
	public void setpExpertOrg(String pExpertOrg) {
		this.pExpertOrg = pExpertOrg;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public void setpInventor(String pInventor) {
		this.pInventor = pInventor;
	}
	public void setpPatentAgency(String pPatentAgency) {
		this.pPatentAgency = pPatentAgency;
	}
	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}
	public void setPatent(Patent p) {
		this.pTitle = p.pTitle;
		this.pDate =  p.pDate;
		this.pAbs =  p.pAbs;
		this.pApplicant =  p.pApplicant;
		this.pInventor =  p.pInventor;
		this.pExpertName =  p.pExpertName;
		this.pExpertOrg =  p.pExpertOrg;
		this.pPatentAgency =  p.pPatentAgency;
	}
}
