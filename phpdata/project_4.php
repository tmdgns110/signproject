<html><body>
<center><h1>Project Excuse Me</h1><br>

<?php
include("include/project.inc");
$con = db_con();

$name = $_GET['name'];
$price= $_GET['price'];
$store = $_GET['store'];

$qry1 = "insert into menu values('$name','$price','$store')";
$res1 = mysql_query($qry1, $con);

echo "you've just registered store <font color = red>$store</font>.<br>";

?>

<form method = "GET" action = "project.html">
	<h3>Back to main</h3>
	<input type = "submit" value = "main">
</form></center><br><br>
