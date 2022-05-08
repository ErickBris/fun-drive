package com.example.favorite;

public class Pojo {

	private int id;
	private String CategoryName;
	private String ImageUrl; 
	private String ImageDCount; 
	private String ImageFId; 
	private String ImageFUser; 
	private String ImageFTag; 
	private String ImageFSize; 

	public Pojo()
	{

	}

	public Pojo(String imageid)
	{
		this.ImageUrl=imageid;
	}

	public Pojo(String cat_name,String img_url,String img_dcount,String img_fid,String img_fuser,String img_ftag,String img_fsize)
	{

		this.CategoryName=cat_name;
		this.ImageUrl=img_url;
		this.ImageDCount=img_dcount;
		this.ImageFId=img_fid;
		this.ImageFUser=img_fuser;
		this.ImageFTag=img_ftag;
		this.ImageFSize=img_fsize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	public String getImageFId()
	{
		return ImageFId;

	}

	public void setImageFId(String imagefid)
	{
		this.ImageFId=imagefid;
	}
	public String getImageFUser()
	{
		return ImageFUser;

	}

	public void setImageFUser(String imagefuser)
	{
		this.ImageFUser=imagefuser;
	}
	public String getImageFTag()
	{
		return ImageFTag;

	}

	public void setImageFTag(String imageftag)
	{
		this.ImageFTag=imageftag;
	}
	public String getImageFSize()
	{
		return ImageFSize;

	}

	public void setImageFSize(String imagefsize)
	{
		this.ImageFSize=imagefsize;
	}

}
