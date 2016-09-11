<?php
include "lib.php";

$x1 = 37123522;
$y1 = 127123976;
$x2 = 37133522;
$y2 = 127123976;

$storelist = getStoreList($x1,$y1);
$count = sizeof($storelist);

if(!$count) print("Don't search");
else if($count==1) listingMenu($storelist[0]['code'],"dafault");

?>
