<?php include('includes/header.php');?>
<?php include('includes/menu.php');?>
<?php 

    include('includes/function.php');

	include('messages/messages.php');
	require_once("thumbnail_images.class.php"); 
  
   
   	mysql_query("SET NAMES 'utf8'");	

 	if(isset($_POST['submit']))
	{
		
 	
		
		

	
		    						                 $data = array(
														'message' => $_POST['msg'],
														);		
 					  $qry = Insert('normal_notification',$data);									
  		
			
			
			
    						                           									
  					  $_SESSION['msg']="14";
					  header( "Location:normal_notification.php");
					  exit;
 	}
	

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
                rules: {				msg: "required"
 									 
   									},
                 messages: { 
										  msg: "Please enter  Message"
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
					<a href="normal_notification.php">Manage Normal Notification</a>
					<i class="icon-angle-right"></i> 
				 </li>
				
                 <li>
					<i class="icon-plus"></i>
					<a href="#">Add Normal Notification</a>
				</li>
            </ul>
                         <div class="row-fluid">
                        <div class="box span12">
                        <div class="box-header" data-original-title>
						<h2><i class="icon-plus"></i><span class="break"></span>Add Normal Notification</h2>
						 
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
				<label class="control-label" for="tag">Message</label>
				<div class="controls">
			  <input type="text" name="msg" id="app_logo" value="" class="input-xlarge">      
          
        		</div>
		
                </div>
                
                
                <div class="form-actions">
			    <button type="submit" name="submit" class="btn btn-primary">Add Notification</button>
			    </div>
				</fieldset>
				</form>   
 			    </div>
				</div><!--/span-->

			</div><!--/row-->
            </div>			

<?php include('includes/footer.php');?>                  