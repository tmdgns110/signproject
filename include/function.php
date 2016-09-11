<?php
/* function.php defines some functions that we need. */
	function deleteMenu($menu)
	{
		GLOBAL $conn;
		$query = "delete from menuList where menu like '$menu'";
		$result = mysql_query($query,$conn) or die(mysql_errno().":".mysql_error()."<br>");
		echo "success to delete";
	}

/*	function updateMenu($menu, $price, $info, $newMenu, $newPrice, $newInfo)
	{
		GLOBAL $conn;
		$query = "update menuList set
				menu = '$newMenu', price = $newPrice, info = '$newInfo', date = now()
				where menu = '$menu' and price = $price and info = '$info'";
		$result = mysql_query($query,$conn) or die(mysql_errno().":".mysql_error()."<br>");
	}*/

	function addMenu($code, $menu, $price, $info=NULL)
	{
		GLOBAL $conn;
		$query = "insert into menuList(menu,price,info,date,code) values('$menu',$price,'$info',now(),$code)";
		$result = mysql_query($query,$conn) or die(mysql_errno().":".mysql_error()."<br>");
		echo "success to add";
	}	

	function getCode($store,$NS=NULL,$EW=NULL) // NULL is only debugging mode.
	{
		GLOBAL $conn;
		$query = "select code from storeList where store like '$store'";
		#################디버깅모드가아니면 if조건문은 무조건 수행되어야 함###
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
			if($distance<5000)
				$array[] = array("store"=>$row[store], "code"=>$row[code],
						"NS"=>$row[NS], "EW"=>$row[EW]);
		}
		return $array;	
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
			echo "{\"Status\":\"OK\",\"num_results\":\"$total_record\",\"results\":[";
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

		if(!$count) {
			print("Don't search");
			return false;
		}
		else if($count==1) {
			listingMenu($storeList[0][code],"dafault");
		}
		else {

			echo "{\"Status\":\"OK\",\"num_results\":\"$count\",\"results\":[";
			for($i=0;$i<$count;$i++)
		        {
		                echo
		                "{\"store\":\"{$storeList[$i][store]}\"}";
		                if($i<$total_record-1) echo ",";
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
