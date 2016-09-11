<?php
session_start();
include "../lib.php";
include "../include/function2.php";
$code = $_SESSION['code'];

$add = $_POST['add'];
$delete = $_POST['delete'];
$update = $_POST['update'];

//__________________________________function to add, update and delete______________________________
if($_POST['act_add']) {
	$menu = $_POST['menu'];
	$price = $_POST['price'];
	$info = $_POST['info'];

	addMenu($code,$menu,$price,$info);
}

else if($_POST['act_update']) {
	$menu = $_POST['menu'];
	$price = $_POST['price'];
	$info = $_POST['info'];
	$newMenu = $_POST['newMenu'];
	$newPrice = $_POST['newPrice'];
	$newInfo = $_POST['newInfo'];

	updateMenu($menu,$price,$info,$newMenu,$newPrice,$newInfo);
}

else if($_POST['act_delete']) {
	$menu = $_POST['menu'];

	deleteMenu($menu);
}

print("<hr>");

//__________________________________function to add, update and delete______________________________
listingMenuforweb($code,"default");

print("<hr>");


if( !($add or $delete or $update) ) { // It is the case that employee don't click anything.
	print("
		<form method='POST' action='mainforweb.php'>
		<table>
		<tr>
		   <td><input type='submit' name='add' value='add'></td>
		   <td><input type='submit' name='delete' value='delete'></td>
		   <td><input type='submit' name='update' value='update'></td>
		</tr>
		</table>
		</form>
	");
}

else {
	if($add) {
		print("
			<form method='POST' action='mainforweb.php'>
			<table>
			<tr>
			   <p><td>Menu </td><td><input type='text' name='menu'></td>
			</tr>
			<tr>
			   <p><td>Price </td><td><input type='text' name='price'></td>
			</tr>
			<tr> 
			   <p><td>Info </td><td><input type='text' name='info'></td>
			</tr>
			</table>
			<input type='submit' name='act_add' value='register the menu' style='width:222;'>");
	}

	else if($update) {
		print("
			<form method='POST' action='mainforweb.php'>
			<table>
			<tr>
			   <p><td>Insert</td><td> your menu information that have just placed.</td>
			</tr>
			<tr>
			   <p><td>Menu </td><td><input type='text' name='menu'></td>
			</tr>
			<tr>
			   <p><td>Price </td><td><input type='text' name='price'></td>
			</tr>
			<tr> 
			   <p><td>Info </td><td><input type='text' name='info'></td>
			</tr>
			<tr>
			   <p><td><font color='red'>Insert</td><td> replace menu information that will placed.</font></td>
			</tr>
			<tr>
			   <p><td>Menu </td><td><input type='text' name='newMenu'></td>
			</tr>
			<tr>
			   <p><td>Price </td><td><input type='text' name='newPrice'></td>
			</tr>
			<tr> 
			   <p><td>Info </td><td><input type='text' name='newInfo'></td>
			</tr>
			</table>
			<input type='submit' name='act_update' value='update the menu' style='width:222;'>");
	}

	else {
		print("
			<form method='POST' action='mainforweb.php'>
			<table>
			<tr>
			   <p><td>Menu </td><td><input type='text' name='menu'></td>
			</tr>
			</table>
			<input type='submit' name='act_delete' value='delete the menu' style='width:222;'>");
	}

}


	
?>
