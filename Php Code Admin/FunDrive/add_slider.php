<?php include('includes/header.php');?>
<?php include('includes/menu.php');?>
<?php 

    include('includes/function.php');

	include('messages/messages.php');
	require_once("thumbnail_images.class.php"); 
  
   	mysql_query("SET NAMES 'utf8'");	

 	if(isset($_POST['submit']))
	{
		
		
		$category_image=rand(0,99999)."_".$_FILES['category_image']['name'];
				    $pic1=$_FILES['category_image']['tmp_name'];
					
					 $tpath1='upload/'.$category_image;
						
					     copy($pic1,$tpath1);
		
		
    						                            $data = array(											 
  													    'name'  =>  $_POST['category_name'],
														'image'  =>  $category_image,
														'link' => $_POST['link']
    												    );		
 					  $qry = Insert('tbl_slider',$data);									
  					  $_SESSION['msg']="43";
					  header( "Location:manage_slider.php");
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
            $("#add_category").validate({
                rules: {
                                        category_name: "required",
										category_image : "required",
 										
 									 
   									},
                 messages: { 
										 category_name: "Please enter Category Name",
										 category_image: "Please enter Category Image",
 										
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
					<a href="manage_slider.php">Manage Slider</a>
					<i class="icon-angle-right"></i> 
				 </li>
				
                 <li>
					<i class="icon-plus"></i>
					<a href="#">Add Slider</a>
				</li>
            </ul>
                        
                        <div class="row-fluid">
                        <div class="box span12">
                        <div class="box-header" data-original-title>
						<h2><i class="icon-plus"></i><span class="break"></span>Add Slider</h2>
						 
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
                <form id="add_category" class="form-horizontal" method="post" action="" enctype="multipart/form-data">
                <input type="hidden" name="id" value="<?php echo $_GET['id'];?>" />
                <fieldset>
                 
				<div class="control-group">
				<label class="control-label" for="category_name">Name</label>
				<div class="controls">
				<input type="text" class="span6 typeahead" name="category_name" id="category_name" value="">
 				</div>
		        </div>
                <div class="control-group">
				<label class="control-label" for="category_name">Link</label>
				<div class="controls">
				<input type="text" class="span6 typeahead" name="link" id="category_name" value="">
 				</div>
		        </div>
                
                 <div class="control-group">
				 <label class="control-label" for="category_image">Images</label>
                 <div class="controls">
                 <input type="file" name="category_image" id="category_image">
                 </div>
                 </div>
                  
                <div class="form-actions">
			    <button type="submit" name="submit" class="btn btn-primary">Add Slider</button>
			    </div>
				</fieldset>
				</form>   
 			    </div>
				</div><!--/span-->

			</div><!--/row-->
            </div>			

<?php include('includes/footer.php');?>                  