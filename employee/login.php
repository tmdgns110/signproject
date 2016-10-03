<?php
require "../lib.php";
/* it works about login process */
$username = $_POST["username"];
$password = $_POST["password"];

$query = "select * from employee where username like '$username' and password like '$password';";
$result = mysql_query($query,$conn);

mysql_num_rows($result) or die("login not success");

echo "login success !!!!! Welcome ".$username;

?>


