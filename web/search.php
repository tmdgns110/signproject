<?php
session_start();
include "../lib.php";

$store = $_POST['store'];
$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];

$storeList = getStoreList($latitude,$longitude);

##################################################
############인식률을 높일 수 있는 모듈부분 #######
############.............................. #######
############.............................. #######
##################################################

listingStore($storeList);

?>
