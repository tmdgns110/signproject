<?php 
require "../lib.php";
$store = $_POST["store"];
$branch = $_POST["branch"];

$query = "select * from employee where store like '$store' and branch like '$branch';";
$result = mysql_query($query,$conn);


if(mysql_num_rows($result) > 0) {
echo "it is existed!";
}
else {
echo "register possible!";
}

?>
