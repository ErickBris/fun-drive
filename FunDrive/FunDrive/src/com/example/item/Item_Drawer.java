package com.example.item;

public class Item_Drawer {
	
	private int CategoryId; 
	private String CategoryName;
	private int CategoryImage;
	  
	public Item_Drawer(int cid, String CName,int drawerWall) {		
		this.CategoryId = cid;
		this.CategoryName = CName;
		this.CategoryImage=drawerWall;
	}
	
	 
	public int getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(int categoryid) {
		this.CategoryId = categoryid;
	}
	
	
	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryname) {
		this.CategoryName = categoryname;
	}
	public int getCategoryImage() {
		return CategoryImage;
	}

	public void setCategoryImage(int categoryimage) {
		this.CategoryImage = categoryimage;
	}
	
	 

}
