<html><body>
<center><h1>Project Excuse Me</h1><br>

<?php
include("lib.php");

$store = $_GET['store'];
$branch = $_GET['branch'];

$code = getCode($store,$branch);

listingMenu($code,"default");

?>

</body></html>
