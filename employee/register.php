<?php 
require "../lib.php";

$username = $_POST["username"];
$password = $_POST["password"];
$store = $_POST["store"];
$email = $_POST["email"];
$hp = $_POST["hp"];
$latitude = $_POST["latitude"]*1000000;
$longitude = $_POST["longitude"]*1000000;

$query = "select * from employee where username like '$username'";
$result = mysql_query($query,$conn);

$query2 = "insert into employee(username,password,store,NS,EW,email,hp,date) values ('$username','$password','$store',$latitude,$longitude,'$email','$hp',now())";
$result2 = mysql_query($query2,$conn);

$query3 ="insert into storeList(store,date,NS,EW) values ('$store',now(),'$latitude','$longitude')";
$result3 = mysql_query($query3,$conn);

if($result && $result2 && $result3){
echo 'success';
}
else{
echo 'failure';
exit;
}

?>

