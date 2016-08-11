<?php
/* function.php defines some functions that we need. */
	function deleteMenu($menu)
	{
		GLOBAL $conn;
		$query = "delete from menuList where menu like '$menu'";
		$result = mysql_query($query,$conn) or die(mysql_errno().":".mysql_error()."<br>");
	}

	function updateMenu($menu, $price, $info, $newMenu, $newPrice, $newInfo)
	{
		GLOBAL $conn;
		$query = "update menuList set
				menu = '$newMenu', price = $newPrice, info = '$newInfo', date = now()
				where menu = '$menu' and price = $price and info = '$info'";
		$result = mysql_query($query,$conn) or die(mysql_errno().":".mysql_error()."<br>");
	}

	function addMenu($code, $menu, $price, $info=NULL)
	{
		GLOBAL $conn;
		$query = "insert into menuList(menu,price,info,date,code) values('$menu',$price,'$info',now(),$code)";
		$result = mysql_query($query,$conn) or die(mysql_errno().":".mysql_error()."<br>");
	}	

	function getCode($store,$branch=NULL)
	{
		GLOBAL $conn;
		$query = "select code from storeList where store like '$store'";
		if($branch!=NULL) $query .= " and branch like '$branch'";

		$result = mysql_query($query,$conn);

		mysql_num_rows($result) or die("Empty data!<br>");
		mysql_num_rows($result) or die("there is too many store more than one.<br>
						please set teh name of the branch<br>");

		$field = mysql_fetch_row($result);
		$code = $field[0];

		return $code;
	}

	function isNewestItem($date)
	{
		$newestDate = date("Y-m-d", strtotime("-7 day"));

		if($date>$newestDate) // the parameter named date are newer than newestData,
			return true;
		else false;
	}

	function postImg() 
	{
		print("<img src='../include/new.gif'>");
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

			if(!mysql_num_rows($result)) {
			// it means that the menuList doens't exist about the code.
			// so we must derive registering the menuList from the employee.

				print("<h3>Sorry, It is empty set.<br>");
				print("You've to register the menu information about own's store.");

				//등록옵션 구현
				return false;
			}
				
			else {
			// listing the information about the menu that is placed to own's store

				print("<table width=500	border=1>");
				print("<tr>
					<h3><th><font color = magenta>MENU</font></th></h3>
					<h3><th><font color = grey>PRICE</font></th></h3>
					<h3><th><font color = grey>Info</font></th></h3>
				       </tr>");	

				$col = 3; // 0번째인덱스 메뉴, 1번째인덱스 가격 2번째인덱스 info => 3index
				while($row = mysql_fetch_row($result)) {
					print("<tr>");
					for($i=0; $i<$col; $i++) {
						print("<td>".$row[$i]);
						if($i==0 && isNewestItem($row[3])) postImg();
						print("</td>");
					}
					print("</tr>");
				}
			}
			print("</table>");
		}
	}
?>
