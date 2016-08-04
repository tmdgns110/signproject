<?php
/* db_connect.php accesses the database(MySQL). */
/* the function db_con() accesses MySql and selects the DB.
   And then, return the variable about connection to db */

function db_con()
{
	$host = "localhost";
	$user = "root";
	$pwd = "1234";
 
	$db_name = "EM";
  
	$con = mysql_connect($host,$user,$pwd);
	if($con==0) echo mysql_error();

	mysql_select_db($db_name, $con);
	mysql_set_charset('utf8');
	return $con;
}

$conn = db_con();
?>
