<?php
/* function.php defines some functions that we need. */
	function deleteMenu($menu)
	{
		GLOBAL $conn;
		$query = "delete from menuList where menu like '$menu'";
		$result = mysql_query($query,$conn) or die(mysql_errno().":".mysql_error()."<br>");
	}

	function addMenu($code, $menu, $price, $info=NULL)
	{
		GLOBAL $conn;
		$query = "insert into menuList(menu,price,info,date,code) values('$menu',$price,'$info',now(),$code)";
		$result = mysql_query($query,$conn) or die(mysql_errno().":".mysql_error()."<br>");
	}	

	function getCode($store,$NS=NULL,$EW=NULL) // NULL is only debugging mode.
	{
		GLOBAL $conn;
		$query = "select code from storeList where store like '$store'";
		if($NS && $EW) $query .= " and NS like $NS and EW like $EW";
		$result = mysql_query($query,$conn);

		mysql_num_rows($result) or die("Empty data!<br>");

		$field = mysql_fetch_row($result);
		$code = $field[0];

		return $code;
	}

	function getStoreList($NS,$EW)
	{
		GLOBAL $conn;
		$query = "select * from storeList";
		$result = mysql_query($query,$conn);
		$total_store = mysql_num_rows($result);

		for($i=0;$i<$total_store;$i++)
	        {
       			mysqli_data_seek($result, $i);
	                $row=mysql_fetch_array($result);

			$distance = calDistance($NS,$EW,$row[NS],$row[EW]);
				if($distance<5000) // less than 500m
				$array[] = array("store"=>$row[store], "code"=>$row[code],
						"NS"=>$row[NS], "EW"=>$row[EW]);
		}
		return $array;	
	}

	function scoring($name, $array)
	{
		$origin_name = trim($name);
		$n = strlen($name);
		
		$count = sizeof($array);
		$score[$count];
		for($i=0;$i<$count;$i++) {
			$score[$i]=0;
			$cmp_name = $array[$i]['store'];
			$m = strlen($cmp_name);

			for($j=1;$j<=$n&&$j<=$m;$j++) {
				for($k=0;$k<=$n-$j;$k++) {
					$temp = substr($origin_name,$k,$j);
					if(strstr($cmp_name,$temp)) $score[$i] += $j;
					else $score[$i] -= 1;
				}
			}
		}
		$MaxScore = $score[0];
		$pos = 0;
		for($l=1;$l<$count;$l++) {
			if($MaxScore < $score[$l]) {
				$MaxScore = $score[$l];
				$pos = $l;
			}
		}
		$store = array(0=>$array[$pos]);
		return $store;
	}

	function listingMenu($code,$opt)
	{
		GLOBAL $conn;
		if($code=="") {
		// it means that the storeList doesn't exist about the code,
		// so we must derive registering the storeList from the employee.

			print("<h3>Sorry, It is empty set.<br>");
			print("You've to register own's store information<br>");
			
			//등록옵션 구현
			return false;
		}

		else {
		// it means that there is menuList about the code.
			switch($opt) {
		           case "menu_desc":
				$sort = " order by menu desc, price asc ";
				break;
			   case "price_asc":
				$sort = " order by price, menu ";
				break;
			   case "price_desc":
				$sort = " order by price desc, menu ";
				break;
			   default:
				$sort = " order by menu, price ";
			}

			$query = "select * from menuList where code like $code".$sort;
			$result = mysql_query($query,$conn)
				or die("$query<br>".mysql_errno().":".mysql_error());

			/* JSON PARSING _________________________________________________*/

			$total_record = mysql_num_rows($result);
			echo "{\"Status\":\"200\",\"num_results\":\"$total_record\",\"results\":[";
			for($i=0;$i<$total_record;$i++)
		        {
        			mysqli_data_seek($result, $i);
		                $row=mysql_fetch_array($result);
		                echo
		                "{\"menu\":\"$row[menu]\",\"price\":\"$row[price]\",\"info\":\"$row[info]\"}";

		                if($i<$total_record-1) echo ",";
      			}

		        echo "]}";
		
		}
	}

	function listingStore($storeList)
	{
		GLOBAL $conn;
		$count = sizeof($storeList);

		if($count==1) {
			listingMenu($storeList[0]['code'],"dafault");
		}
		else {
			$code = !$count ? 100 : 300;
			echo "{\"Status\":\"$code\",\"num_results\":\"$count\",\"results\":[";
			if(!$count) echo "{\"store\":\"don't search\"}";
			else for($i=0;$i<$count;$i++) {
		                echo "{\"store\":\"{$storeList[$i][store]}\"}";
		                if($i<$count-1) echo ",";
      			}
		        echo "]}";
		}
	}

	function calDistance($x1, $y1, $x2, $y2)
	{
		$subNS = $x2-$x1;
		$subEW = $y2-$y1;
		$distance = sqrt(pow($subNS,2) + pow($subEW,2));
		return $distance;
	}
?>
