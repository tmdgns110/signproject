<?php
include "../lib.php";

$id = $_POST['ID'];
$query = "select * from employee where username like '$id'";
$result = mysql_query($query,$conn);
$row = mysql_fetch_array($result);
$code = getCode($row['store'],$row['NS'],$row['EW']);

//__________________________________function to add and delete______________________________
if($_POST['type']=="act_add") {
	$menu = $_POST['menu'];
	$price = $_POST['price'];
	$info = $_POST['info'];

	addMenu($code,$menu,$price,$info);
}
else if($_POST['type']=="act_del") {
	$menu = $_POST['menu'];

	deleteMenu($menu);
}

listingMenu($code,"default");
	
?>
