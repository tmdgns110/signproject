<?php 
require "../lib.php";
$username = $_POST["username"];
$password = $_POST["password"];

$query = "select * from employee where username like '$username' and password like '$password';";
$result = mysql_query($query,$conn);

if(mysql_num_rows($result) > 0) {
echo "login success !!!!! Welcome ".$username." <br>";
}
else {
echo "login not success";
}

?>


