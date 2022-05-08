package com.example.favorite;

public class RingPojo {

	private String DRingItemId;
	private String DRingItemCatId; 
	private String DRingItemCatName; 
	private String DRingItemName; 
 	private String DRingItemUrl; 
 	private String DRingItemDCount; 
	private String DRingItemRUser; 
	private String DRingItemRTag; 
	private String DRingItemRSize; 
	private String DRingItemRStar; 
	private String DRingItemRImag; 



	public RingPojo(String ringid,String ringcatid,  String ringcatname,String ringname,
			String ringurl,String ringdcount,String ringruser,
			String ringtag,String ringsize,String ringstar,String ringimg) {
		// TODO Auto-generated constructor stub
		this.DRingItemId=ringid;
		this.DRingItemName=ringname;
		this.DRingItemCatName=ringcatname;
		this.DRingItemUrl=ringurl;
		this.DRingItemCatId=ringcatid;
		this.DRingItemDCount=ringdcount;
		this.DRingItemRUser=ringruser;
		this.DRingItemRTag=ringtag;
		this.DRingItemRSize=ringsize;
 		this.DRingItemRStar=ringstar;
 		this.DRingItemRImag=ringimg;
	}

	public RingPojo(String ringid) {
		// TODO Auto-generated constructor stub
		this.DRingItemId=ringid;
	}

	public RingPojo() {
		// TODO Auto-generated constructor stub
	}


	public String getDRingItemId() {
		return DRingItemId;
	}

	public void setDRingItemId(String DRingItemId) {
		this.DRingItemId = DRingItemId;
	}

	public String getDRingItemName() {
		return DRingItemName;
	}

	public void setDRingItemName(String DRingItemName) {
		this.DRingItemName = DRingItemName;
	}

	public String getDRingItemCatName() {
		return DRingItemCatName;
	}

	public void setDRingItemCatName(String DRingItemCatName) {
		this.DRingItemCatName = DRingItemCatName;
	}

	public String getDRingItemUrl() {
		return DRingItemUrl;
	}

	public void setDRingItemUrl(String DRingItemUrl) {
		this.DRingItemUrl = DRingItemUrl;
	}
	public String getDRingItemCatId() {
		return DRingItemCatId;
	}

	public void setDRingItemCatId(String DRingItemCatId) {
		this.DRingItemCatId = DRingItemCatId;
	}
	public String getDRingItemDCount() {
		return DRingItemDCount;
	}

	public void setDRingItemDCount(String DRingItemDCount) {
		this.DRingItemDCount = DRingItemDCount;
	}
	public String getDRingItemRUser() {
		return DRingItemRUser;
	}

	public void setDRingItemRUser(String DRingItemRUser) {
		this.DRingItemRUser = DRingItemRUser;
	}
	public String getDRingItemRTag() {
		return DRingItemRTag;
	}

	public void setDRingItemRTag(String DRingItemRTag) {
		this.DRingItemRTag = DRingItemRTag;
	}
	public String getDRingItemRSize() {
		return DRingItemRSize;
	}

	public void setDRingItemRSize(String DRingItemRSize) {
		this.DRingItemRSize = DRingItemRSize;
	}
	public String getDRingItemRStar() {
		return DRingItemRStar;
	}

	public void setDRingItemRStar(String DRingItemRStar) {
		this.DRingItemRStar = DRingItemRStar;
	}
	public String getDRingItemRImag() {
		return DRingItemRImag;
	}

	public void setDRingItemRImag(String DRingItemRImag) {
		this.DRingItemRImag = DRingItemRImag;
	}

}