package com.example.favorite;

public class VideoPojo {

	private String FVItemId;
	private String FVItemType; 
	private String FVItemCatId; 
	private String FVItemCatName; 
	private String FVItemThumb; 
	private String FVItemTag; 
	private String FVItemUrl; 
	private String FVItemSize; 
	private String FVItemUser;
	private String FVItemRate;
	private String FVItemPlayIds;
	private String FVItemName;



	public VideoPojo(String fvid, String fvtype, String fvcid,String fvcname, String fvthumb,
			String fvtag,String fvurl,String fvsize,String fvuser,String fvrate,String fvplayid,String fvname) {
		// TODO Auto-generated constructor stub
		this.FVItemId=fvid;
		this.FVItemType=fvtype;
		this.FVItemCatId=fvcid;
		this.FVItemCatName=fvcname;
 		this.FVItemThumb=fvthumb;
		this.FVItemTag=fvtag;
		this.FVItemUrl=fvurl;
		this.FVItemSize=fvsize;
		this.FVItemUser=fvuser;
		this.FVItemRate=fvrate;
		this.FVItemPlayIds=fvplayid;
		this.FVItemName=fvname;
		 
	}

	public VideoPojo(String vid) {
		// TODO Auto-generated constructor stub
		this.FVItemId=vid;
	}

	public VideoPojo() {
		// TODO Auto-generated constructor stub
	}


	public String getFVItemId() {
		return FVItemId;
	}

	public void setFVItemId(String FVItemId) {
		this.FVItemId = FVItemId;
	}

	public String getFVItemType() {
		return FVItemType;
	}

	public void setFVItemType(String FVItemType) {
		this.FVItemType = FVItemType;
	}

	public String getFVItemCatId() {
		return FVItemCatId;
	}

	public void setFVItemCatId(String FVItemCatId) {
		this.FVItemCatId = FVItemCatId;
	}

	public String getFVItemCatName() {
		return FVItemCatName;
	}

	public void setFVItemCatName(String FVItemCatName) {
		this.FVItemCatName = FVItemCatName;
	}
	public String getFVItemThumb() {
		return FVItemThumb;
	}

	public void setFVItemThumb(String FVItemThumb) {
		this.FVItemThumb = FVItemThumb;
	}
	public String getFVItemTag() {
		return FVItemTag;
	}

	public void setFVItemTag(String FVItemTag) {
		this.FVItemTag = FVItemTag;
	}
	public String getFVItemUrl() {
		return FVItemUrl;
	}

	public void setFVItemUrl(String FVItemUrl) {
		this.FVItemUrl = FVItemUrl;
	}
	public String getFVItemSize() {
		return FVItemSize;
	}

	public void setFVItemSize(String FVItemSize) {
		this.FVItemSize = FVItemSize;
	}
	public String getFVItemUser() {
		return FVItemUser;
	}

	public void setFVItemUser(String FVItemUser) {
		this.FVItemUser = FVItemUser;
	}
	public String getFVItemRate() {
		return FVItemRate;
	}

	public void setFVItemRate(String FVItemRate) {
		this.FVItemRate = FVItemRate;
	}
	public String getFVItemPlayIds() {
		return FVItemPlayIds;
	}

	public void setFVItemPlayIds(String FVItemPlayIds) {
		this.FVItemPlayIds = FVItemPlayIds;
	}
	public String getFVItemName() {
		return FVItemName;
	}

	public void setFVItemName(String FVItemName) {
		this.FVItemName = FVItemName;
	}
}