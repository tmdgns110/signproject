<?php 
require "../lib.php";
$username = $_POST["username"];
$query = "select * from employee where username like '$username';";
$result = mysql_query($query ,$conn);

if(mysql_num_rows($result) > 0) {
echo "it is existed!";
}
else {
echo "register possible!";
}

?>