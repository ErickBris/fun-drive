package com.example.util;

import java.io.Serializable;

public class Constant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//all images path
	public static final String SERVER_IMAGE_UPFOLDER_CATEGORY="http://www.viaviweb.in/Apps/FunDrive/upload/";

	//all thumb images path
	public static final String SERVER_IMAGE_UPFOLDER_THUMB="http://www.viaviweb.in/Apps/FunDrive/upload/";

	//Ringtone folder path
	public static final String SERVER_RINGTONE_FOLDER="http://www.viaviweb.in/Apps/FunDrive/ringtone/";

	//Ringtone image path
	public static final String RING_IMAGE_FOLDER_PATH="http://www.viaviweb.in/Apps/FunDrive/ringtone/image/";


	//Recent wallpaper
	public static final String LATEST_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?task=recent_wallpaper";

	//this url gives list of category in 2nd tab
	public static final String CATEGORY_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?page=";
	
	public static final String CATEGORY_URL1 = "http://www.viaviweb.in/Apps/FunDrive/api.php";

	//this url gives item of specific category.
	public static final String CATEGORY_ITEM_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?wallpaper_cat_id=";

	public static final String RINGLATEST_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?task=recent_ringtone";

	public static final String RINGCATEGORY_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?task=get_ringtone_cat";

	public static final String RINGCATEGORYITEM_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?ringtone_cat_id=";

	//this url gives your company details
	public static final String COMPANY_DETAILS_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?task=appinfo";
  
	public static final String DOWNLOADWALL_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?download=true&wallpaper_id=";
  
	public static final String DOWNLOADRING_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?download=true&ringtone_id=";

	public static final String REGISTER_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?task=registration";

	//Login
	public static final String LOGIN_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?task=login";

	//Forgot
	public static final String FORGOTPASS_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?task=forgot_password";
	
	//Rate Wall paper
	public static final String RATE_WALLPAPER_URL = "http://www.viaviweb.in/Apps/FunDrive/api_rating.php?rated=wallpaper&device_id=";
	
	//Rate Ring Tone
	public static final String RATE_RINGTONE_URL = "http://www.viaviweb.in/Apps/FunDrive/api_rating.php?rated=ringtone&device_id=";
	
	//Most Popular Wallpaper
	public static final String MOST_POPULAR_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?most_rated_wallpaper";
	
	//Most Popular Ringtone
	public static final String MOST_POPULAR_RING_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?most_rated_ringtone";

	//More Apps Custom
	public static final String MORE_APPS_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?more_apps";
	
	//Live Wall paper
	public static final String LWP_WEB_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?live_wallpaper";
	
	//Add Wallpaper
	public static final String ADD_WALLPAPER_URL = "http://www.viaviweb.in/Apps/FunDrive/add_wallpaper_api.php?user_id=";
	
	//Add Ringtone
	public static final String ADD_RINGTONE_URL = "http://www.viaviweb.in/Apps/FunDrive/add_ringtone_api.php?user_id=";
	
	//Wall Paper Single
	public static final String SINGLE_WALLPAPER_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?wallpaper_id=";
	
	//Ring Tone Single
	public static final String SINGLE_RINGTONE_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?ringtone_id=";

	// Application Register to GCM 
	public static final String APP_GCM_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?token=";
	
	//Feedback 
	public static final String FEEDBACK_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?feedback&name=";
	
	//Search Wall paper
	public static final String SEARCH_WALLPAPER_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?wallpaper_search=";

	//Search Ring tone
	public static final String SEARCH_RINGTONE_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?ringtone_search=";
	
	//Latest Video
	public static final String VIDEO_LATEST_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?task=recent_video";
	
	//Video Category
	public static final String VIDEO_CATEGORY_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?video_category";
	
	//Video CategoryItem
	public static final String VIDEO_CATEGORYITEM_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?video_cat_id=";
	
	//Video Single
	public static final String VIDEO_SINGLE_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?video_id=";

	//Rate Ring Tone
	public static final String RATE_VIDEO_URL = "http://www.viaviweb.in/Apps/FunDrive/api_rating.php?rated=video&device_id=&video_id=";
		
	//this is the path of uploaded image of server where image store
	public static final String VIDEO_FOLDER_PATH="http://www.viaviweb.in/Apps/FunDrive/upload/video/";
	
	//most popular video
	public static final String MOST_POPULAR_VIDEO_URL="http://www.viaviweb.in/Apps/FunDrive/api.php?video=popular";

	//search video
	public static final String SEARCH_VIDEO_URL="http://www.viaviweb.in/Apps/FunDrive/api.php?videosearch=";

	//Video Category
	public static final String VIDEO_CATEGORYSPIN_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?video_category";
	
	//Add video
	public static final String ADD_VIDEO_URL = "http://www.viaviweb.in/Apps/FunDrive/add_video_api.php?user_id=";
		
	//Upload video thumb
	public static final String UPLOAD_VIDEOTHUMB_URL = "http://www.viaviweb.in/Apps/FunDrive/add_video_api.php?uploaded_file=";
	
	//Upload video file
	public static final String UPLOAD_VIDEOFILE_URL = "http://www.viaviweb.in/Apps/FunDrive/add_video_api.php?video";
	
	//Upload ringtone file
	public static final String UPLOAD_RINTONEFILE_URL = "http://www.viaviweb.in/Apps/FunDrive/add_ringtone_api.php";

	//Upload ringtoneimage file
	public static final String UPLOAD_RINTONEIMG_URL = "http://www.viaviweb.in/Apps/FunDrive/add_ringtone_api.php?image";

	//Upload wallpaperimage file
	public static final String UPLOAD_WALLPAPERIMG_URL = "http://www.viaviweb.in/Apps/FunDrive/add_wallpaper_api.php";

	//Slider
	public static final String SLIDER_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?slider";
			
	//Feature Wallpaper
	public static final String F_WALLPAPER_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?feature_wallpaper";
		
	//Feature Ringtone
	public static final String F_RINGTONE_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?feature_ringtone";
		
	//Feature Video
	public static final String F_VIDEO_URL = "http://www.viaviweb.in/Apps/FunDrive/api.php?feature_video";

	//GCM Sender Id
	public static final String GCM_SENDER_ID="162842286728";

 	// youtube image path
	public static final String YOUTUBE_IMAGE_FRONT="http://img.youtube.com/vi/";
	public static final String YOUTUBE_IMAGE_BACK="/hqdefault.jpg";
	public static final String YOUTUBE_SMALL_IMAGE_BACK="/default.jpg";
	
	
	public static final String LATEST_ARRAY_NAME="entertainment";
	public static final String LATEST_IMAGE_CATEGORY_NAME="category_name";
	public static final String LATEST_IMAGE_URL="wallpaper_image";
	public static final String LATEST_IMAGE_WDCOUNTL="download_count";
	public static final String LATEST_IMAGE_WID="id";
	public static final String LATEST_IMAGE_WUSER="user";
	public static final String LATEST_IMAGE_WTAG="tag";
	public static final String LATEST_IMAGE_WSIZE="size";
	public static final String LATEST_IMAGE_STAR="wallpaper_rate_avg";

	public static final String CATEGORY_ARRAY_NAME="entertainment";
	public static final String CATEGORY_NAME="category_name";
	public static final String CATEGORY_CID="cid";
	public static final String CATEGORY_IMAGE_URL="category_image";

	//for title display in CategoryItem

	public static final String CATEGORY_ITEM_ARRAY ="entertainment";
	public static final String CATEGORY_ITEM_CATNAME ="category_name";
	public static final String CATEGORY_ITEM_IMAGEURL ="wallpaper_image";
	public static final String CATEGORY_ITEM_CATID ="cid";
	public static final String CATEGORY_ITEM_WDCOUNT ="download_count";
	public static final String CATEGORY_ITEM_ID ="id";
	public static final String CATEGORY_ITEM_USER ="user";
	public static final String CATEGORY_ITEM_TAG ="tag";
	public static final String CATEGORY_ITEM_SIZE ="size";
	public static final String CATEGORY_ITEM_STAR ="wallpaper_rate_avg";

	public static  String CATEGORY_ITEM_CATIDD;
	public static String CATEGORY_TITLE;
	public static String CATEGORY_ID;
	public static String CATEGORY_ITEMWALLID;
	public static String RINGTONE_ITEMID;
	

	public static final String LATESTRING_ARRAY_NAME="entertainment";
	public static final String LATESTRING_RINGID="ringtone_id";
	public static final String LATESTRING_RINGCATENAME="category_name";
	public static final String LATESTRING_RINGNAME="ringtone_name";
	public static final String LATESTRING_RINGURL="ringtone_url";
	public static final String LATESTRING_RINGCATID="cid";
	public static final String LATESTRING_RINGDOWNCOUNT="download_count";
	public static final String LATESTRING_RINGUSER="user";
	public static final String LATESTRING_RINGTAG="tag";
	public static final String LATESTRING_RINGSIZE="size";
	public static final String LATESTRING_RINGSTAR="ringtone_rate_avg";
	public static final String LATESTRING_RINGIMAGE="ringtone_image";

	public static final String CATERING_ARRAY_NAME="entertainment";
	public static final String CATERING_CATID="cid";
	public static final String CATERING_CATENAME="category_name";
	public static final String CATERING_CATEIMG="category_image";

	public static final String CATEITEMRING_RINDID="ringtone_id";
	public static final String CATEITEMRING_RINDCATID="cid";
	public static final String CATEITEMRING_CATENAME="category_name";
	public static final String CATEITEMRING_RINGNAME="ringtone_name";
	public static final String CATEITEMRING_RINDURL="ringtone_url";
	public static final String CATEITEMRING_RINDDOWNCOUNT="download_count";
	public static final String CATEITEMRING_RINDUSER="user";
	public static final String CATEITEMRING_RINDTAG="tag";
	public static final String CATEITEMRING_RINDSIZE="size";
	public static final String CATEITEMRING_RINDIMAGE="ringtone_image";

	public static final String DOWNLOAD_ARRAY="entertainment";
	public static final String DOWNLOAD_WALLMSG="download";
	public static final String DOWNLOAD_RINGMSG="download";
 
	public static  String RINGCATID;
	public static  String RINGCATNAME;
	public static  String RINGCATITEMID;
 
	public static final String COMPANY_DETAILS_ID="id";
	public static final String COMPANY_DETAILS_APPNAME="app_name";
	public static final String COMPANY_DETAILS_COMLOGO="app_logo";
	public static final String COMPANY_DETAILS_COMMAIL="app_email";
	public static final String COMPANY_DETAILS_COMSITE="app_website";
	public static final String COMPANY_DETAILS_COMDES="app_description";
	
	/*
	 * Video
	 */
	public static final String VLATEST_VID="id";
	public static final String VLATEST_VTYPE="video_type";
	public static final String VLATEST_VCID="cid";
	public static final String VLATEST_VCATNAME="category_name";
	public static final String VLATEST_VTHUMB="t_image";
	public static final String VLATEST_VTAG="tag";
	public static final String VLATEST_VURL="video_url";
	public static final String VLATEST_VSIZE="size";
	public static final String VLATEST_VUSER="user";
	public static final String VLATEST_VRATE="video_rate_avg";
	public static final String VLATEST_VVIDEOPLAYID="video_id";
	public static final String VLATEST_VVIDEONAME="video_name";
	 
	public static  String VIDEOCATITEMID;
	
	
	/*
	 * video category
	 */
	public static final String VCATEGORY_CID="cid";
	public static final String VCATEGORY_CNAME="category_name";
	public static final String VCATEGORY_CIMAGE="category_image";
	
	public static  String VIDEOCATID;
	public static  String VIDEOCATNAME;
	
	/*
	 * Video Category Item
	 */
	public static final String VCITEM_VID="id";
	public static final String VCITEM_VTYPE="video_type";
	public static final String VCITEM_VCID="cid";
	public static final String VCITEM_VCATNAME="category_name";
	public static final String VCITEM_VTHUMB="t_image";
	public static final String VCITEM_VTAG="tag";
	public static final String VCITEM_VURL="video_url";
	public static final String VCITEM_VSIZE="size";
	public static final String VCITEM_VUSER="user";
	public static final String VCITEM_VRATE="video_rate_avg";
	public static final String VCITEM_VVIDEOPLAYID="video_id";
	public static final String VCITEM_VVIDEONAME="video_name";
	
	public static String VCAT_ITEMID;
	public static String VIDEO_ITEMID;
	/*
	 * Register
	 */
	public static final String USER_REG_ARRAY="entertainment";
	public static final String USER_REG_MSG="msg";
	public static final String USER_REG_SUCESS="Success";


	/*
	 * Login
	 */


	public static final String USER_LOGIN_ARRAY="entertainment";
	public static final String USER_LOGIN_MSG="msg";
	public static final String USER_LOGIN_SUCESS="Success";
	public static final String USER_LOGIN_ID="user_id";
	public static final String USER_LOGIN_NAME="user_name";

	/*
	 * Forgot Password
	 */

	public static final String FORGOTPASS_ARRAY_NAME="entertainment";
	public static final String FORGOTPASS_MSG="msg";
	public static final String FORGOTPASS_SUCESS="Success";
	public static int GET_SUCCESS_MSG;
	
	/*
	 * Rate Dialog
	 */
	
	public static final String RATE_ARRAY_NAME="entertainment";
	public static final String RATE_MSG="MSG";
	
	/*
	 * More Application
	 */

	public static final String MOREAPP_ARRAY_NAME="entertainment";
	public static final String MOREAPP_NAME="name";
	public static final String MOREAPP_URL="url";
	public static final String MOREAPP_IMAGE="image";
	
	/*
	 * Live Wall paper
	 */

	public static final String LWP_ARRAY_NAME="entertainment";
	public static final String LWP_NAME="name";
	public static final String LWP_URL="url";
	public static final String LWP_IMAGE="image";
	
	
	/*
	 * Feedback
	 */
	public static final String FEEDBACK_ARRAY="entertainment";
	public static final String FEEDBACK_MSG="msg";
	
	/*
	 * Slider
	 */
	
	public static final String SLIDER_ARRAY="entertainment";
	public static final String SLIDER_NAME="name";
	public static final String SLIDER_IMAGE="image";
	public static final String SLIDER_LINK="link";
	
/*
 * Download folder path
 */
 	public static final String DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE="/FunDrive/Ringtone/";
	public static final String DOWNLOAD_SDCARD_FOLDER_PATH_WALLPAPER="/FunDrive/Wallpaper";
	
	
	public static final String CATEGORY_NUM="num";
	// Number of columns of Grid View
	public static final int NUM_OF_COLUMNS = 2;

	// Gridview image padding
	public static final int GRID_PADDING = 8; // in dp
	
	public static String DEVICE_ID,LOGIN_ID,LOGIN_FORM,SEARCH;

}
