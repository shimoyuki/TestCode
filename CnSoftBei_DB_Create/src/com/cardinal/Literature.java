package com.cardinal;

public class Literature {
	private int lId;
	private String lTitle = "";
	private String lAbs = "";
	private String lAuthorCn = "";
	private String lUnit = "";
	private String lJournalCn = "";
	private String lAppDate = "";
	private String lKey = "";
	private String lExpertName = "";
	private String lExpertOrg = "";
	
	
	public Literature() {
		
	}
	public Literature(String lTitle, String lAbs, String lAuthorCn,
			String lUnit, String lJournalCn, String lAppDate, String lKey,
			String lExpertName, String lExpertOrg) {
		super();
		this.lTitle = lTitle;
		this.lAbs = lAbs;
		this.lAuthorCn = lAuthorCn;
		this.lUnit = lUnit;
		this.lJournalCn = lJournalCn;
		this.lAppDate = lAppDate;
		this.lKey = lKey;
		this.lExpertName = lExpertName;
		this.lExpertOrg = lExpertOrg;
	}
	public String getlAbs() {
		return lAbs;
	}
	public String getlAppDate() {
		return lAppDate;
	}
	public String getlAuthorCn() {
		return lAuthorCn;
	}
	public String getlExpertName() {
		return lExpertName;
	}
	public String getlExpertOrg() {
		return lExpertOrg;
	}
	public int getlId() {
		return lId;
	}
	public String getlJournalCn() {
		return lJournalCn;
	}
	public String getlKey() {
		return lKey;
	}
	public String getlTitle() {
		return lTitle;
	}
	public String getlUnit() {
		return lUnit;
	}
	public void setlAbs(String lAbs) {
		this.lAbs = lAbs;
	}
	public void setlAppDate(String lAppDate) {
		this.lAppDate = lAppDate;
	}
	public void setlAuthorCn(String lAuthorCn) {
		this.lAuthorCn = lAuthorCn;
	}
	public void setlExpertName(String lExpertName) {
		this.lExpertName = lExpertName;
	}
	public void setlExpertOrg(String lExpertOrg) {
		this.lExpertOrg = lExpertOrg;
	}
	public void setlId(int lId) {
		this.lId = lId;
	}
	public void setlJournalCn(String lJournalCn) {
		this.lJournalCn = lJournalCn;
	}
	public void setlKey(String lKey) {
		this.lKey = lKey;
	}
	public void setlTitle(String lTitle) {
		this.lTitle = lTitle;
	}
	public void setlUnit(String lUnit) {
		this.lUnit = lUnit;
	}
	public void setLiterature(Literature l) {
		this.lTitle = l.lTitle;
		this.lAbs = l.lAbs;
		this.lAuthorCn = l.lAuthorCn;
		this.lUnit = l.lUnit;
		this.lJournalCn = l.lJournalCn;
		this.lAppDate = l.lAppDate;
		this.lKey = l.lKey;
		this.lExpertName = l.lExpertName;
		this.lExpertOrg = l.lExpertOrg;
	}
}
