<?php include('includes/header.php');?>
<?php include('includes/menu.php');?>
<?php 
    include('includes/function.php');

	include('messages/messages.php');
	require_once("thumbnail_images.class.php");  

 	 	if(isset($_GET['id']))
		{
 			$qry="SELECT * FROM video WHERE id='".$_GET['id']."'";
			$result=mysql_query($qry);
			$row=mysql_fetch_assoc($result);
 		}
   
		  if(isset($_POST['submit']))
			{
			$video_id = '000q1w2';
	
		if($_POST['upload_type']=='Local_Upload')
		{
					if($_FILES['image']['name'] != '')
		{
					unlink('upload/'.$_POST['old_image']);
					$t_image = time().'_'.$_FILES['image']['name'];
					$pic2=$_FILES['image']['tmp_name'];

		
   			$tpath2='upload/'.$t_image;
			
			copy($pic2,$tpath2);
		}
		else
		{
		$t_image = $_POST['old_image'];
		}	
			if($_FILES['video']['name'] !=  '')
			{
					unlink('upload/vidoe'.$_POST['old_video']);
			
			$video = time().'_'.$_FILES['video']['name'];
			$pic1=$_FILES['video']['tmp_name'];
	
			$tpath1='upload/video/'.$video;
			copy($pic1,$tpath1);
	
			$bytes = $_FILES['video']['size'];
	   
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
			
			 $bytes = $_POST['old_size'];
			 $video = $_POST['old_video'];
			}
			


			
		}
		else
		{
			 $bytes = '';
			 $video = $_POST['youtube'];
			 $t_image = '';
			 	function youtube_id_from_url($url) {
    $pattern = 
        '%^# Match any youtube URL
        (?:https?://)?  # Optional scheme. Either http or https
        (?:www\.)?      # Optional www subdomain
        (?:             # Group host alternatives
          youtu\.be/    # Either youtu.be,
        | youtube\.com  # or youtube.com
          (?:           # Group path alternatives
            /embed/     # Either /embed/
          | /v/         # or /v/
          | /watch\?v=  # or /watch\?v=
          )             # End path alternatives.
        )               # End host alternatives.
        ([\w-]{10,12})  # Allow 10-12 for 11 char youtube id.
        $%x'
        ;
    $result = preg_match($pattern, $url, $matches);
    if (false !== $result) {
        return $matches[1];
    }
	
    return false;
}


		
		
		$video_id = youtube_id_from_url($_POST['youtube']);
	
		
		}
			
			
		
														 $data = array(											 
												         'cid'  =>  $_POST['cat_id'],
														'video_type' => $_POST['upload_type'],
														'video_url'  => $video,
														't_image' =>$t_image,
														'video_id' => $video_id,
														'video_name' => $_POST['name'],

														'tag'=>$_POST['tag'],
														'size'=>$bytes,
														 
   														);	
			 
			 
			           $category_edit=Update('video', $data, "WHERE id = '".$_POST['id']."'");
					   }
					    if ($category_edit > 0){
						
 						$_SESSION['msg']="6";
						header( "Location:edit_video.php?id=".$_POST['id']);
						exit;
						}
 		
 	$ringtone_qry_cat="SELECT * FROM video_category";
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
<script type="text/javascript">
$(document).ready(function(e) {
           $("#upload_type").change(function(){
			   var type=$("#upload_type").val();
							if(type=="youtube")
							{
								$("#video2").hide();
								$("#youtube").show();

							}
							if(type=="Local_Upload")
							{
								$("#youtube").hide();
								$("#video2").show();
							}
							

							   });
							   
							   $( window  ).load(function() {
										   var type=$("#upload_type").val();
										   				if(type=="youtube")
							{
								$("#video2").hide();
								$("#youtube").show();

							}
							if(type=="Local_Upload")
							{
								$("#youtube").hide();
								$("#video2").show();
							}
			

							});
        });
		
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
    <li> <i class="icon-home"></i> <a href="dashboard.php">Home</a> <i class="icon-angle-right"></i> </li>
    <li> <i class="icon-table"></i> <a href="manage_video.php">Manage Video</a> <i class="icon-angle-right"></i> </li>
    <li> <i class="icon-edit"></i> <a href="#">Edit Video</a> </li>
  </ul>
  <div class="row-fluid">
    <div class="box span12">
      <div class="box-header" data-original-title>
        <h2><i class="icon-edit"></i><span class="break"></span>Edit Video</h2>
      </div>
      <div class="box-content">
        <p style="color:#990000; font-size:14px;" align="center">
          <?php if(isset($_SESSION['msg'])){ 
						?>
        <div class="alert alert-success">
          <button type="button" class="close" data-dismiss="alert">×</button>
          <?php echo $admin_lang[$_SESSION['msg']] ; ?> </div>
        <?php unset($_SESSION['msg']);		
							
					}?>
        </p>
        <form id="edit_video" class="form-horizontal" method="post" action="" enctype="multipart/form-data">
          <fieldset>
          <div class="control-group">
            <label class="control-label" for="cat_id">Video Category Name</label>
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
		  
		    <div class="control-group">
            <label class="control-label" for="tag">Video Name</label>
            <div class="controls">
              <input type="text" name="name" id="app_logo" value="<?php echo $row['video_name'];?>" class="input-xlarge">
            </div>
          </div>
        
		  
		  
          <div class="control-group">
            <label class="control-label" for="cat_id">Video Type</label>
            <div class="controls">
              <select name="upload_type" id="upload_type" data-rel="chosen">
                <option  value="">select category</option>
                <option <?php if($row['video_type'] == 'Local_Upload'){ echo 'selected';} ?> value="Local_Upload">Local Upload</option>
                <option <?php if($row['video_type'] == 'youtube'){echo 'selected';} ?> value="youtube">Youtube</option>
              </select>
            </div>
          </div>
          <div class="control-group" id="youtube">
            <label class="control-label" for="image">Youtube Url</label>
            <div class="controls">
              <input type="text" name="youtube"  value="<?php echo $row['video_url'];?>" class="input-xlarge">
            </div>
          </div>
          <div id="video2">
            <div class="control-group" >
              <label class="control-label"  id="video"  for="image">Select Video</label>
              <div class="controls">
                <input type="file" name="video"  value="" class="input-xlarge">
                <?php echo $row['video_url'];?> </div>
            </div>
            <div class="control-group" id="image">
              <label class="control-label"  id="image" for="image">Video image</label>
              <div class="controls">
                <input type="file" name="image" id="image" value="" class="input-xlarge">
                <img src="upload/<?php  echo $row['t_image'];?>" height="100" width="100"> </div>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="tag">Tag</label>
            <div class="controls">
              <input type="text" name="tag" id="app_logo" value="<?php echo $row['tag'];?>" class="input-xlarge">
            </div>
          </div>
          <div class="form-actions">
            <button type="submit" name="submit" class="btn btn-primary">Edit Video</button>
          </div>
          </fieldset>
          <input type="hidden" name="old_image" value="<?php echo $row['t_image'];?> ">
          <input type="hidden" name="old_video" value="<?php echo $row['video_url'];?> ">
          <input type="hidden" name="old_size" value="<?php echo $row['size'];?> ">
          <input type="hidden" name="id" value="<?php echo $row['id'];?> ">
        </form>
      </div>
    </div>
    <!--/span-->
  </div>
  <!--/row-->
</div>
<?php include('includes/footer.php');?>
