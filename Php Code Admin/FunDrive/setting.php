<?php include('includes/header.php');?>
    
<?php include('includes/menu.php');?>

<?php 
    include('includes/function.php');
	  include('messages/messages.php'); 
		require_once("thumbnail_images.class.php");
 		
		mysql_query("SET NAMES 'utf8'");	
	 
 			$qry="SELECT * FROM tbl_settings where id='1'";
			$result=mysql_query($qry);
			$settings_row=mysql_fetch_assoc($result);
 
	if(isset($_POST['submit']))
	{
		
		$img_res=mysql_query("SELECT * FROM tbl_settings WHERE id='1'");
		$img_row=mysql_fetch_assoc($img_res);
			
			if($img_row['app_logo']!="")
			{
				 
			}	 
 		
		if($_FILES['app_logo']['name']!="")
		{
			  
			unlink('upload/'.$img_row['app_logo']);				 
				
			$app_logo=$_FILES['app_logo']['name'];
			$pic1=$_FILES['app_logo']['tmp_name'];
		
			$tpath1='upload/'.$app_logo;
				
			copy($pic1,$tpath1);
				 
				$data = array(
				'app_name'  =>  $_POST['app_name'],
				'app_logo'  =>  $app_logo,
				'app_email'  =>  $_POST['app_email'],
				'app_website'  =>  addslashes($_POST['app_website']),
				'app_description'  => addslashes($_POST['app_description'])								 	 
				);
		}
		else
		{
		 
				$data = array(
					'app_name'  =>  $_POST['app_name'],					 
					'app_email'  =>  $_POST['app_email'],
					'app_website'  =>  addslashes($_POST['app_website']),
					'app_description'  => addslashes($_POST['app_description'])								 	 
					);
		}
		 
		$news_edit=Update('tbl_settings', $data, "WHERE id = '1'");
		
			 
			if ($news_edit > 0){
				
				$_SESSION['msg']="5";
				header( "Location:setting.php");
				exit;
			} 	
		
 	}
	 
?>
<script src="ckeditor/ckeditor.js"></script>

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
                                        app_name: "required",
										app_email: "required",
										app_website: "required",
										app_description: "required"
 									 
   									},
                 messages: { 
										 app_name: "Please enter Appliction Name",
										 app_email: "Please enter Appliction Email",
										 app_website: "Please enter Application Website",
										 app_description:"Please enter Application Description"
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
					<a href="#">Apps Setting</a>
					<i class="icon-angle-right"></i> 
				 </li>
				
                 
            </ul>
                         <div class="row-fluid">
                        <div class="box span12">
                        <div class="box-header" data-original-title>
						<h2><i class="icon-plus"></i><span class="break"></span>Apps Setting</h2>
						 
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
                <fieldset>
                 
                   <div class="control-group">
							  <label class="control-label" for="">Apps Name</label>
                 <div class="controls">
                	   <input type="text" name="app_name" id="app_name" value="<?php echo $settings_row['app_name'];?>" class="input-xlarge">
     
                    		</div>
							</div>
                
 				<div class="control-group">
				<label class="control-label" for="rss_title">Apps Logo</label>
				<div class="controls">
				  <input type="file" name="app_logo" id="app_logo" value="" class="input-xlarge">      
          
          <?php if($settings_row['app_logo']){?><img src="upload/<?php echo $settings_row['app_logo'];?>" width="72" width="72" /><?php }?>
      			</div>
		        </div>
                
                 <div class="control-group">
				<label class="control-label" for="rss_link">Apps Email</label>
				<div class="controls">
		<input type="text" name="app_email" id="app_email" value="<?php echo $settings_row['app_email'];?>" class="input-xlarge">
        
        		</div>
		        </div>
                 <div class="control-group">
				<label class="control-label" for="rss_link">Apps Website</label>
				<div class="controls">
		 <input type="text" name="app_website" id="app_website" value="<?php echo $settings_row['app_website'];?>" class="input-xlarge">        
       
        		</div>
		        </div>
                 <div class="control-group">
				<label class="control-label" for="rss_link">Apps Description</label>
				<div class="controls">
		 <textarea name="app_description" id="app_description" class="input-xlarge" cols="60" rows="10"><?php echo $settings_row['app_description'];?></textarea>
        
         								 <script>                             
                            CKEDITOR.replace( 'app_description' );
                        </script>
                
       
        		</div>
		        </div>
                
            
            
                <div class="form-actions">
			    <button type="submit" name="submit" class="btn btn-primary">Save Settings</button>
			    </div>
				</fieldset>
				</form>   
 			    </div>
				</div><!--/span-->

			</div><!--/row-->
            </div>			

<?php include('includes/footer.php');?>                  