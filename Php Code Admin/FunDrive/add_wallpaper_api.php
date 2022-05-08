<?php 
	include('includes/db_connection.php');   
	include('includes/function.php');
	include('messages/messages.php'); 
 
		mysql_query("SET NAMES 'utf8'");	
	  				
					
					if($_GET['user_id'])
					{
					
						 
									$wallpaper=basename( $_GET['image_path']);
						
									$data = array(											 
												    	'cid'  =>  $_GET['cid'],
														'wallpaper_image'  =>  $wallpaper,
														'status'  =>  '0',
														'tag'  =>  $_GET['tag'],
 														'size'  =>  $_GET['size'],
														'user'  =>  $_GET['user_id']
														);		
					 
					  			$qry = Insert('wallpaper',$data);	
									 
									$set['ZedgeApp'][]=array('msg' => $admin_lang['13'],'success'=>'1');
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
		

										
													$set['wallpaper_image_url'][] = array('image_path' => $image_url , 'image_size' => $bytes );
													
											}
											else
											{
													$set['wallpaper_image_url'][] = array('image_path' => "fail");
											}
										   
					}			
						 
 
	
	header( 'Content-Type: application/json; charset=utf-8' );
	$json = json_encode($set);
				
	echo $json;
	
	 
?>
                    