<?php
include "lib.php";

//some of values were parsed by Android as POST method
$store = $_POST['store'];
$latitude = $_POST['latitude']*1000000;
$longitude = $_POST['longitude']*1000000;

//and then, draw it to log file.
$fp = fopen('/var/www/html/log.txt', 'a');
if(!$fp) print("error<br>");
$outstring = "store:".$store."\t\tlatitude:".$latitude."\t\tlongitude:".$longitude."\n";
fwrite($fp,$outstring,strlen($outstring));
fclose($fp);

//First, get all stores ranged in 500m.
$storeList = getStoreList($latitude,$longitude);
//Second, compare the store that was parsed to storeList by scoring algorithm.
$name = scoring($store,$storeList);
//Last, print menu information fommated by JSON.
listingStore($name);
?>
