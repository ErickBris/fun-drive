<?php include("includes/db_connection.php");
 
if($_POST["email"]=='') { 
     
    	$_SESSION['msg']="44";
		header( "Location:index.php");
	
}
else if(!filter_var($_POST["email"], FILTER_VALIDATE_EMAIL)) { 
     
    	$_SESSION['msg']="43";
		header( "Location:index.php");
			
}
else
{
	$qry="select * from tbl_admin where email='".$_POST["email"]."'";
		 
		$result=mysql_query($qry);
		$row=mysql_fetch_assoc($result);
	
	if($row > 0)
		{
	
				$pass = rand(1,1000);
				$pass1 = md5($pass);
				$qry = "update tbl_admin set password = '$pass1' where id='".$row["id"]."'"; 
				mysql_query($qry)or die(mysql_error());
				
				$to = $row['email'];
			
				// subject
				$subject = 'DBox admin password';	
				
				
				$message = '
							<div>
							   <strong>Username</strong>:'.$row['username'].'<br>				    
							   <strong>Password</strong> :'.$pass.'<br>				    
							 </div>
							';
				
				 
				$headers = 'MIME-Version: 1.0' . "\r\n";
				$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
			
				// Mail it
			   if(@mail($to, $subject, $message, $headers)){
				
				$_SESSION['msg']="42";
						header( "Location:index.php?pass=$pass");
			   }
			
			   else
			   {
					
						$_SESSION['msg']="40";
						header( "Location:index.php");
			   }
		}
		else
		{
			
						$_SESSION['msg']="41";
						header( "Location:index.php");
 
		}
}	
?>