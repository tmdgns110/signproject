<?php
/* create all tables that we need. */
include "../lib.php";
$con = db_con(); // use the function db_con() to set the database.

/*____________________________________________________________________*/
/* create the table named "employee" */
$result = mysql_query("create table ".$employee."(".
		"username varchar(20) not null,".
		"password varchar(20) not null,".
		"store varchar(20) not null,".
	 	"NS int not null,".
   		"EW int not null,".
		"email varchar(20),".
		"hp varchar(20),".
		"date date".
		")");
if(!$result) echo mysql_errno().": ".mysql_error()."<br>";
else echo "You've just created the table ".$employee."<br>";

/*____________________________________________________________________*/
/* create the table named "storeList" */
$result2 = mysql_query("create table ".$storeList."(".
		"store varchar(30) not null,".
		"date date,".
		"code int not null auto_increment primary key,".
   		"NS int not null,".
   		"EW int not null".	
		")");
if(!$result2) echo mysql_errno().": ".mysql_error()."<br>";

else echo "You've just created the table ".$storeList."<br>";
/*____________________________________________________________________*/
/* create the table named "menuList" */
$result3 = mysql_query("create table ".$menuList."(".
		"menu varchar(30) not null,".
		"price int not null,".
		"info varchar(50),".
		"date date,".
		"code int not null,".
		"foreign key(code) references storeList(code) ".
		"on delete cascade".
		")");
if(!$result3) echo mysql_errno().": ".mysql_error()."<br>";
else echo "You've just created the table ".$menuList."<br>";

?>
