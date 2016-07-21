<?php 
require "conn.php";
$store = $_POST["store"];
$mysql_qry = "select * from employee_data where store like '$store';";
$result = mysqli_query($conn ,$mysql_qry);

if(mysqli_num_rows($result) > 0) {
echo "it is existed!";
}
else {
echo "register possible!";
}

?>
