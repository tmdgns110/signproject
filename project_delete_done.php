<html><body>
<center><h1>Project Excuse Me</h1><br>

<?php
session_start();

include("include/project.inc");
$con = db_con();

$item = $_GET['item'];
$price= $_GET['price'];
$store = $_SESSION['session_store'];
$branch = $_GET['branch'];

$qry1 = "delete from menu where item = '$item'";
$res1 = mysql_query($qry1, $con);

echo "you've just deleted store <font color = red>$store</font>.<br>";


//source project_search.php
echo "<h3>Store <font color = red>$store</font> Menu list<br>";

$qry2 = "select item,price from menu where store = '$store'";
$res2 = mysql_query($qry2,$con);

$row = mysql_num_rows($res2);
$col = mysql_num_fields($res2);

if(!$row) 
{
	echo "Empty data!";
}
else
{
	echo "<table width=500 border = 1>";

	echo "<tr><h3><th><font color = magenta>MENU</font></th></h3>";
	echo "<h3><th><font color = grey>PRICE</font></th></h3>";

	while($row1 = mysql_fetch_row($res2))
	{
		echo "<tr>";
		for($i=0; $i<$col; $i++)
		{
			echo "<td>";
			if($i==1) echo "$";
			echo "$row1[$i]</td>";
		}
	}
	echo "</table>";

	echo "<script>location.href('project_manage_mode.php');</script>";
}

?>

<form method = "GET" action = "project_manage_mode.php">
	<h3>Back to manage mode
	<input type = "submit" value = "Go"></h3>
</form></center><br><br>
