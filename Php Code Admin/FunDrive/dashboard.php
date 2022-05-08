<?php include("includes/header.php");?>

<?php include("includes/menu.php");



$sql="SELECT COUNT(*) as num FROM wallpaper_categories";

$category_Wallpaper= mysql_fetch_array(mysql_query($sql));

$category_Wallpaper = $category_Wallpaper['num'];



$sql="SELECT COUNT(*) as num FROM ringtones_category";

$ringtones_category= mysql_fetch_array(mysql_query($sql));

$ringtones_category = $ringtones_category['num'];





$sql="SELECT COUNT(*) as num FROM tbl_users";

$users= mysql_fetch_array(mysql_query($sql));

$users = $users['num'];


$sql="SELECT COUNT(*) as num FROM wallpaper";

$wallpaper= mysql_fetch_array(mysql_query($sql));

$wallpaper = $wallpaper['num'];



$sql="SELECT COUNT(*) as num FROM ringtone";

$ringtone= mysql_fetch_array(mysql_query($sql));

$ringtone = $ringtone['num'];


?>



<!-- start: Content -->

			<div id="content" class="span10">

			

			

			<ul class="breadcrumb">

				<li>

					<i class="icon-home"></i>

					<a href="dashboard.php">Home</a> 

					<i class="icon-angle-right"></i>

				</li>

				<li><a href="dashboard.php">Dashboard</a></li>

			</ul>











			<div class="row-fluid">

  			<div class="row-fluid">	

            

            <a href="manage_category.php" class="quick-button metro green span2">

					<i class="icon-fixed-width"></i>

					<p>Wallpaper Category</p>

					<span class="badge"><?php echo $category_Wallpaper;?></span>

				</a>



				<a href="manage_rington.php" class="quick-button metro yellow span2">

					<i class="icon-fixed-width"></i>

					<p>Ringtone Category</p>

					<span class="badge"><?php echo $ringtones_category;?></span>

				</a>

 				<a href="users.php" class="quick-button metro blue span2">

					<i class="icon-user"></i>

					<p>Users</p>

					<span class="badge"><?php echo $users;?></span>

				</a>
				
 				<a href="manage_wallpaper.php" class="quick-button metro pink span2">

					<i class="icon-camera"></i>

					<p>Wallpapers</p>

					<span class="badge"><?php echo 	$wallpaper;?></span>

				</a>

				<a href="manage_ringtone.php" class="quick-button metro red span2">

					<i class="icon-music"></i>

					<p>Ringtones</p>

					<span class="badge"><?php echo 	$ringtone;?></span>

				</a>

 

            	

				<div class="clearfix"></div>



								

			</div><!--/row-->	 

 			

			</div>
	<!--/.fluid-container-->


	
	
	
	
	
	
	

<!-- end: Content -->



<?php include("includes/footer.php");?>