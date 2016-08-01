<html><body>
<center><h1>Project Excuse Me</h1><br>

<?php
include("include/project.inc");
$con = db_con();

$name = $_GET['name'];
$price= $_GET['price'];
$store = $_GET['store'];

if($name) $qry1 = "update menu set name = '$name' where store = '$store'";
else if($price) $qry1 = "update menu set price = '$price' where store = '$store'";

$res1 = mysql_query($qry1, $con);

echo "you've just registered store <font color = red>$store</font>.<br>";

?>

<form method = "GET" action = "main.html">
	<h3>Back to main</h3>
	<input type = "submit" value = "main">
</form></center><br><br>
