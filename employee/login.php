<?php
session_start();
require "../lib.php";

$username = $_POST["username"];
$password = $_POST["password"];

$query = "select * from employee where username like '$username' and password like '$password';";
$result = mysql_query($query,$conn);

mysql_num_rows($result) or die("login not success");

echo "login success !!!!! Welcome ".$username";

if($_SESSION['code']=='') {
	$row = mysql_fetch_array($result);
	$code = getCode($row['store'],$row['NS'],$row['EW']);
	$_SESSION['code']=$code;
}
?>


