package com.example.item;

public class ItemCategory {


	private String ItemCategoryName;
	private String ItemImageUrl;
	private String ItemCatId;
	private String ItemDCount;
	private String ItemWallId;
	private String ItemWallUser;
	private String ItemWallTag;
	private String ItemWallSize;
	private String ImageStar;

	public ItemCategory(String itemcategoryname, String itemimageurl, String itemcatid) {
		// TODO Auto-generated constructor stub
		this.ItemCategoryName=itemcategoryname;
		this.ItemImageUrl=itemimageurl;
		this.ItemCatId=itemcatid;
	}

	public ItemCategory() {
		// TODO Auto-generated constructor stub
	}

	public String getItemCategoryName() {
		return ItemCategoryName;
	}

	public void setItemCategoryName(String itemcategoryname) {
		this.ItemCategoryName = itemcategoryname;
	}


	public String getItemImageurl()
	{
		return ItemImageUrl;

	}

	public void setItemImageurl(String itemimageurl)
	{
		this.ItemImageUrl=itemimageurl;
	}
	public String getItemCatId()
	{
		return ItemCatId;

	}

	public void setItemCatId(String itemcatid)
	{
		this.ItemCatId=itemcatid;
	}
	public String getItemDCount()
	{
		return ItemDCount;

	}

	public void setItemDCount(String itemdcount)
	{
		this.ItemDCount=itemdcount;
	}
	public String getItemWallId()
	{
		return ItemWallId;

	}

	public void setItemWallId(String itemwallid)
	{
		this.ItemWallId=itemwallid;
	}
	public String getItemWallUser()
	{
		return ItemWallUser;

	}

	public void setItemWallUser(String itemwalluser)
	{
		this.ItemWallUser=itemwalluser;
	}
	public String getItemWallTag()
	{
		return ItemWallTag;

	}

	public void setItemWallTag(String itemwalltag)
	{
		this.ItemWallTag=itemwalltag;
	}
	public String getItemWallSize()
	{
		return ItemWallSize;

	}

	public void setItemWallSize(String itemwallsize)
	{
		this.ItemWallSize=itemwallsize;
	}

	public String getImageStar()
	{
		return ImageStar;

	}

	public void setImageStar(String imagestar)
	{
		this.ImageStar=imagestar;
	}


}
