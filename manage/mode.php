<html><body>
<center><h1>Project Excuse Me</h1><br>
<?php
	session_start();
	$temp = $_GET['store'];

	if($temp!=NULL) $_SESSION["session_store"] = $temp;
//source project_search.php
	include("../lib.php");

	$store = $_SESSION["session_store"];

	echo "<h3>Store <font color = red>$store</font> Menu list<br>";

	$qry1 = "select item,price from menu where store = '$store'";
	$res1 = mysql_query($qry1,$conn);

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
<h3><font color = red>[Manage Mode]</font> Select the mode.</h3>
<h4><font color = grey>
<form method = "GET" action = "register.php">
	Resgister menu list on your store. <input type = "submit" value = "Register"></form>
<form method = "GET" action = "delete.php">
	Delete menu list on your store. <input type = "submit" value = "Delete"></form>
<form method = "GET" action = "main.html">
	Back to main<input type = "submit" value = "main"></form>
</font></h4></center><br>

</body></html>


