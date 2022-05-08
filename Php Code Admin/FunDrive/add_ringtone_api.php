<?php 
	include('includes/db_connection.php');   
	include('includes/function.php');
	include('messages/messages.php'); 
 
		mysql_query("SET NAMES 'utf8'");	
	  				
					
					if($_GET['user_id'])
					{
					
						 
									$ringtone=basename( $_GET['ringtone_path']);
									$image=basename( $_GET['image']);
						
									$data = array(											 
												    	'cid'  =>  $_GET['cid'],
														'ringtone_url'  =>  $ringtone,
														'ringtone_name'  => $_GET['ringtone_name'],
														'ringtone_image' => $image,

														'ringtone_status'  =>  '0',
														'tag'  =>  $_GET['tag'],
 														'size'  =>  $_GET['size'],
														'user'  =>  $_GET['user_id']
														);		
					 
					  			$qry = Insert('ringtone',$data);	
									 
									$set['entertainment'][]=array('msg' => 'Ringtone added successfully...','success'=>'1');
								}
								elseif(isset($_GET['image']))
								{
								
																		$file_folder = "ringtone/image/";
    
										  $file_name= $_FILES['image']['name'];
											
											$file_path = $file_folder.$file_name;
											if(move_uploaded_file($_FILES['image']['tmp_name'], $file_path))
											{
													
													$full_path = 'http://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/'.$file_folder;
													$image_url = $full_path.$file_name;
													
		$bytes = $_FILES['image']['size'];
	   
	    
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
		

										
													$set['ringtone_image'][] = array('ringtone_image_path' => $image_url , 'ringtone_image_size' => $bytes );
													
											}
											else
											{
													$set['ringtone_image'][] = array('ringtone_image' => "fail");
											}
										   
	
								
								}
					
					else
					{
									
											$file_folder = "ringtone/";
    
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
		

										
													$set['ringtone_url'][] = array('ringtone_path' => $image_url , 'ringtone_size' => $bytes );
													
											}
											else
											{
													$set['ringtone_url'][] = array('ringtone_path' => "fail");
											}
										   
					}			
						 
 
	
	header( 'Content-Type: application/json; charset=utf-8' );
	$json = json_encode($set);
				
	echo $json;
	
	 
?>
                    