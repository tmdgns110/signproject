<?php

function db_con()  //수정수정
{
	$host = "localhost";
	$user = "root";
	$pwd = "i'mthere";

	$con = mysql_connect($host,$user,$pwd);
	if($con==0) echo mysql_error();

	mysql_select_db('EM', $con);
	return $con;
}

?>
