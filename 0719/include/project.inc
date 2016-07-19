<?php

function db_con()
{
	$host = "localhost";
	$user = "u126554307_admin";
	$pwd = "jgyadmin360!!";

	$con = mysql_connect($host,$user,$pwd);
	if($con == 0) echo mysql_error();
	mysql_query("SET NAMES utf8");

	mysql_select_db("u126554307_em", $con);

	return $con;
}
?>
