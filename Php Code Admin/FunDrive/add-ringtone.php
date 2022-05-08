<?php include('includes/header.php');?>
<?php include('includes/menu.php');?>
<?php 

    include('includes/function.php');

	include('messages/messages.php');
	require_once("thumbnail_images.class.php"); 
  
   	mysql_query("SET NAMES 'utf8'");	

 	if(isset($_POST['submit']))
	{
			$r_link = time().'_'.$_FILES['link']['name'];
			$pic1=$_FILES['link']['tmp_name'];

					
   			$tpath1='ringtone/'.$r_link;
			
			copy($pic1,$tpath1);
			
			
			$image = time().'_'.$_FILES['image']['name'];
			$tpath1='ringtone/image/'.$image;
			$pic2=$_FILES['image']['tmp_name'];
			
			copy($pic2,$tpath1);
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
 	
		
		
    						                            $data = array(
														'cid'  =>  $_POST['cat_id'],
														
 													    'ringtone_name'  =>  $_POST['name'],
														'ringtone_url'  => $r_link ,
    												    'ringtone_status' => '1',
														'download_count'=>0,
														'size' => $bytes,
														'tag' => $_POST['tag'],
														'ringtone_image' => $image,
														'user' => 'admin'
														);		
 					  $qry = Insert('ringtone',$data);									
  					  $_SESSION['msg']="5";
					  header( "Location:manage_ringtone.php");
					  exit;
 	}
	

	$rss_qry="SELECT * FROM ringtones_category";
	$rss_result=mysql_query($rss_qry);
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
            $("#add_rss").validate({
                rules: {
                                        cat_id: "required",
										name: "required",
										link: "required"
 									 
   									},
                 messages: { 
										 cat_id: "Please enter Category Name",
										 name: "Please enter Ringtone Name",
										 link: "Please select Ringtone "
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

#add_rss label.error {
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
					<i class="icon-plus"></i>
					<a href="#">Add Ringtone</a>
				</li>
            </ul>
                         <div class="row-fluid">
                        <div class="box span12">
                        <div class="box-header" data-original-title>
						<h2><i class="icon-plus"></i><span class="break"></span>Add Ringtone</h2>
						 
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
                  
                <form id="add_rss" class="form-horizontal" method="post" action="" enctype="multipart/form-data">
                <input type="hidden" name="id" value="<?php echo $_GET['id'];?>" />
                <fieldset>
                 
                   <div class="control-group">
							  <label class="control-label" for="cat_id">Ringtone Category Name</label>
                 <div class="controls">
                 	<select name="cat_id" id="cat_id" data-rel="chosen">
                    <option value="">select category</option>
                  	 
                     <?php 											 
											while($rss_row=mysql_fetch_array($rss_result))
											{
										?>
                     <option value="<?php echo $rss_row['cid'];?>"><?php echo $rss_row['category_name'];?></option>
                   <?php }?>
						        </select>
 							</div>
							</div>
                
 				<div class="control-group">
				<label class="control-label" for="rss_title">Ringtone Title</label>
				<div class="controls">
				<input type="text" class="span6 typeahead" name="name" id="rss_title" value="">
 				</div>
		        </div>
		                
 				<div class="control-group">
				<label class="control-label" for="rss_title">Ringtone Image</label>
				<div class="controls">
				<input type="file" class="span6 typeahead" name="image" id="rss_title" value="">
 				</div>
		        </div>

                
                 <div class="control-group">
				<label class="control-label" for="rss_link">Select Ringtone</label>
				<div class="controls">
				<input type="file" class="span6 typeahead" name="link" id="rss_link" value="">
 				</div>
		        </div>
                
                 <div class="control-group">
				<label class="control-label" for="Tag">Tag</label>
				<div class="controls">
				<input type="text" class="span6 typeahead" name="tag" id="rss_link" value="">
 				</div>
		        </div>
                
                <div class="form-actions">
			    <button type="submit" name="submit" class="btn btn-primary">Add Ringtone</button>
			    </div>
				</fieldset>
				</form>   
 			    </div>
				</div><!--/span-->

			</div><!--/row-->
            </div>			

<?php include('includes/footer.php');?>                  