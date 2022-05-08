<?php include("includes/header.php");?>
 <?php include("includes/menu.php");?>

<?php
	include('includes/function.php');
	include('messages/messages.php'); 
 	
	mysql_query("SET NAMES 'utf8'");	
	
	
	if(isset($_POST['ringtone']))
	{
		
		$ringtone =implode(',',$_POST['ringtone']);
	$sql= "update  feature_ringtone  set ringtone='$ringtone'";
	mysql_query($sql)or die(mysql_error());

		
	}
	
	
  	//Active and Deactive status
	
	
	
	if(isset($_GET['status_deactive_id']))
	{
		$data = array('ringtone_status'  =>  '0');
		
		$edit_status=Update('ringtone', $data, "WHERE ringtone_id = '".$_GET['status_deactive_id']."'");
		
		 $_SESSION['msg']="9";
		 header( "Location:manage_ringtone.php");
		 exit;
	}
	if(isset($_GET['status_active_id']))
	{
		$data = array('ringtone_status'  =>  '1');
		
		$edit_status=Update('ringtone', $data, "WHERE ringtone_id = '".$_GET['status_active_id']."'");
		
		$_SESSION['msg']="8";
		 header( "Location:manage_ringtone.php");
		 exit;
	}
	
	//Delete Ringtone
	if(isset($_GET['id']))
	{
  			$img_rss=mysql_query('SELECT * FROM ringtone WHERE id=\''.$_GET['id'].'\'');
			$img_rss_row=mysql_fetch_assoc($img_teams);
			
			if($img_teams_row['ringtone_url']!="")
		    {
					unlink('ringtone/'.$img_teams_row['ringtone_url']);
					unlink('ringtone/image/'.$img_teams_row['ringtone_image']);
					
 			}
  
		Delete('ringtone','ringtone_id='.$_GET['id'].'');
		
		$_SESSION['msg']="7";
	}
	//Get all owners
	 $rss_qry="SELECT * FROM ringtone r,ringtones_category c WHERE r.cid=c.cid && r.ringtone_status= '1' ORDER BY r.ringtone_id";
	 $rss_result=mysql_query($rss_qry);
	 
	 
	$feature = mysql_query("select * from feature_ringtone")or die(mysql_error()) ;
	$feature = mysql_fetch_assoc($feature);
							
	$str = explode(',',$feature['ringtone']);
	
	 
 	
 ?>

<!-- start: Content -->
			<div id="content" class="span10">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="dashboard.php">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Manage Ringtone</a></li>
			</ul>

			 			<div class="row-fluid">		
			 			 <div class="span12" align="right"><a href="add-ringtone.php" title="" data-rel="tooltip" class="btn btn-warning" data-original-title="Add Ringtone">Add Ringtone</a></div>
						</div>
 					<br/>
         <div class="row-fluid">	
				<div class="box span12">

					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon user"></i><span class="break"></span>Manage Ringtone</h2>
						 
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
						
																	                <form action="manage_ringtone.php" method="post">

						<table class="table table-striped table-bordered bootstrap-datatable datatable">
																							         		
						  <thead>
							  <tr>
 							      <th>Features Ringtone</th>
                                  <th>Ringtone Category Name</th>
                                  <th>Ringtone Title</th>
                                  <th>Ringtone</th>
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
									{		$check =  "";
								foreach($str as $simage)
								{
									if($rss_row['ringtone_id'] == $simage)
									{ 
                                    $check =  "checked"; 
									} 
									
								}
								?>
                <tr>
				<td><input type="checkbox" name="ringtone[]" <?php echo $check; ?> value="<?php echo $rss_row['ringtone_id'];  ?>"  /></td>
                   <td><?php echo $rss_row['category_name'];?></td>
                   <td><?php echo $rss_row['ringtone_name'];?></td> 
                  <td>
		<audio  controls="controls" >
    
  <source src="ringtone/<?php echo $rss_row['ringtone_url'];?>" type="audio/ogg"/>
			</audio>		  
				 </td>
                  
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
                    <?php if($rss_row['ringtone_status']=='1'){?>
                       <a href="manage_ringtone.php?status_deactive_id=<?php echo $rss_row['ringtone_id'];?>"><span class="label label-success">Active</span></a>
										<?php }else{?>
                       <a href="manage_ringtone.php?status_active_id=<?php echo $rss_row['ringtone_id'];?>"><span class="label">Inactive</span></a>
                    <?php }?>	
                  </td>
                  <td class="center">
                   
                    <a class="btn btn-info" href="edit-ringtone.php?id=<?php echo $rss_row['ringtone_id'];?>">
                      <i class="halflings-icon white edit"></i>  
                    </a>                    
                    <a class="btn btn-danger" href="manage_ringtone.php?id=<?php echo $rss_row['ringtone_id'];?>" title="Delete taxi" onClick="return confirm('Delete this Ringtone?')">
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