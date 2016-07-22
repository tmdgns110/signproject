<?php 
require "conn.php";
$user_name = $_POST["user_name"];
$user_pass = $_POST["password"];
$store = $_POST["store"];
$branch = $_POST["branch"];


$mysql_qry = "select * from employee_data where username like '$user_name' and password like '$user_pass' and store like '$store' and branch like '$branch';";
$result = mysqli_query($conn ,$mysql_qry);

if(mysqli_num_rows($result) > 0) {
echo "it is existed!";
exit;

}
/*
else {
echo "register possible!";
}
*/

$mysql_qry1 = "insert into employee_data (username,password,store,branch) values ('$user_name','$user_pass','$store','$branch');";
$result1 = mysqli_query($conn ,$mysql_qry1);

//$result = mysqli_query($conn,"insert into employee_data (username,password,store,branch) values ('$user_name','$user_pass','$store','$branch')");

if($result1){
echo 'success';
}
else{
echo 'failure';
}

?>

/*
if(mysqli_num_rows($result) > 0) {
echo "register success";
}
else {
echo "register not success";
}

?>
*/

