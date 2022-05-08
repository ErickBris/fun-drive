<?php include('includes/header.php');?>
<?php include('includes/menu.php');?>
<?php 
    include('includes/function.php');

	include('messages/messages.php');
	require_once("thumbnail_images.class.php");  

 	 	if(isset($_GET['id']))
	{
 			$qry="SELECT * FROM wallpaper WHERE id='".$_GET['id']."'";
			$result=mysql_query($qry);
			$row=mysql_fetch_assoc($result);
 	}
   
		  if(isset($_POST['submit']))
			{
				
				if($_FILES['image']['name'] != '')
				{
				unlink('upload/'.$_POST['old_image']);	
				$image = time().'_'.$_FILES['image']['name'];
			
				$pic1=$_FILES['image']['tmp_name'];
			
				$tpath1='upload/'.$image;
				
				copy($pic1,$tpath1);
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
				
				}
				else
				{
					$image = $_POST['old_image'];
					$bytes = $_POST['old_size'];
				}
  														 $data = array(											 
												          'cid'  =>  $_POST['cat_id'],
														  'wallpaper_image'  => $image,
														  'tag' => $_POST['tag'],
														  'size' => $bytes  
														  
   														);	
			 
			 
			           $category_edit=Update('wallpaper', $data, "WHERE id = '".$_POST['id']."'");
					   }
					    if ($category_edit > 0){
						
 						$_SESSION['msg']="6";
						header( "Location:edit-wallpaper.php?id=".$_POST['id']);
						exit;
						}
 		
 	$ringtone_qry_cat="SELECT * FROM wallpaper_categories";
	$ringtone_qry_cat=mysql_query($ringtone_qry_cat);

 
 ?>
<script src="js/jquery-1.9.1.min.js"></script>

<script type="text/javascript" src="js/jquery.validate.min.js"></script>

<script type="text/javascript">
(function($,W,D)
{
    var JQUERY4U = {};

    JQUERY4U.UTIL =
    {
        setupFormValidation: function()
        {
            //form validation rules
            $("#add_category").validate({
                rules: {
                                        category_name: "required",
										category_image: "required",
  									},
                 messages: { 
										 category_name:"Please enter Category Name",
										 category_images:"Please enter Category Image"
                 },
                submitHandler: function(form) {
                    form.submit();
                }
            });
        }
    }

    //when the dom has loaded setup form validation rules
    $(D).ready(function($) {
        JQUERY4U.UTIL.setupFormValidation();
    });

})(jQuery, window, document);
</script>
<style>

#add_category label.error {
    color: #FB3A3A;
    display: inline-block;
    margin: 4px 0 5px 20px;
    padding: 0;
    text-align: left;
    width: auto;
}
</style>

 <div id="content" class="span10">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="dashboard.php">Home</a>
					<i class="icon-angle-right"></i> 
				</li>
                <li>
					<i class="icon-table"></i>
					<a href="manage_wallpaper.php">Manage Wallpaper</a>
					<i class="icon-angle-right"></i> 
				</li>
				<li>
					<i class="icon-edit"></i>
					<a href="#">Edit Ringtone</a>
				</li>
			</ul>
			
			<div class="row-fluid">
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2><i class="icon-edit"></i><span class="break"></span>Edit Category</h2>
						 
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
				<form id="edit_ringtone" class="form-horizontal" method="post" action="" enctype="multipart/form-data">
            	         <input  type="hidden" name="id" value="<?php echo $_GET['id'];?>" />
	
 						                   <div class="control-group">
							  <label class="control-label" for="cat_id">Ringtone Category Name</label>
                 <div class="controls">
                 	<select name="cat_id" id="cat_id" data-rel="chosen">
                    <option value="">select category</option>
                  	 
                     <?php 											 
											while($r_c_row=mysql_fetch_array($ringtone_qry_cat))
											{
												$sel = '';
												if($r_c_row['cid'] == $row['cid'])
												{
													$sel = "selected";	
												}	
																				?>
                     <option value="<?php echo $r_c_row['cid'];?>" <?php echo $sel; ?>><?php echo $r_c_row['category_name'];?></option>
                   <?php }?>
						        </select>
 							</div>
							</div>

                        
                          <fieldset>
							    
                                
                                <div class="control-group">
							  <label class="control-label" for="category_name">Wallpaper Image</label>
							  <div class="controls">
                              <input type="file" name="image" >
                              <img src="upload/<?php echo $row['wallpaper_image']; ?>" width="72" width="72" />
						    <input type="hidden" name="old_image" value="<?php echo $row['wallpaper_image'];?> "> 
                            <input type="hidden" name="old_size" value="<?php echo $row['size'];?> "> 
                            
                                </div>
                                </div>
                                
                                <div class="control-group">
							  <label class="control-label" for="category_name">Tag</label>
							  <div class="controls">
                              <input type="text" name="tag" value="<?php echo $row['tag'];?> " >
                                </div>
                                </div>
                                
                                
  							<div class="form-actions">
							  <button type="submit" name="submit" class="btn btn-primary">Edit Wallpaper</button>
 							</div>
						  </fieldset>
						</form>   

					</div>
				</div><!--/span-->

			</div><!--/row-->
 </div>


<?php include('includes/footer.php');?>                  