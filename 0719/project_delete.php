<html><body>
<center><h1>Project Excuse Me</h1><br>

<?php
session_start();

//source project_search.php
	include("include/project.inc");
$con = db_con();

$store = $_SESSION["session_store"];

echo "<h3>Store <font color = red>$store</font> Menu list<br>";

$qry1 = "select item,price from menu where store = '$store'";
$res1 = mysql_query($qry1,$con);

$row = mysql_num_rows($res1);
$col = mysql_num_fields($res1);

if(!$row) 
{
	echo "Empty data!";
}
else
{
	echo "<table width=500 border = 1>";

	echo "<tr><h3><th><font color = magenta>MENU</font></th></h3>";
	echo "<h3><th><font color = grey>PRICE</font></th></h3>";

	while($row1 = mysql_fetch_row($res1))
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
}
//end
?>

<h3>Delete Your <font color = red>Store</font></h3><br>

<form method = "GET" action = "project_delete_done.php">
	ITEM : <input type = "text" size 20 name = 'item'><p>
	PRICE : <input type = "text" size 5 name = 'price'><p>
	BRANCH : <input type = "text" size 20 name = 'branch'><p>
	<input type = "submit" value = "Register">
</form></center><br><br><br>

<hr size=2>

</body></html>



