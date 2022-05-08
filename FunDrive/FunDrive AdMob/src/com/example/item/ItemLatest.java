package com.example.item;

public class ItemLatest {
	
 	private String CategoryName;
	private String ImageUrl; 
	private String ImageDCount; 
	private String ImageWId; 
	private String ImageWUser; 
	private String ImageWTag; 
	private String ImageWSize;
	private String ImageStar;
	
	
	public ItemLatest(String lcatename, String limage) {
		// TODO Auto-generated constructor stub
		this.CategoryName=lcatename;
		this.ImageUrl=limage;
	}

	public ItemLatest() {
		// TODO Auto-generated constructor stub
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryname) {
		this.CategoryName = categoryname;
	}
	 
	public String getImageurl()
	{
		return ImageUrl;
		
	}
	
	public void setImageurl(String imageurl)
	{
		this.ImageUrl=imageurl;
	}
	public String getImageDCount()
	{
		return ImageDCount;
		
	}
	
	public void setImageDCount(String imagedcount)
	{
		this.ImageDCount=imagedcount;
	}
	public String getImageWId()
	{
		return ImageWId;
		
	}
	
	public void setImageWId(String imagewid)
	{
		this.ImageWId=imagewid;
	}
	
	public String getImageWUser()
	{
		return ImageWUser;
		
	}
	
	public void setImageWUser(String imagewuser)
	{
		this.ImageWUser=imagewuser;
	}
	public String getImageWTag()
	{
		return ImageWTag;
		
	}
	
	public void setImageWTag(String imagewtag)
	{
		this.ImageWTag=imagewtag;
	}
	public String getImageWSize()
	{
		return ImageWSize;
		
	}
	
	public void setImageWSize(String imagewsize)
	{
		this.ImageWSize=imagewsize;
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
