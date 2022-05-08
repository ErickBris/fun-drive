
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
  			
			
  
		Delete('tbl_feedback','id='.$_GET['id'].'');
		
		$_SESSION['msg']="7";
		
 	}
	$category_qry="SELECT * FROM tbl_feedback ORDER BY id desc";
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
				<li><a href="feedback.php">Manage Feedback</a></li>
			</ul>

			 			
 					<br/>
         <div class="row-fluid">	
				<div class="box span12">

					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon user"></i><span class="break"></span>Manage Feedback</h2>
						 
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
                              <th>Name</th>
                              <th>Email</th>
                              <th>Message</th>
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
                  <td><?php echo $category_row['name'];?></td>
                  <td><?php echo $category_row['email'];?></td> 
                  <td><?php echo $category_row['message'];?></td>
                     
                  <td class="center">
                   
                    <a class="btn btn-danger" href="feedback.php?id=<?php echo $category_row['id'];?>" title="Delete Feedback" onClick="return confirm('Delete this Feeback?')">
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