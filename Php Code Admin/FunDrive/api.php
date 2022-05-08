<?php include("includes/db_connection.php");

	mysql_query("SET NAMES 'utf8'"); 
	include('includes/function.php');
	
	if(isset($_GET['wallpaper_cat_id']))
	{
		$cat_id=$_GET['wallpaper_cat_id'];		
			$limit=($_GET['page']-1) * 10;
	
		$sel1="select wallpaper.id,wallpaper.wallpaper_rate_avg,wallpaper.cid,wallpaper_categories.category_name,wallpaper.wallpaper_image,
		wallpaper.tag,wallpaper.size,wallpaper.user,wallpaper.download_count from wallpaper INNER JOIN  wallpaper_categories ON wallpaper.cid = wallpaper_categories.cid where wallpaper.cid = '$cat_id' && status = '1' ";
	
		$sql1 = mysql_query($sel1);
	
		$number = mysql_num_rows($sql1); 
	
		$sel="select wallpaper.id,wallpaper.wallpaper_rate_avg,wallpaper.cid,wallpaper_categories.category_name,wallpaper.wallpaper_image,
		wallpaper.tag,wallpaper.size,wallpaper.user,wallpaper.download_count from wallpaper INNER JOIN  wallpaper_categories ON wallpaper.cid = wallpaper_categories.cid where wallpaper.cid = '$cat_id' && status = '1' ORDER BY wallpaper.id DESC  LIMIT $limit, 10  ";
		$jsonObj= array();
		$sql = mysql_query($sel);
		
		
		
		while($data = mysql_fetch_assoc($sql))
		{
			$row['num'] = $number;
			$row['id'] = $data['id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['wallpaper_image'] = $data['wallpaper_image'];
			$row['tag'] = $data['tag'];
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
			$row['wallpaper_rate_avg'] = $data['wallpaper_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	
	}
	elseif(isset($_GET['ringtone_cat_id']))
	{
		$cat_id=$_GET['ringtone_cat_id'];		
	
		$sel="select ringtone.ringtone_id,ringtone.ringtone_rate_avg,ringtone.cid,ringtone.ringtone_image,ringtones_category.category_name,ringtone.size,ringtone.tag,ringtone.user,
		ringtone.ringtone_name,ringtone.ringtone_url,ringtone.download_count from ringtone INNER JOIN  ringtones_category ON ringtone.cid = ringtones_category.cid
		 
		where ringtone.cid = '$cat_id' && ringtone_status = '1' ORDER BY ringtone.ringtone_id DESC";
		
          	$jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['ringtone_id'] = $data['ringtone_id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['ringtone_image'] = $data['ringtone_image'];
			$row['tag'] = $data['tag'];
			$row['size'] = $data['size'];

			$row['ringtone_name'] = $data['ringtone_name'];
			$row['ringtone_url'] = $data['ringtone_url'];

			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
			$row['ringtone_rate_avg'] = $data['ringtone_rate_avg'];
			array_push($jsonObj,$row);
		
			}
	
		  
		  $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
			
	
	}
	

	elseif(isset($_GET['task']) && ($_GET['task']) == 'appinfo')
	{
		$query="SELECT * FROM tbl_settings WHERE id='1' ";
	
	}
	elseif(isset($_GET['video_category']))
	{
		$query="SELECT * FROM video_category ";
	
	}
	
	elseif(isset($_GET['task']) && ($_GET['task']) == 'get_ringtone_cat' )
	{
		$query="SELECT cid,category_name,category_image FROM ringtones_category ORDER BY ringtones_category.cid";
		
		
		
	}
	elseif( ($_GET['task']) && ($_GET['task']) == 'recent_wallpaper' )
	{
			$sel="select wallpaper.id,wallpaper.wallpaper_rate_avg,wallpaper.cid,wallpaper_categories.category_name,wallpaper.wallpaper_image,wallpaper.tag,wallpaper.size,wallpaper.user,wallpaper.download_count from wallpaper INNER JOIN  wallpaper_categories ON wallpaper.cid = wallpaper_categories.cid where status  = '1' ORDER BY wallpaper.id   DESC LIMIT 10";
		$jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['wallpaper_image'] = $data['wallpaper_image'];
			$row['tag'] = $data['tag'];
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
			$row['wallpaper_rate_avg'] = $data['wallpaper_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	



	}
	elseif(($_GET['task']) && ($_GET['task']) == 'recent_ringtone')
	{
	$sel="select ringtone.ringtone_id,ringtone.cid,ringtone.ringtone_rate_avg,ringtone.ringtone_image,ringtones_category.category_name,ringtone.size,ringtone.tag,ringtone.user,ringtone.ringtone_name,ringtone.ringtone_url,ringtone.download_count from ringtone INNER JOIN  ringtones_category ON ringtone.cid = ringtones_category.cid 
where ringtone.ringtone_status  = '1' ORDER BY ringtone.ringtone_id   DESC LIMIT 10";
			$jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['ringtone_id'] = $data['ringtone_id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['tag'] = $data['tag'];
			$row['ringtone_image'] = $data['ringtone_image'];
			$row['size'] = $data['size'];

			$row['ringtone_name'] = $data['ringtone_name'];
			$row['ringtone_url'] = $data['ringtone_url'];

			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
						$row['ringtone_rate_avg'] = $data['ringtone_rate_avg'];
			array_push($jsonObj,$row);
		
			}
	
		  
		  $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();

		
	}
	elseif(isset($_GET['download']) && ($_GET['download']) == true  && isset($_GET['ringtone_id']) )
	{
		$id = $_GET['ringtone_id'];
		$sel = "select * from ringtone where ringtone_id=$id ";
		$sel1 = mysql_query($sel)or die(mysql_error());
		$data = mysql_fetch_assoc($sel1);
		$count = $data['download_count'] + 1;
		$data = array(											 
					'download_count'  =>  $count
				);
		$category_edit=Update('ringtone', $data, "WHERE ringtone_id = '".$id."'")or die(mysql_error());	
		$set['entertainment'][] = array(											 
					'download'  =>  $count
				);
		echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));

		
				die();

		
	}
	elseif(isset($_GET['download']) && ($_GET['download']) == true  && isset($_GET['wallpaper_id']))
	{
		$id = $_GET['wallpaper_id'];
		$sel = "select * from wallpaper where id=$id ";
		$sel1 = mysql_query($sel)or die(mysql_error());
		$data = mysql_fetch_assoc($sel1);
		$count = $data['download_count'] + 1;
		$data = array(											 

					'download_count'  =>  $count
				);
		$category_edit=Update('wallpaper', $data, "WHERE id = '".$id."'")or die(mysql_error());	
		$set['entertainment'][] = array(											 
					'download'  =>  $count
				);
		echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));


		die();
		
	}
	elseif(isset($_GET['task']) && ($_GET['task']) == "registration")
	{
		$user_name  = $_GET['username'];
		$email  = $_GET['email'];
		$pass  = $_GET['password'];
		
		if($email)
     {
      //Check duplicate entry
      $qry="SELECT * FROM tbl_users WHERE email='".$_GET['email']."'";
      $result=mysql_query($qry);
      $row=mysql_fetch_assoc($result);
      
      if($row['email']==$_GET['email'])
      {
       $set['entertainment'][]=array('msg' => "Email address already used",'success'=>'0');
      }
      else
      {
       
          $data = array(            
               'user_name'  =>  $_GET['fullname'],
              'email'  =>  $_GET['email'],
              'password'  => $_GET['password'],
               );  
      
     $qry = Insert('tbl_users',$data); 
       
     $set['entertainment'][]=array('msg' => 'Registration SuccessFull','Success'=>'1');
        }
	      echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
	die();
     }
		
	 
		
	}
	

	elseif(isset($_GET['task']) && ($_GET['task']) == "login")
	{
		$email  = $_GET['email'];
		$pass  = $_GET['password'];
		
		$sel = mysql_query("select * from tbl_users where email = '".$email."' && password = '".$pass."'  ");
		if(mysql_num_rows($sel) > 0)
		{
			$sel = "select user_id,user_name,email from tbl_users where email = '".$email."' && password = '".$pass."'  ";
			$sql = mysql_query($sel);
			$user = mysql_fetch_assoc($sql);
			$user_id = $user['user_id'];
			$email = $user['email'];
			$user_name = $user['user_name'];
			$set['entertainment'][]=array('Success'=>'1','user_id'=>$user_id,'user_name'=>$user_name,'email'=>$email);
		
		
			echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			
			
			die();
				
		}
		else
		{
				$set['entertainment'][]=array('msg' => 'Email or Password wrong','Success'=>'0');
				echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
				die();
		}
		
	}
	
	elseif(isset($_GET['task']) && ($_GET['task']) == "forgot_password")
	{
				$email  = $_GET['email'];
				$sel = mysql_query("select * from tbl_users where email = '".$email."' ");
		if(mysql_num_rows($sel) > 0)
		{
			$data = mysql_fetch_assoc($sel);
			$to = $data['email'];
			$subject = "entertainment Reset Password";
			$pass = $data['password'];
			$message = "You Email Is :  ".$to." Your Password Is :  ".$pass;
			mail($to,$subject,$message,$headers);

			$set['entertainment'][]=array('msg' => 'Your Password Hase Been Send Please Check Your Email '.$to,'Success'=>'1');
			echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();	
		}	
		else
		{
					 $set['entertainment'][]=array('msg' => 'User Not Exiest Please Create Account','Success'=>'0');
				echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
				die();
		
		}	
				

		
	}
		elseif(isset($_GET['most_rated_ringtone']))
		{

$sel="select ringtone.ringtone_id,ringtone.cid,ringtone.ringtone_rate_avg,ringtone.ringtone_image,ringtones_category.category_name,ringtone.size,ringtone.tag,ringtone.user,ringtone.ringtone_name,ringtone.ringtone_url,ringtone.download_count from ringtone INNER JOIN  ringtones_category ON ringtone.cid = ringtones_category.cid 
where ringtone.ringtone_status  = '1' ORDER BY ringtone.ringtone_rate_avg   DESC";
			$jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['ringtone_id'] = $data['ringtone_id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['tag'] = $data['tag'];
			$row['ringtone_image'] = $data['ringtone_image'];
			$row['size'] = $data['size'];

			$row['ringtone_name'] = $data['ringtone_name'];
			$row['ringtone_url'] = $data['ringtone_url'];

			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
						$row['ringtone_rate_avg'] = $data['ringtone_rate_avg'];
			array_push($jsonObj,$row);
		
			}
	
		  
		  $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
		
		}

	elseif(isset($_GET['more_apps']))
	{
				$query="SELECT * FROM more_apps ORDER BY id desc";

	}
	elseif(isset($_GET['live_wallpaper']))
	{
				$query="SELECT * FROM live_wallpaper ORDER BY id desc";

	}	
	elseif(isset($_GET['most_rated_wallpaper']))
	{ 
			$sel="select wallpaper.id,wallpaper.wallpaper_rate_avg,wallpaper.cid,wallpaper_categories.category_name,
			wallpaper.wallpaper_image,wallpaper.tag,wallpaper.size,wallpaper.user,wallpaper.download_count from wallpaper
			 INNER JOIN  wallpaper_categories ON wallpaper.cid = wallpaper_categories.cid where status  = '1' 
			 ORDER BY wallpaper.wallpaper_rate_avg   DESC ";
		$jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['wallpaper_image'] = $data['wallpaper_image'];
			$row['tag'] = $data['tag'];
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
			$row['wallpaper_rate_avg'] = $data['wallpaper_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	
			
	}
	elseif(isset($_GET['page']))
	{
		
		    $query_rec = "SELECT COUNT(*) as num FROM wallpaper_categories";
			$total_pages = mysql_fetch_array(mysql_query($query_rec));
			
		
			$limit=($_GET['page']-1) * 10;
			$query="SELECT cid,category_name,category_image FROM wallpaper_categories  ORDER BY cid DESC  LIMIT $limit, 10";
	 	
		
			$resouter = mysql_query($query);
     
   			$set = array();
     
   			$total_records = mysql_numrows($resouter);
  			  if($total_records >= 1){
     
    			  while ($link = mysql_fetch_array($resouter, MYSQL_ASSOC)){
	   
		 				$link1=array_merge((array)$total_pages, (array)$link);
        				$set['entertainment'][] = $link1;
	     			
				
	 			 }
				 	echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));

			}
			die();
	
	 }
	 elseif(isset($_GET['ringtone_id']))
	{
		$id = $_GET['ringtone_id'];
		$sel="select ringtone.ringtone_id,ringtone.cid,ringtone.ringtone_image,ringtone.ringtone_rate_avg,ringtones_category.category_name,ringtone.size,ringtone.tag,ringtone.user,ringtone.ringtone_name,ringtone.ringtone_url,ringtone.download_count from ringtone INNER JOIN  ringtones_category ON ringtone.cid = ringtones_category.cid 
where ringtone.ringtone_id  = $id ";
			$jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['ringtone_id'] = $data['ringtone_id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['tag'] = $data['tag'];
			$row['ringtone_image'] = $data['ringtone_image'];
			$row['size'] = $data['size'];

			$row['ringtone_name'] = $data['ringtone_name'];
			$row['ringtone_url'] = $data['ringtone_url'];

			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
						$row['ringtone_rate_avg'] = $data['ringtone_rate_avg'];
			array_push($jsonObj,$row);
		
			}
	
		  
		  $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
 
	}
	elseif(isset($_GET['wallpaper_id']))
	{	$id= $_GET['wallpaper_id'];
				$sel="select wallpaper.id,wallpaper.wallpaper_rate_avg,wallpaper.cid,wallpaper_categories.category_name,wallpaper.wallpaper_image,wallpaper.tag,wallpaper.size,wallpaper.user,wallpaper.download_count from wallpaper INNER JOIN  wallpaper_categories ON wallpaper.cid = wallpaper_categories.cid 
				where wallpaper.id  = $id";
		$jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['wallpaper_image'] = $data['wallpaper_image'];
			$row['tag'] = $data['tag'];
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
			$row['wallpaper_rate_avg'] = $data['wallpaper_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	
		
	}
	elseif(isset($_GET["feedback"]))
	{
	 
          $data = array(            
               'name'  =>  $_GET['name'],
              'email'  =>  $_GET['email'],
              'message'  => $_GET['message'],
               );  
      
     $qry = Insert('tbl_feedback',$data); 
       
     $set['entertainment'][]=array('msg' => 'Add Feedback SuccessFull','Success'=>'1');
	 
    	     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	  		
	}
	elseif(isset($_GET['ringtone_search']))
	{
	$title = $_GET['ringtone_search']; 	
		$sel="select ringtone.ringtone_id,ringtone.cid,ringtone.ringtone_rate_avg,ringtone.ringtone_image,ringtones_category.category_name,ringtone.size,ringtone.tag,ringtone.user,ringtone.ringtone_name,ringtone.ringtone_url,ringtone.download_count from ringtone INNER JOIN  ringtones_category ON ringtone.cid = ringtones_category.cid 
where ringtone.ringtone_status  = '1' && ringtone.ringtone_name like '%$title%'  ORDER BY ringtone.ringtone_id   DESC LIMIT 10";
			$jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['ringtone_id'] = $data['ringtone_id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['tag'] = $data['tag'];
			$row['ringtone_image'] = $data['ringtone_image'];
			$row['size'] = $data['size'];

			$row['ringtone_name'] = $data['ringtone_name'];
			$row['ringtone_url'] = $data['ringtone_url'];

			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
						$row['ringtone_rate_avg'] = $data['ringtone_rate_avg'];
			array_push($jsonObj,$row);
		
			}
	
		  
		  $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();

	
	}
	elseif(($_GET['task']) && ($_GET['task']) == 'recent_video')
			{
					$id = $_GET['video_cat_id'];
			 $sel="SELECT * FROM video r,video_category c WHERE r.cid=c.cid && r.status = '1' ORDER BY r.id desc LIMIT 10";
			 $jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['video_type'] = $data['video_type'];
			
			$row['cid']  = $data['cid'];
			$row['video_name'] = $data['video_name'];
			$row['category_name']   = $data['category_name'];
			$row['t_image'] = $data['t_image'];
			$row['tag'] = $data['tag'];
			$row['video_url'] = $data['video_url'];
			$row['video_id'] = $data['video_id'];
			
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['video_rate_avg'] = $data['video_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	

			
			
			}
			
	elseif(isset($_GET['videosearch']))
	{
		$key = $_GET['videosearch'];
			 $sel="SELECT * FROM video r,video_category c WHERE r.cid=c.cid && r.status = '1' && r.video_name like'%$key%' ";
			 $jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['video_type'] = $data['video_type'];
			
			$row['cid']  = $data['cid'];
			$row['video_name'] = $data['video_name'];
			$row['category_name']   = $data['category_name'];
			$row['t_image'] = $data['t_image'];
			$row['tag'] = $data['tag'];
			$row['video_url'] = $data['video_url'];
			$row['video_id'] = $data['video_id'];
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['video_rate_avg'] = $data['video_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	
	
	}
	elseif(isset($_GET['wallpaper_search']))
	{
		$title = $_GET['wallpaper_search'];
			$sel="select wallpaper.id,wallpaper.wallpaper_rate_avg,wallpaper.cid,wallpaper_categories.category_name,wallpaper.wallpaper_image,wallpaper.tag,wallpaper.size,wallpaper.user,wallpaper.download_count from wallpaper INNER JOIN  wallpaper_categories ON wallpaper.cid = wallpaper_categories.cid where status  = '1' && wallpaper.tag like '%$title%' ORDER BY wallpaper.id   DESC LIMIT 10";
		$jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['cid']  = $data['cid'];
			$row['category_name']   = $data['category_name'];
			$row['wallpaper_image'] = $data['wallpaper_image'];
			$row['tag'] = $data['tag'];
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['download_count'] = $data['download_count'];
			$row['wallpaper_rate_avg'] = $data['wallpaper_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	


		
	}
	elseif(isset($_GET['token']))
	{
		
		     $data = array(            
               'token'  =>  $_GET['token'],
               );  
      
     $qry = Insert('tbl_push_notification',$data); 
       
              $set['entertainment'][]=array('msg' => 'Success','Success'=>'1');
				echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
				die();
			
	}
	elseif(isset($_GET['video_cat_id']))
	{
	$id = $_GET['video_cat_id'];
			 $sel="SELECT * FROM video r,video_category c WHERE r.cid=c.cid && r.status = '1' && r.cid='$id' ORDER BY r.id desc";
			 $jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['video_type'] = $data['video_type'];
			
			$row['cid']  = $data['cid'];
			$row['video_name'] = $data['video_name'];
			$row['category_name']   = $data['category_name'];
			$row['t_image'] = $data['t_image'];
			$row['tag'] = $data['tag'];
			$row['video_url'] = $data['video_url'];
			$row['video_id'] = $data['video_id'];
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['video_rate_avg'] = $data['video_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	

			 
			 

	
	}
	elseif(isset($_GET['video']) && ($_GET['video']) == 'popular' )
	{
			 $sel="SELECT * FROM video r,video_category c WHERE r.cid=c.cid && r.status = '1' ORDER BY r.video_rate_avg DESC";
			 $jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['video_type'] = $data['video_type'];
			
			$row['cid']  = $data['cid'];
			$row['video_name'] = $data['video_name'];
			$row['category_name']   = $data['category_name'];
			$row['t_image'] = $data['t_image'];
			$row['tag'] = $data['tag'];
			$row['video_url'] = $data['video_url'];
			$row['video_id'] = $data['video_id'];
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['video_rate_avg'] = $data['video_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
	

	
	}
	elseif(isset($_GET['feature_video']))
	{
	
	
	$slider = mysql_query("select * from feature_video")or die(mysql_error()) ;
	$slider1 = mysql_fetch_assoc($slider);
	$jsonObj = array();						
	$str = explode(',',$slider1['video']);
				foreach($str as $simage)
								{
								$query = "SELECT * FROM video r,video_category c WHERE r.cid=c.cid && r.status = '1' && r.id ='$simage'";
		 		$resouter = mysql_query($query);
     
    $set = array();
     
     
      while ($link = mysql_fetch_array($resouter, MYSQL_ASSOC)){
	   $row = $link;
       
      }
     array_push($jsonObj,$row);
		
    

	
	}
	  $set['entertainment'] = $jsonObj;
     echo $val= str_replace('\\/', '/', json_encode($set));

	die();
	
	
	
	}
	
		elseif(isset($_GET['feature_wallpaper']))
	{
	
	
	$slider = mysql_query("select * from feature_wallpaper")or die(mysql_error()) ;
	$slider1 = mysql_fetch_assoc($slider);
	$jsonObj = array();						
	$str = explode(',',$slider1['wallpaper']);
				foreach($str as $simage)
								{
								$query = "select wallpaper.id,wallpaper.wallpaper_rate_avg,wallpaper.cid,wallpaper_categories.category_name,wallpaper.wallpaper_image,wallpaper.tag,wallpaper.size,wallpaper.user,wallpaper.download_count from wallpaper INNER JOIN  wallpaper_categories ON wallpaper.cid = wallpaper_categories.cid 
				where wallpaper.id ='$simage'";
		 		$resouter = mysql_query($query);
     
    $set = array();
     
     
      while ($link = mysql_fetch_array($resouter, MYSQL_ASSOC)){
	   $row = $link;
       
      }
     array_push($jsonObj,$row);
		
    

	
	}
	  $set['entertainment'] = $jsonObj;
     echo $val= str_replace('\\/', '/', json_encode($set));

	die();
	
	
	
	}
	elseif(isset($_GET['slider']))
	{
				$query="SELECT * FROM tbl_slider ";

	
	}
	elseif(isset($_GET['feature_ringtone']))
	{
	
	
	$slider = mysql_query("select * from feature_ringtone")or die(mysql_error()) ;
	$slider1 = mysql_fetch_assoc($slider);
	$jsonObj = array();						
	$str = explode(',',$slider1['ringtone']);
				foreach($str as $simage)
								{
								$query = "select ringtone.ringtone_id,ringtone.cid,ringtone.ringtone_image,ringtone.ringtone_rate_avg,ringtones_category.category_name,ringtone.size,ringtone.tag,ringtone.user,ringtone.ringtone_name,ringtone.ringtone_url,ringtone.download_count from ringtone INNER JOIN  ringtones_category ON ringtone.cid = ringtones_category.cid 
where ringtone.ringtone_id  ='$simage'";
		 		$resouter = mysql_query($query);
     
    $set = array();
     
     
      while ($link = mysql_fetch_array($resouter, MYSQL_ASSOC)){
	   $row = $link;
       
      }
     array_push($jsonObj,$row);
		
    

	
	}
	  $set['entertainment'] = $jsonObj;
     echo $val= str_replace('\\/', '/', json_encode($set));

	die();
	
	
	
	}


	
	
	elseif(isset($_GET['video_id']))
		{
		
			$id = $_GET['video_id'];
	
			$sel="SELECT * FROM video r,video_category c WHERE r.cid=c.cid && r.status = '1' && r.id = '$id'";
			 $jsonObj= array();
		$sql = mysql_query($sel);
		while($data = mysql_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['video_type'] = $data['video_type'];
			
			$row['cid']  = $data['cid'];
			$row['video_name'] = $data['video_name'];
			$row['category_name']   = $data['category_name'];
			$row['t_image'] = $data['t_image'];
			$row['tag'] = $data['tag'];
			$row['video_url'] = $data['video_url'];
			$row['video_id'] = $data['video_id'];
			$row['size'] = $data['size'];
			if($data['user'] == 'admin')
			{
				
			$row['user'] = $data['user'];
			}
			else
			{
				$id = $data['user'];
				$userinfo = "select * from tbl_users where user_id = '".$id."'";
				$user = mysql_query($userinfo);
				$username = mysql_fetch_assoc($user);
				$row['user'] = $username['user_name'] ;
			}
			$row['video_rate_avg'] = $data['video_rate_avg'];

			array_push($jsonObj,$row);
		
			}
			        $set['entertainment'] = $jsonObj;
			
		     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
			die();
		
	}
	 else
  	 {
		
		$query="SELECT cid,category_name,category_image FROM wallpaper_categories  ORDER BY cid DESC";
	 	
		
		
	  
	}
 	
	 
	$resouter = mysql_query($query);
    $set = array();
     
    $total_records = mysql_numrows($resouter);
    if($total_records >= 1){
     
      while ($link = mysql_fetch_array($resouter, MYSQL_ASSOC)){
	   
        $set['entertainment'][] = $link;
      }
    }
	
 		 header( 'Content-Type: application/json; charset=utf-8' );

     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
?>