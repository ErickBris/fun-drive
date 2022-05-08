<?php include("includes/header.php");?>
 <?php include("includes/menu.php");?>

<?php
	include('includes/function.php');
	include('messages/messages.php'); 
 	
	mysql_query("SET NAMES 'utf8'");	
	
	//Get all owners
	 $rss_qry="SELECT * FROM wallpaper r,wallpaper_categories c WHERE r.cid=c.cid && r.status = '1' ORDER BY r.id";
	 $rss_result=mysql_query($rss_qry);
 	
  	//Active and Deactive status
	
	if(isset($_POST['wallpaper']))
	{
		
		$wallpaper =implode(',',$_POST['wallpaper']);
	$sql= "update  feature_wallpaper  set wallpaper='$wallpaper'";
	mysql_query($sql)or die(mysql_error());

		
	}
	
	if(isset($_GET['status_deactive_id']))
	{
		$data = array('status'  =>  '0');
		
		$edit_status=Update('wallpaper', $data, "WHERE id = '".$_GET['status_deactive_id']."'");
		
		 $_SESSION['msg']="9";
		 header( "Location:manage_wallpaper.php");
		 exit;
	}
	if(isset($_GET['status_active_id']))
	{
		$data = array('status'  =>  '1');
		
		$edit_status=Update('wallpaper', $data, "WHERE id = '".$_GET['status_active_id']."'");
		
		$_SESSION['msg']="8";
		 header( "Location:manage_wallpaper.php");
		 exit;
	}
	
	//Delete Player
	if(isset($_GET['id']))
	{
  			$img_rss=mysql_query('SELECT * FROM wallpaper WHERE id=\''.$_GET['id'].'\'');
			$img_rss_row=mysql_fetch_assoc($img_teams);
			
			if($img_teams_row['image']!="")
		    {
					unlink('upload/'.$img_teams_row['image']);
					unlink('upload/'.$img_teams_row['image']);
 			}
  
		Delete('wallpaper','id='.$_GET['id'].'');
		
		$_SESSION['msg']="7";
		header( "Location:manage_wallpaper.php");
		//exit;
 	}
	
	$feature = mysql_query("select * from feature_wallpaper")or die(mysql_error()) ;
	$feature = mysql_fetch_assoc($feature);
							
	$str = explode(',',$feature['wallpaper']);
	
 ?>

<!-- start: Content -->
			<div id="content" class="span10">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="dashboard.php">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Manage Wallpaper</a></li>
			</ul>

			 			<div class="row-fluid">		
			 			 <div class="span12" align="right"><a href="add-wallpaper.php" title="" data-rel="tooltip" class="btn btn-warning" data-original-title="Add Wallpaper">Add Wallpaper</a></div>
						</div>
 					<br/>
         <div class="row-fluid">	
				<div class="box span12">

					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon user"></i><span class="break"></span>Manage Wallpaper</h2>
						 
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
											                <form action="manage_wallpaper.php" method="post">

						<table class="table table-striped table-bordered bootstrap-datatable datatable">
																							         		
						  <thead>
							  <tr>
							      <th>Features Wallpaper</th>
                                  <th>Wallpaper Category Name</th>
                                  <th>Wallpaper Image</th>
			                      <th>Size</th>
								  <th>Tag</th>
                                  <th>User</th>
                                  <th>Downloads</th>
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
				<td><input type="checkbox" name="wallpaper[]" <?php echo $check; ?> value="<?php echo $rss_row['id'];  ?>"  /></td>
                   <td><?php echo $rss_row['category_name'];?></td>
                   <td><img src="upload/<?php echo $rss_row['wallpaper_image'];?>" height="100" width="100"/></td> 
                 
                  <td><?php echo $rss_row['size'];?></td>
                 <td><?php echo $rss_row['tag'];?></td>
						
                  <td><?php if($rss_row['user'] == 'admin'){ echo $rss_row['user'];}
				  
				   else {  	$uid  =$rss_row['user'];	
				   			$user_get="SELECT * FROM tbl_users WHERE user_id = '".$uid."' ";
							 $user=mysql_query($user_get);
							 $user_info = mysql_fetch_assoc($user);
							 echo $user_info['user_name'];
 	  					}?></td>
                  <td><?php echo $rss_row['download_count'];?></td>
                                   

                  
                   <td class="center">
                    <?php if($rss_row['status']=='1'){?>
                       <a href="manage_wallpaper.php?status_deactive_id=<?php echo $rss_row['id'];?>"><span class="label label-success">Active</span></a>
										<?php }else{?>
                       <a href="manage_wallpaper.php?status_active_id=<?php echo $rss_row['id'];?>"><span class="label">Inactive</span></a>
                    <?php }?>	
                  </td>
                  <td class="center">
                   
                    <a class="btn btn-info" href="edit-wallpaper.php?id=<?php echo $rss_row['id'];?>">
                      <i class="halflings-icon white edit"></i>  
                    </a>                    
                    <a class="btn btn-danger" href="manage_wallpaper.php?id=<?php echo $rss_row['id'];?>" title="Delete taxi" onClick="return confirm('Delete this Wallpaper?')">
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