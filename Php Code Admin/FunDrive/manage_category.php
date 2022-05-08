
<?php include("includes/header.php");?>
 <?php include("includes/menu.php");?>

<?php
	include('includes/function.php');
	include('messages/messages.php'); 
 	
	mysql_query("SET NAMES 'utf8'");	
 	
 	//Get all owners
	 	
 	//Delete Player
	if(isset($_GET['cid']))
	{
  			$img_rss=mysql_query('SELECT * FROM wallpaper_categories WHERE cid=\''.$_GET['cid'].'\'');
			$img_rss_row=mysql_fetch_assoc($img_teams);
			
			if($img_category_row['category_image']!="")
		    {
					unlink('upload/'.$img_category_row['category_image']);
					unlink('upload/'.$img_category_row['category_image']);
 			}
  
		Delete('wallpaper_categories','cid='.$_GET['cid'].'');
		
		$_SESSION['msg']="12";
		//header( "Location:manage_category.php");
		//exit;
 	}
	$category_qry="SELECT * FROM wallpaper_categories ORDER BY wallpaper_categories.cid";
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
				<li><a href="manage_category.php">Manage Category</a></li>
			</ul>

			 			<div class="row-fluid">		
			 			 <div class="span12" align="right"><a href="add-category.php" title="" data-rel="tooltip" class="btn btn-warning" data-original-title="Add Category">Add Category</a></div>
						</div>
 					<br/>
         <div class="row-fluid">	
				<div class="box span12">

					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon user"></i><span class="break"></span>Manage Category</h2>
						 
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
                              <th>Category Image</th>
                              <th>Category Name</th>
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
                  <td><img src="upload/<?php echo $category_row['category_image'];?>" height="100" width="100"/></td> 
                  <td><?php echo $category_row['category_name'];?></td>
                     
                  <td class="center">
                   
                    <a class="btn btn-info" href="edit-category.php?cid=<?php echo $category_row['cid'];?>">
                      <i class="halflings-icon white edit"></i>  
                    </a>                    
                    <a class="btn btn-danger" href="manage_category.php?cid=<?php echo $category_row['cid'];?>" title="Delete taxi" onClick="return confirm('Delete this Category?')">
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