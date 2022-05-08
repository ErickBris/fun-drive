
<?php include("includes/header.php");?>
 <?php include("includes/menu.php");?>

<?php
	include('includes/function.php');
	include('messages/messages.php'); 
 	
	mysql_query("SET NAMES 'utf8'");	
 	
 	//Get all owners
	 	
 	//Delete Player
	if(isset($_GET['id']))
	{
  			$img_rss=mysql_query('SELECT * FROM normal_notification WHERE id=\''.$_GET['id'].'\'');
			$img_rss_row=mysql_fetch_assoc($img_teams);
			
			if($img_category_row['image']!="")
		    {
					unlink('upload/'.$img_category_row['image']);
			}
  
		Delete('normal_notification','id='.$_GET['id'].'');
		
		$_SESSION['msg']="7";
}

if(isset($_GET['notification']))
	{				
									$users_sql = "SELECT * FROM tbl_push_notification ";     
									$users_result = mysql_query($users_sql);
									while($user_row = mysql_fetch_assoc($users_result))
									{
									$msg=$_GET['msg'];
									echo Send_GCM_msg($user_row['token'],$msg);
									}
									
	}

	$category_qry="SELECT * FROM normal_notification order by id desc";
	$category_result=mysql_query($category_qry);

 ?>

<!-- start: Content -->
			<div id="content" class="span10">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="dashboard.php">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="normal_notification.php">Manage Normal Notification</a></li>
			</ul>

			 			<div class="row-fluid">		
			 			 <div class="span12" align="right"><a href="add_normal_notification.php" title="" data-rel="tooltip" class="btn btn-warning" data-original-title="Add Normal Notification">Add Normal Notification</a></div>
						</div>
 					<br/>
         <div class="row-fluid">	
				<div class="box span12">

					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon user"></i><span class="break"></span>Manage Normal Notification</h2>
						 
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
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
																							         		
						  <thead>
							  <tr>
                              <th>Message</th>
                              
                              <th>Notification</th>
                              <th>Actions</th>
							  </tr>
						  </thead>   
						  <tbody>
                
                             <?php 
									$i=1;
									while($category_row=mysql_fetch_array($category_result))
									{
								?>
                <tr>
                <td><?php echo $category_row['message'];?></td>
                                  <td>
                                     <a href="normal_notification.php?notification&msg=<?php echo  $category_row['message'];?>" >Send Notification</a></td> 

                     
                  <td class="center">
                   
                    <a class="btn btn-info" href="edit-normal_notification.php?id=<?php echo $category_row['id'];?>">
                      <i class="halflings-icon white edit"></i>  
                    </a>                    
                    <a class="btn btn-danger" href="normal_notification.php?id=<?php echo $category_row['id'];?>" title="Delete App" onClick="return confirm('Delete this App?')">
                      <i class="halflings-icon white trash"></i> 
                    </a>
 
                      
                  </td>
                </tr>
                <?php
									}
								?>
							  
						  </tbody>
					  </table>            
					</div>
				</div><!--/span-->
			
			</div><!--/row-->

			
			</div>
       
		
	</div><!--/.fluid-container-->
	
<!-- end: Content -->

<?php include("includes/footer.php");?>