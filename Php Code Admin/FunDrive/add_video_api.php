<?php 
	include('includes/db_connection.php');   
	include('includes/function.php');
	include('messages/messages.php'); 
 
		mysql_query("SET NAMES 'utf8'");	
	  				
					
					if($_GET['user_id'])
					{
						$video_id = '000q1w2';
						 		if($_GET['upload_type'] == 'Local_Upload')
								{
									$t_image=basename( $_GET['image_path']);
									$video=basename( $_GET['video_path']);
								}
								else
								{
									$t_image = '';
									$video = $_GET['youtube_url'];
									
											function youtube_id_from_url($url) {
    $pattern = 
        '%^# Match any youtube URL
        (?:https?://)?  # Optional scheme. Either http or https
        (?:www\.)?      # Optional www subdomain
        (?:             # Group host alternatives
          youtu\.be/    # Either youtu.be,
        | youtube\.com  # or youtube.com
          (?:           # Group path alternatives
            /embed/     # Either /embed/
          | /v/         # or /v/
          | /watch\?v=  # or /watch\?v=
          )             # End path alternatives.
        )               # End host alternatives.
        ([\w-]{10,12})  # Allow 10-12 for 11 char youtube id.
        $%x'
        ;
    $result = preg_match($pattern, $url, $matches);
    if (false !== $result) {
        return $matches[1];
    }
	
    return false;
}


		
		
		$video_id = youtube_id_from_url($_GET['youtube_url']);

									
									
									
								}
								
		    						                 $data = array(
														'cid'  =>  $_GET['cat_id'],
														'video_name'=>$_GET['video_name'],
														
														'video_type' => $_GET['upload_type'],
														'video_url'  => $video,
														't_image' =>$t_image,
														'status' => '0',
														'video_id' => $video_id,

														'tag'=>$_GET['tag'],
														'size'=>$_GET['video_size'],
														'user'=>$_GET['user_id'],
														);		
 					  $qry = Insert('video',$data);									
  		$set['entertainment'][]=array('msg' => $admin_lang['42'],'success'=>'1');
								}
					elseif(isset($_GET['video']))
					{
						
										$file_folder = "upload/video/";
    
										 echo $file_name= $_FILES['video']['name'];
											
											$file_path = $file_folder.$file_name;
											if(move_uploaded_file($_FILES['video']['tmp_name'], $file_path))
											{
													
													$full_path = 'http://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/'.$file_folder;
													$image_url = $full_path.$file_name;
													
		$bytes = $_FILES['video']['size'];
	   
	    
        if ($bytes >= 1073741824)
        {
            $bytes = number_format($bytes / 1073741824, 2) . ' GB';
        }
        elseif ($bytes >= 1048576)
        {
            $bytes = number_format($bytes / 1048576, 2) . ' MB';
        }
        elseif ($bytes >= 1024)
        {
            $bytes = number_format($bytes / 1024, 2) . ' KB';
        }
        elseif ($bytes > 1)
        {
            $bytes = $bytes . ' bytes';
        }
        elseif ($bytes == 1)
        {
            $bytes = $bytes . ' byte';
        }
        else
        {
            $bytes = '0 bytes';
        }
		

										
													$set['video_url'][] = array('video_path' => $image_url , 'image_size' => $bytes );
													
											}
											else
											{
													$set['video_url'][] = array('video_path' => "fail");
											}
										   
					
					
					
					
					}
					else
					{
					
									
											$file_folder = "upload/";
    
										  $file_name= $_FILES['uploaded_file']['name'];
											
											$file_path = $file_folder.$file_name;
											if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path))
											{
													
													$full_path = 'http://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/'.$file_folder;
													$image_url = $full_path.$file_name;
													
		$bytes = $_FILES['uploaded_file']['size'];
	   
	    
        if ($bytes >= 1073741824)
        {
            $bytes = number_format($bytes / 1073741824, 2) . ' GB';
        }
        elseif ($bytes >= 1048576)
        {
            $bytes = number_format($bytes / 1048576, 2) . ' MB';
        }
        elseif ($bytes >= 1024)
        {
            $bytes = number_format($bytes / 1024, 2) . ' KB';
        }
        elseif ($bytes > 1)
        {
            $bytes = $bytes . ' bytes';
        }
        elseif ($bytes == 1)
        {
            $bytes = $bytes . ' byte';
        }
        else
        {
            $bytes = '0 bytes';
        }
		

										
													$set['t_image_url'][] = array('image_path' => $image_url , 'image_size' => $bytes );
													
											}
											else
											{
													$set['t_image_url'][] = array('image_path' => "fail");
											}
										   
					}			
						 
 
	
	header( 'Content-Type: application/json; charset=utf-8' );
	$json = json_encode($set);
				
	echo $json;
	
	 
?>
                    