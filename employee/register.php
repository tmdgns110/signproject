<?php 
require "../lib.php";

$username = $_POST["username"];
$password = $_POST["password"];
$store = $_POST["store"];
$branch = $_POST["branch"];
$email = $_POST["email"];
$hp = $_POST["hp"];

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
$query3 = "insert into employee(username,password,store,branch,email,hp) values ('$username','$password','$store','$branch','$email','$hp');"
$result3 = mysql_query($query3,$conn);

if($result3){
echo 'success';
}
else{
echo 'failure';
}
}


/* employee 테이블 모든 값 불러오기 */
$res4=mysql_query('select * from employee', $conn);

for($i=0;$i<mysql_num_rows($res4);$i++) {
	$label = mysql_fetch_array($res4);
	echo $label[0].".".$label[1].".".$label[2].".".$label[3]."<br>";
}
/////////////////////////////////////////////


?>

