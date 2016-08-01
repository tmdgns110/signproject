<?php 
require "conn.php";
$user_name = $_POST["user_name"];
$mysql_qry = "select * from employee_data where username like '$user_name';";
$result = mysqli_query($conn ,$mysql_qry);

if(mysqli_num_rows($result) > 0) {
echo "it is existed!";
}
else {
echo "register possible!";
}

?>
