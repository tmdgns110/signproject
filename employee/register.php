<?php 
require "../lib.php";
$username = $_POST["username"];
$password = $_POST["password"];
$store = $_POST["store"];
$branch = $_POST["branch"];

$query = "select * from employee where username like '$username'";
$result = mysql_query($query,$conn);
$query2 =  "select * from employee where store like '$store' and branch like '$branch'";
$result2 = mysql_query($query2,$conn);

if(mysql_num_rows($result) > 0) {
echo "it is existed check username!";
exit;
}
else if(mysql_num_rows($result2) > 0) {
echo "it is existed check store and branch!";
exit;
}
else{
$query3 = "insert into employee(username,password,store,branch,email,hp) values ('$username','$password','$store','$branch','email','hp');";
$result3 = mysql_query($query3,$conn);

//$result = mysqli_query($conn,"insert into employee_data (username,password,store,branch) values ('$user_name','$user_pass','$store','$branch')");

if($result3){
echo 'success';
}
else{
echo 'failure';
}
}
?>



