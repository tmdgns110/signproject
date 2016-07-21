<?php 
require "conn.php";
$branch = $_POST["branch"];
$mysql_qry = "select * from employee_data where branch like '$branch';";
$result = mysqli_query($conn ,$mysql_qry);

if(mysqli_num_rows($result) > 0) {
echo "it is existed!";
}
else {
echo "register possible!";
}

?>
