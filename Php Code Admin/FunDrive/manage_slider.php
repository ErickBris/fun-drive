
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
  			$img_rss=mysql_query('SELECT * FROM tbl_slider WHERE id=\''.$_GET['id'].'\'');
			$img_rss_row=mysql_fetch_assoc($img_teams);
			
			if($img_category_row['category_image']!="")
		    {
					unlink('upload/'.$img_category_row['category_image']);
 			}
  
		Delete('tbl_slider','id='.$_GET['id'].'');
		
		$_SESSION['msg']="45";
		
 	}
	$category_qry="SELECT * FROM tbl_slider";
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
				<li><a href="manage_category.php">Manage Slider</a></li>
			</ul>

			 			<div class="row-fluid">		
			 			 <div class="span12" align="right"><a href="add_slider.php" title="" data-rel="tooltip" class="btn btn-warning" data-original-title="Add Slider">Add Slider</a></div>
						</div>
 					<br/>
         <div class="row-fluid">	
				<div class="box span12">

					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon user"></i><span class="break"></span>Manage Slider</h2>
						 
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
                              <th>Image</th>
                              <th>Name</th>
                              <th>Link</th>
                              <th>Actions </th>
							  </tr>
						  </thead>   
						  <tbody>
                
                             <?php 
									$i=1;
									while($category_row=mysql_fetch_array($category_result))
									{
								?>
                <tr>
                  <td><img src="upload/<?php echo $category_row['image'];?>" height="100" width="100"/></td> 
                  <td><?php echo $category_row['name'];?></td>
                     
                  <td><?php echo $category_row['link'];?></td>
                  <td class="center">
                   
                    <a class="btn btn-info" href="edit_slider.php?id=<?php echo $category_row['id'];?>">
                      <i class="halflings-icon white edit"></i>  
                    </a>                    
                    <a class="btn btn-danger" href="manage_slider.php?id=<?php echo $category_row['id'];?>" title="Delete taxi" onClick="return confirm('Delete this Slider?')">
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