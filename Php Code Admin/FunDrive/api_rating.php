<?php include("includes/db_connection.php");

	mysql_query("SET NAMES 'utf8'"); 
	include('includes/function.php');
	
	
    
    if(isset($_GET['rated']) && ($_GET['rated']) == "wallpaper" )
	{
		 
    	//search if the user(ip) has already gave a note
    	$ip = $_GET['device_id'];
    	$therate = $_GET['rate'];
    	$wallpapar_id = $_GET['wallpaper_id'];
	
    	$query1 = mysql_query("select * from tbl_wallpaper_rating where wallpaper_id  = '$wallpapar_id' && ip = '$ip' "); 
    	while($data1 = mysql_fetch_assoc($query1)){
    		$rate_db1[] = $data1;
    	}
		
		
		
    	if(@count($rate_db1) == 0 ){
			
    		     $data = array(            
               'wallpaper_id'  =>$wallpapar_id ,
              'rate'  =>  $therate,
              'ip'  => $ip,
               );  
      
     		$qry = Insert('tbl_wallpaper_rating',$data); 
     	
					//Total rate result
					 
				$query = mysql_query("select * from tbl_wallpaper_rating where wallpaper_id  = '$wallpapar_id' ");
               
			   while($data = mysql_fetch_assoc($query)){
                  	$rate_db[] = $data;
                    $sum_rates[] = $data['rate'];
                }
				
                if(@count($rate_db)){
                    $rate_times = count($rate_db);
                    $sum_rates = array_sum($sum_rates);
                    $rate_value = $sum_rates/$rate_times;
                    $rate_bg = (($rate_value)/5)*100;
                }else{
                    $rate_times = 0;
                    $rate_value = 0;
                    $rate_bg = 0;
                }
				 
				$rate_avg=round($rate_value); 
				
			//	mysql_query("UPDATE tbl_wallpaper SET wallpaper_total_rate= '$rate_times',wallpaper_rate_avg= '$rate_avg' WHERE wallpaper_id= '$wallpapar_id'");
				$data = array(											 
					'wallpaper_total_rate'  =>  $rate_times,
					'wallpaper_rate_avg'  =>  $rate_avg
				);
		$category_edit=Update('wallpaper', $data, "WHERE id = '".$wallpapar_id."'")or die(mysql_error());	
		
				echo '{"entertainment":[{"MSG":"You have succesfully rated"}]}';
				
    	}else{
    						
				echo '{"entertainment":[{"MSG":"You have already rated"}]}';
    	}
    
		
	}
	elseif(isset($_GET['rated']) && ($_GET['rated']) == "video")
	{
			
		 
    	//search if the user(ip) has already gave a note
    	$ip = $_GET['device_id'];
    	$therate = $_GET['rate'];
    	$video_id = $_GET['video_id'];
	
    	$query1 = mysql_query("select * from tbl_video_rating where video_id  = '$video_id' && ip = '$ip' "); 
    	while($data1 = mysql_fetch_assoc($query1)){
    		$rate_db1[] = $data1;
    	}
		
		
		
    	if(@count($rate_db1) == 0 ){
			
    		     $data = array(            
               'video_id'  =>$video_id ,
              'rate'  =>  $therate,
              'ip'  => $ip,
               );  
      
     		$qry = Insert('tbl_video_rating',$data); 
     	
					//Total rate result
					 
				$query = mysql_query("select * from tbl_video_rating where video_id  = '$video_id' ");
               
			   while($data = mysql_fetch_assoc($query)){
                  	$rate_db[] = $data;
                    $sum_rates[] = $data['rate'];
                }
				
                if(@count($rate_db)){
                    $rate_times = count($rate_db);
                    $sum_rates = array_sum($sum_rates);
                    $rate_value = $sum_rates/$rate_times;
                    $rate_bg = (($rate_value)/5)*100;
                }else{
                    $rate_times = 0;
                    $rate_value = 0;
                    $rate_bg = 0;
                }
				 
				$rate_avg=round($rate_value); 
				
			//	mysql_query("UPDATE tbl_wallpaper SET wallpaper_total_rate= '$rate_times',wallpaper_rate_avg= '$rate_avg' WHERE wallpaper_id= '$wallpapar_id'");
				$data = array(											 
					'video_total_rate'  =>  $rate_times,
					'video_rate_avg'  =>  $rate_avg
				);
		$category_edit=Update('video', $data, "WHERE id = '".$video_id."'")or die(mysql_error());	
		
				echo '{"entertainment":[{"MSG":"You have succesfully rated"}]}';
				
    	}else{
    						
				echo '{"entertainment":[{"MSG":"You have already rated"}]}';
    	}
    
		
	
	}
	elseif(isset($_GET['rated']) && ($_GET['rated']) == "ringtone")
	{
			//search if the user(ip) has already gave a note
    	$ip = $_GET['device_id'];
    	$therate = $_GET['rate'];
    	$ringtone_id = $_GET['ringtone_id'];
	
    	$query1 = mysql_query("SELECT * FROM tbl_ringtone_rating where ip = '$ip' and  ringtone_id= '$ringtone_id'"); 
    	while($data1 = mysql_fetch_assoc($query1)){
    		$rate_db1[] = $data1;
    	}
		
    	if(@count($rate_db1) == 0 ){
			
    		     $data = array(            
               'ringtone_id'  =>$ringtone_id ,
              'rate'  =>  $therate,
              'ip'  => $ip,
               );  
      
     		$qry = Insert('tbl_ringtone_rating',$data); 
     	
					//Total rate result
					 
				$query = mysql_query("select * from tbl_ringtone_rating where ringtone_id  = '$ringtone_id' ");
               
			   while($data = mysql_fetch_assoc($query)){
                  	$rate_db[] = $data;
                    $sum_rates[] = $data['rate'];
                }
				
                if(@count($rate_db)){
                    $rate_times = count($rate_db);
                    $sum_rates = array_sum($sum_rates);
                    $rate_value = $sum_rates/$rate_times;
                    $rate_bg = (($rate_value)/5)*100;
                }else{
                    $rate_times = 0;
                    $rate_value = 0;
                    $rate_bg = 0;
                }
				 
				$rate_avg=round($rate_value); 
				
			//	mysql_query("UPDATE tbl_wallpaper SET wallpaper_total_rate= '$rate_times',wallpaper_rate_avg= '$rate_avg' WHERE wallpaper_id= '$wallpapar_id'");
				$data = array(											 
					'ringtone_total_rate'  =>  $rate_times,
					'ringtone_rate_avg'  =>  $rate_avg
				);
		$category_edit=Update('ringtone', $data, "WHERE ringtone_id = '".$ringtone_id."'")or die(mysql_error());	
		
				echo '{"entertainment":[{"MSG":"You have succesfully rated"}]}';
				
    	}else{
    						
				echo '{"entertainment":[{"MSG":"You have already rated"}]}';
    	}

	}
	else
	{
		echo '{"entertainment":[{"MSG":"Error"}]}';
		
	}
?>