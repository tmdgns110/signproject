<?php 
require "conn.php";
$user_name = $_POST["user_name"];
$user_pass = $_POST["password"];
$store = $_POST["store"];
$branch = $_POST["branch"];


$mysql_qry = "select * from employee_data where username like '$user_name';";
$result = mysqli_query($conn ,$mysql_qry);
$mysql_qry1 =  "select * from employee_data where store like '$store' and branch like '$branch';";
$result1 = mysqli_query($conn , $mysql_qry1);

if(mysqli_num_rows($result) > 0) {
echo "it is existed check username!";
exit;
}
else if(mysqli_num_rows($result1) > 0) {
echo "it is existed check store and branch!";
exit;
}
else{
$mysql_qry2 = "insert into employee_data (username,password,store,branch) values ('$user_name','$user_pass','$store','$branch');";
$result2 = mysqli_query($conn ,$mysql_qry2);

//$result = mysqli_query($conn,"insert into employee_data (username,password,store,branch) values ('$user_name','$user_pass','$store','$branch')");

if($result2){
echo 'success';
}
else{
echo 'failure';
}
}
?>



