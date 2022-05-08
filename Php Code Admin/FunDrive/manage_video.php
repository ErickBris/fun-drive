<?php include("includes/header.php");?>
 <?php include("includes/menu.php");?>

<?php
	include('includes/function.php');
	include('messages/messages.php'); 
 	
	mysql_query("SET NAMES 'utf8'");	
	
	//Get all owners
	 $rss_qry="SELECT * FROM video r,video_category c WHERE r.cid=c.cid && r.status = '1' ORDER BY r.id";
	 $rss_result=mysql_query($rss_qry);
 	
  	//Active and Deactive status
	if(isset($_POST['video']))
	{
		$video =implode(',',$_POST['video']);
	$id =  $_POST['id'];
	$sql= "update  feature_video  set video='$video'";
	mysql_query($sql)or die(mysql_error());

		
	}
	if(isset($_GET['status_deactive_id']))
	{
		$data = array('status'  =>  '0');
		
		$edit_status=Update('video', $data, "WHERE id = '".$_GET['status_deactive_id']."'");
		
		 $_SESSION['msg']="9";
		 header( "Location:manage_video.php");
		 exit;
	}
	if(isset($_GET['status_active_id']))
	{
		$data = array('status'  =>  '1');
		
		$edit_status=Update('video', $data, "WHERE id = '".$_GET['status_active_id']."'");
		
		$_SESSION['msg']="8";
		 header( "Location:manage_video.php");
		 exit;
	}
	
	//Delete Player
	if(isset($_GET['id']))
	{
  			$img_rss=mysql_query('SELECT * FROM video WHERE id=\''.$_GET['id'].'\'');
			$img_rss_row=mysql_fetch_assoc($img_teams);
			
			if($img_teams_row['t_image']!="")
		    {
					unlink('upload/'.$img_teams_row['t_image']);
					unlink('upload/video/'.$img_teams_row['video_url']);
 			}
  
		Delete('video','id='.$_GET['id'].'');
		
		$_SESSION['msg']="7";
		header( "Location:manage_video.php");
		//exit;
 	}
	
	
	
	$feature = mysql_query("select * from feature_video")or die(mysql_error()) ;
	$feature = mysql_fetch_assoc($feature);
							
	$str = explode(',',$feature['video']);
	
 ?>
 

<!-- start: Content -->
			<div id="content" class="span10">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="dashboard.php">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Manage Video</a></li>
			</ul>

			 			<div class="row-fluid">		
			 			 <div class="span12" align="right"><a href="add_video.php" title="" data-rel="tooltip" class="btn btn-warning" data-original-title="Add Video">Add Video</a></div>
						</div>
 					<br/>
         <div class="row-fluid">	
				<div class="box span12">

					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon user"></i><span class="break"></span>Manage Video</h2>
						 
					</div>
					<div class="box-content">
						<?php if(isset($_SESSION['msg'])){ 
						?>

					<div class="alert alert-info">
       					 <button type="button" class="close" data-dismiss="alert">×</button>
        				 <?php echo $admin_lang[$_SESSION['msg']] ; ?>
   					 </div>
                              <?php unset($_SESSION['msg']);		
 					}?>
						
						                <form action="manage_video.php" method="post">

						<table class="table table-striped table-bordered bootstrap-datatable datatable">
																							         		
						  <thead>
							  <tr>
                                  <th>Features Video</th>
							      <th>Video Category Name</th>
                                  <th>Video</th>
			                      <th>User</th>
                                  <th>Status</th>
								  <th>Actions</th>
							  </tr>
						  </thead>   
						  <tbody>
                             <?php 
									$i=1;
									while($rss_row=mysql_fetch_array($rss_result))
									{
									$check =  "";
								foreach($str as $simage)
								{
									if($rss_row['id'] == $simage)
									{ 
                                    $check =  "checked"; 
									} 
									
								}
								?>
                <tr>
				<td><input type="checkbox" name="video[]" <?php echo $check; ?> value="<?php echo $rss_row['id'];  ?>"  /></td>
                   <td><?php echo $rss_row['category_name'];?></td>
                   <td><?php echo $rss_row['video_url']; ?>
</td>    
                  <td><?php if($rss_row['user'] == 'admin'){ echo $rss_row['user'];}
				  
				   else {  	$uid  =$rss_row['user'];	
				   			$user_get="SELECT * FROM tbl_users WHERE user_id = '".$uid."' ";
							 $user=mysql_query($user_get);
							 $user_info = mysql_fetch_assoc($user);
							 echo $user_info['user_name'];
 	  					}?></td>
                                   

                  
                   <td class="center">
                    <?php if($rss_row['status']=='1'){?>
                       <a href="manage_video.php?status_deactive_id=<?php echo $rss_row['id'];?>"><span class="label label-success">Active</span></a>
										<?php }else{?>
                       <a href="manage_video.php?status_active_id=<?php echo $rss_row['id'];?>"><span class="label">Inactive</span></a>
                    <?php }?>	
                  </td>
                  <td class="center">
                   
                    <a class="btn btn-info" href="edit_video.php?id=<?php echo $rss_row['id'];?>">
                      <i class="halflings-icon white edit"></i>  
                    </a>                    
                    <a class="btn btn-danger" href="manage_video.php?id=<?php echo $rss_row['id'];?>" title="Delete taxi" onClick="return confirm('Delete this Video?')">
                      <i class="halflings-icon white trash"></i> 
                    </a>
 
                      
                  </td>
                </tr>
                <?php
									}
								?>
							 </tbody>
					  
					   
					  </table>            
	
	
		<tr>
								<td>
								<input type="submit" value="Save" class="btn btn-warning" />
							  </td></tr>
							  </form>
						
					</div>
				</div><!--/span-->
			
			</div><!--/row-->

			
			</div>
       
		
	</div><!--/.fluid-container-->
	
<!-- end: Content -->

<?php include("includes/footer.php");?>