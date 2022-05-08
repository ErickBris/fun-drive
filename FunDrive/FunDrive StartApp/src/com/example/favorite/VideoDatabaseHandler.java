package com.example.favorite;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VideoDatabaseHandler extends SQLiteOpenHelper{
	
private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "AddtoFavrVideo";
	
	private static final String TABLE_NAME = "FavoriteVideo";
	private static final String KEY_ID = "id";
	private static final String KEY_FAV_VID = "favvid";
	private static final String KEY_FAV_VTYPE = "favvtype";
	private static final String KEY_FAV_VCID = "favvcid";
	private static final String KEY_FAV_VCNAME = "favvcname";
	private static final String KEY_FAV_VTHUMB = "favvthumb";
	private static final String KEY_FAV_VTAG = "favvtag";
	private static final String KEY_FAV_VURL = "favvurl";
	private static final String KEY_FAV_VSIZE = "favvsize";
	private static final String KEY_FAV_VUSER = "favvuser";
	private static final String KEY_FAV_VRATE = "favvrate";
	private static final String KEY_FAV_VPLAYID = "favvplayid";
	private static final String KEY_FAV_VNAME = "favvname";
	  
	 

	public VideoDatabaseHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
					+ KEY_ID + " INTEGER PRIMARY KEY," 
					+ KEY_FAV_VID + " TEXT,"
					+ KEY_FAV_VTYPE + " TEXT,"
					+ KEY_FAV_VCID + " TEXT,"
					+ KEY_FAV_VCNAME + " TEXT,"
					+ KEY_FAV_VTHUMB + " TEXT,"
					+ KEY_FAV_VTAG + " TEXT,"
					+ KEY_FAV_VURL + " TEXT,"
					+ KEY_FAV_VSIZE + " TEXT,"
					+ KEY_FAV_VUSER + " TEXT,"
					+ KEY_FAV_VRATE + " TEXT,"
					+ KEY_FAV_VPLAYID + " TEXT,"
					+ KEY_FAV_VNAME + " TEXT"
					+ ")";
			db.execSQL(CREATE_CONTACTS_TABLE);		
			
		}
		
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

				// Create tables again
				onCreate(db);
	}
	
	//Adding Record in Database
	
	public	void AddtoFavoritevideo(VideoPojo pj)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(KEY_FAV_VID, pj.getFVItemId());
		values.put(KEY_FAV_VTYPE, pj.getFVItemType());
		values.put(KEY_FAV_VCID, pj.getFVItemCatId());
		values.put(KEY_FAV_VCNAME, pj.getFVItemCatName());
		values.put(KEY_FAV_VTHUMB, pj.getFVItemThumb());
		values.put(KEY_FAV_VTAG, pj.getFVItemTag());
		values.put(KEY_FAV_VURL, pj.getFVItemUrl());
		values.put(KEY_FAV_VSIZE, pj.getFVItemSize());
		values.put(KEY_FAV_VUSER, pj.getFVItemUser());
		values.put(KEY_FAV_VRATE, pj.getFVItemRate());
		values.put(KEY_FAV_VPLAYID, pj.getFVItemPlayIds());
		values.put(KEY_FAV_VNAME, pj.getFVItemName());
		 
		
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
		
	}
	
	// Getting All Data
		public List<VideoPojo> getAllData() 
		{
			List<VideoPojo> dataList = new ArrayList<VideoPojo>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_NAME;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) 
			{
				do {
					VideoPojo contact = new VideoPojo();
					//contact.setId(Integer.parseInt(cursor.getString(0)));
					contact.setFVItemId(cursor.getString(1));
					contact.setFVItemType(cursor.getString(2));
					contact.setFVItemCatId(cursor.getString(3));
					contact.setFVItemCatName(cursor.getString(4));
					contact.setFVItemThumb(cursor.getString(5));
					contact.setFVItemTag(cursor.getString(6));
					contact.setFVItemUrl(cursor.getString(7));
					contact.setFVItemSize(cursor.getString(8));
					contact.setFVItemUser(cursor.getString(9));
					contact.setFVItemRate(cursor.getString(10));
					contact.setFVItemPlayIds(cursor.getString(11));
					contact.setFVItemName(cursor.getString(12));
					 
 					// Adding contact to list
					dataList.add(contact);
				} while (cursor.moveToNext());
			}

			// return contact list
			return dataList;
		}
		
	//getting single row
		
		public List<VideoPojo> getFavRow(String id) 
		{
			List<VideoPojo> dataList = new ArrayList<VideoPojo>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE favvid="+"'"+id+"'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) 
			{
				do {
					VideoPojo contact = new VideoPojo();
				//	contact.setId(Integer.parseInt(cursor.getString(0)));
					contact.setFVItemId(cursor.getString(1));
					contact.setFVItemType(cursor.getString(2));
					contact.setFVItemCatId(cursor.getString(3));
					contact.setFVItemCatName(cursor.getString(4));
					contact.setFVItemThumb(cursor.getString(5));
					contact.setFVItemTag(cursor.getString(6));
					contact.setFVItemUrl(cursor.getString(7));
					contact.setFVItemSize(cursor.getString(8));
					contact.setFVItemUser(cursor.getString(9));
					contact.setFVItemRate(cursor.getString(10));
					contact.setFVItemPlayIds(cursor.getString(11));
					contact.setFVItemName(cursor.getString(12));
				 
					// Adding contact to list
					dataList.add(contact);
				} while (cursor.moveToNext());
			}

			// return contact list
			return dataList;
		}
		
	//for remove favorite
		
		public void RemoveFav(VideoPojo contact)
		{
		    SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(TABLE_NAME, KEY_FAV_VID + " = ?",
		            new String[] { String.valueOf(contact.getFVItemId()) });
		    db.close();
		}
		
		public enum DatabaseManager {
			INSTANCE;
			private SQLiteDatabase db;
			private boolean isDbClosed =true;
			VideoDatabaseHandler dbHelper;
			public void init(Context context) {
				dbHelper = new VideoDatabaseHandler(context);
			if(isDbClosed){
			isDbClosed =false;
			this.db = dbHelper.getWritableDatabase();
			}

			}
 
			public boolean isDatabaseClosed(){
			return isDbClosed;
			}

			public void closeDatabase(){
			if(!isDbClosed && db != null){
			isDbClosed =true;
			db.close();
			dbHelper.close();
			}
			}
		}
}
