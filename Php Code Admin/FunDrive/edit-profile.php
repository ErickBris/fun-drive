<?php include('includes/header.php');?>
    

<?php include('includes/menu.php');?>

<?php 
    include('includes/function.php');
	include('messages/messages.php'); 
 
	
	echo $result= Single('tbl_admin','id',$_SESSION['ADMIN_ID']);
	$row=mysql_fetch_assoc($result);
	
	if(isset($_POST['submit']))
	{
		 	
				$data = array(
					'username'  =>  $_POST['username'],
					'email'     =>  $_POST['email']    
				);
			
			$admin_id = $_SESSION['ADMIN_ID'];
			$admin_pro=Update('tbl_admin', $data, "WHERE id = '".$admin_id."'")or die(mysql_query());
			 
			if ($admin_pro > 0){
				
				$_SESSION['msg']="2";
				header( "Location:edit-profile.php");
				exit;
			} 	
	}
	
	 
?>
 <div id="content" class="span10">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="dashboard.php">Home</a>
					<i class="icon-angle-right"></i> 
				</li>
				<li>
					<i class="icon-edit"></i>
					<a href="#">Edit Profile</a>
				</li>
			</ul>
			
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon edit"></i><span class="break"></span>Edit Profile</h2>
						 
					</div>
					<div class="box-content">
          <p style="color:#990000; font-size:14px;" align="center">
					<?php if(isset($_SESSION['msg'])){ 
						?>
						
            <div class="alert alert-success">
							<button type="button" class="close" data-dismiss="alert">×</button>
							<?php echo $admin_lang[$_SESSION['msg']] ; ?>
						</div>
            	           
             <?php unset($_SESSION['msg']);		
							
					}?>
                    
          </p>
						<form class="form-horizontal" method="post" action="">
						  <fieldset>
							<div class="control-group">
							  <label class="control-label" for="typeahead">User Name</label>
							  <div class="controls">
								<input type="text" class="span6 typeahead" name="username" id="username" value="<?php echo $row['username'];?>">
								 
							  </div>
							</div>
              <div class="control-group">
							  <label class="control-label" for="typeahead">Email</label>
							  <div class="controls">
								<input type="email" class="span6 typeahead" name="email" id="email" value="<?php echo $row['email'];?>">
								 
							  </div>
							</div>         
              
         					  
							<div class="form-actions">
							  <button type="submit" name="submit" class="btn btn-primary">Save changes</button>
							   
							</div>
						  </fieldset>
						</form>   

					</div>
				</div><!--/span-->

			</div><!--/row-->
 </div>			

<?php include('includes/footer.php');?>                  