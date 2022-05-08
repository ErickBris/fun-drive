package com.example.favorite;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RingDatabaseHandler extends SQLiteOpenHelper{
	
private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "AddtoFavrRing";
	
	private static final String TABLE_NAME = "FavoriteRing";
	private static final String KEY_ID = "id";
	private static final String KEY_RINGITEM_ID = "ringitemid";
	private static final String KEY_RINGITEM_NAME = "ringitemname";
	private static final String KEY_RINGITEM_CATNAME = "ringitemcatname";
	private static final String KEY_RINGITEM_URL = "ringitemurl";
	private static final String KEY_RINGITEM_CATID = "ringitemcatid";
	private static final String KEY_RINGITEM_DCOUNT = "ringitemdcount";
	private static final String KEY_RINGITEM_USER = "ringitemuser";
	private static final String KEY_RINGITEM_TAG = "ringitemtag";
	private static final String KEY_RINGITEM_IMG = "ringitemimg";
	private static final String KEY_RINGITEM_STAR = "ringitemstar";
	private static final String KEY_RINGITEM_SIZE = "ringitemsize";
	 
	 
	 

	public RingDatabaseHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
					+ KEY_ID + " INTEGER PRIMARY KEY," 
					+ KEY_RINGITEM_ID + " TEXT,"
					+ KEY_RINGITEM_NAME + " TEXT,"
					+ KEY_RINGITEM_CATNAME + " TEXT,"
					+ KEY_RINGITEM_URL + " TEXT,"
					+ KEY_RINGITEM_CATID + " TEXT,"
					+ KEY_RINGITEM_DCOUNT + " TEXT,"
					+ KEY_RINGITEM_USER + " TEXT,"
					+ KEY_RINGITEM_TAG + " TEXT,"
					+ KEY_RINGITEM_IMG + " TEXT,"
					+ KEY_RINGITEM_STAR + " TEXT,"
					+ KEY_RINGITEM_SIZE + " TEXT"
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
	
	public	void AddtoFavoritering(RingPojo pj)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(KEY_RINGITEM_ID, pj.getDRingItemId());
		values.put(KEY_RINGITEM_NAME, pj.getDRingItemName());
		values.put(KEY_RINGITEM_CATNAME, pj.getDRingItemCatName());
		values.put(KEY_RINGITEM_URL, pj.getDRingItemUrl());
		values.put(KEY_RINGITEM_CATID, pj.getDRingItemCatId());
		values.put(KEY_RINGITEM_DCOUNT, pj.getDRingItemDCount());
		values.put(KEY_RINGITEM_USER, pj.getDRingItemRUser());
		values.put(KEY_RINGITEM_TAG, pj.getDRingItemRTag());
		values.put(KEY_RINGITEM_SIZE, pj.getDRingItemRSize());
		values.put(KEY_RINGITEM_IMG, pj.getDRingItemRImag());
		values.put(KEY_RINGITEM_STAR, pj.getDRingItemRStar());
		
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
		
	}
	
	// Getting All Data
		public List<RingPojo> getAllData() 
		{
			List<RingPojo> dataList = new ArrayList<RingPojo>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_NAME;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) 
			{
				do {
					RingPojo contact = new RingPojo();
					//contact.setId(Integer.parseInt(cursor.getString(0)));
					contact.setDRingItemId(cursor.getString(1));
					contact.setDRingItemName(cursor.getString(2));
					contact.setDRingItemCatName(cursor.getString(3));
					contact.setDRingItemUrl(cursor.getString(4));
					contact.setDRingItemCatId(cursor.getString(5));
					contact.setDRingItemDCount(cursor.getString(6));
					contact.setDRingItemRUser(cursor.getString(7));
					contact.setDRingItemRTag(cursor.getString(8));
					contact.setDRingItemRSize(cursor.getString(11));
					contact.setDRingItemRImag(cursor.getString(9));
					contact.setDRingItemRStar(cursor.getString(10));
					 
				 
					// Adding contact to list
					dataList.add(contact);
				} while (cursor.moveToNext());
			}

			// return contact list
			return dataList;
		}
		
	//getting single row
		
		public List<RingPojo> getFavRow(String id) 
		{
			List<RingPojo> dataList = new ArrayList<RingPojo>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE ringitemid="+"'"+id+"'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) 
			{
				do {
					RingPojo contact = new RingPojo();
				//	contact.setId(Integer.parseInt(cursor.getString(0)));
					contact.setDRingItemId(cursor.getString(1));
					contact.setDRingItemName(cursor.getString(2));
					contact.setDRingItemCatName(cursor.getString(3));
					contact.setDRingItemUrl(cursor.getString(4));
					contact.setDRingItemCatId(cursor.getString(5));
					contact.setDRingItemDCount(cursor.getString(6));
					contact.setDRingItemRUser(cursor.getString(7));
					contact.setDRingItemRTag(cursor.getString(8));
					contact.setDRingItemRSize(cursor.getString(9));
					contact.setDRingItemRImag(cursor.getString(10));
					contact.setDRingItemRStar(cursor.getString(11));
				 
					// Adding contact to list
					dataList.add(contact);
				} while (cursor.moveToNext());
			}

			// return contact list
			return dataList;
		}
		
	//for remove favorite
		
		public void RemoveFav(RingPojo contact)
		{
		    SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(TABLE_NAME, KEY_RINGITEM_ID + " = ?",
		            new String[] { String.valueOf(contact.getDRingItemId()) });
		    db.close();
		}
		
		public enum DatabaseManager {
			INSTANCE;
			private SQLiteDatabase db;
			private boolean isDbClosed =true;
			RingDatabaseHandler dbHelper;
			public void init(Context context) {
				dbHelper = new RingDatabaseHandler(context);
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
