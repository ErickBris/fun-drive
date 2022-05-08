<?php include('includes/header.php');?>
<?php include('includes/menu.php');?>
<?php 
    include('includes/function.php');

	include('messages/messages.php');
	require_once("thumbnail_images.class.php");  

 	
   
		  if(isset($_POST['submit']))
			{
					if($_FILES['link']['name'] != '')
				{
				unlink('ringtone/'.$_POST['old_link']);	
				$link = time().'_'.$_FILES['link']['name'];
			
				$pic1=$_FILES['link']['tmp_name'];
			
				$tpath1='ringtone/'.$link;
				
				copy($pic1,$tpath1);
				$bytes = $_FILES['link']['size'];
	   
				
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
					$link = $_POST['old_link'];
					$bytes = $_POST['old_size'];
				}
			if($_FILES['image']['name'] != '')
			{
					$image = time().'_'.$_FILES['image']['name'];
			
				$pic2=$_FILES['image']['tmp_name'];
			
				$tpath2='ringtone/image/'.$image;
				
				copy($pic2,$tpath2);
				unlink('ringtone/image/'.$_POST['old_image']);
			
			}
			else
			{
				$image = $_POST['old_image'];
			}

				
  														 $data = array(											 
												          'cid'  =>  $_POST['cat_id'],
														  'ringtone_name'  =>  $_POST['name'],
														  'ringtone_url'  => $link,
														  'tag'  =>  $_POST['tag'],
														  'ringtone_image' => $image,
														  'size'  => $bytes,
														  
														  
   														);	
			 
			 
			           $category_edit=Update('ringtone', $data, "WHERE ringtone_id = '".$_POST['id']."'");
					   }
					    if ($category_edit > 0){
						
 						$_SESSION['msg']="6";
						header( "Location:edit-ringtone.php?id=".$_POST['id']);
						exit;
						}
						
						 	if(isset($_GET['id']))
	{
 			$qry="SELECT * FROM ringtone WHERE ringtone_id='".$_GET['id']."'";
			$result=mysql_query($qry);
			$row=mysql_fetch_assoc($result);
 	}
 		
 	$ringtone_qry_cat="SELECT * FROM ringtones_category";
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
            $("#edit_ringtone").validate({
                rules: {
                                        name: "required",
										},
                 messages: { 
										 name:"Please enter  Name",
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

#edit_ringtone label.error {
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
					<a href="manage_ringtone.php">Manage Ringtone</a>
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
						<h2><i class="icon-edit"></i><span class="break"></span>Edit Ringtone</h2>
						 
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
							  <label class="control-label" for="category_name">Ringtone Name</label>
							  <div class="controls">
						    <input type="text" class="span6 typeahead" name="name" id="name" value="<?php echo $row['ringtone_name'];?>">
                                </div>
                                </div>
                                            
 				<div class="control-group">
				<label class="control-label" for="rss_title">Ringtone Image</label>
				<div class="controls">
				<input type="file" class="span6 typeahead" name="image" id="rss_title" value="">
 				<img src="ringtone/image/<?php echo $row['ringtone_image'];?>" height="150" width="150" />
				</div>
		        </div>

                                
                                <div class="control-group">
							  <label class="control-label" for="category_name">Ringtone URL</label>
							  <div class="controls">
                              <input type="file" class="span6 typeahead" name="link" id="rss_link" >
						    <input type="hidden" name="old_link" value="<?php echo $row['ringtone_url'];?> "> 
                            <input type="hidden" name="old_size" value="<?php echo $row['size'];?> "> 
							<input type="hidden" name="old_image" value="<?php echo $row['ringtone_size'];?> "> 

						    	<br>

                                <audio  controls="controls" style="padding-top:10px" >
    
                          <source src="ringtone/<?php echo $row['ringtone_url'];?>" type="audio/ogg"/>
                                    </audio>		  
                            
                                </div>
                                </div>
                                
                                <div class="control-group">
							  <label class="control-label" for="category_name">Tag</label>
							  <div class="controls">
						    <input type="text" name="tag" value="<?php echo $row['tag'];?> "> 
                                </div>
                                </div>
                                
                                
  							<div class="form-actions">
							  <button type="submit" name="submit" class="btn btn-primary">Edit Ringtone</button>
 							</div>
						  </fieldset>
						</form>   

					</div>
				</div><!--/span-->

			</div><!--/row-->
 </div>


<?php include('includes/footer.php');?>                  