<?php
/* the file named "lib.php" includes some library information/function. */
/* it consisted of 4 prime files. it used to be consistency between developers 
   and re-use some repeated routines. */
/* first, lib_dir is the variable that indicates some prime files. */

	$lib_dir = "include";

/* the specification about the *.php */
/* 1.config.php consisted of some informations that defined likes macro */
/* 2.db_connect.php accesses the database(MySQL). */
/* 3.db_table_name.php defines some table names. */
/* 4.function.php defines some functions that we need. */

	include "{$lib_dir}/config.php";
	include "{$lib_dir}/db_connect.php";
	include "{$lib_dir}/db_table_name.php";
	include "{$lib_dir}/function.php";
?>

