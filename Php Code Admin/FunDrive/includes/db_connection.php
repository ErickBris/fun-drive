<?php 
         error_reporting(0);
 		 ob_start();
		 #Session Start
		 session_start();
		 header("Content-Type: text/html;charset=UTF-8");
		 
		 date_default_timezone_set("Asia/kolkata");
		 
		 # Selects the database
		 
		 	 DEFINE ('DB_USER', 'root');
			 DEFINE ('DB_PASSWORD', '');
			 DEFINE ('DB_HOST', 'localhost'); //host name depends on server
			 DEFINE ('DB_NAME', 'entertainment');
  
		$mysqli = @mysql_connect (DB_HOST, DB_USER, DB_PASSWORD) OR die ('Could not connect to MySQL');
 
    @mysql_select_db (DB_NAME) OR die ('Could not select the database');
    
?>   