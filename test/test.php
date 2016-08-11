<?php
	function listingMenu($code,$opt)
	{
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
			$query = "select menu, price from menuList where code like $code";
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

				print("<table width=500	bordor = 1>");
				print("<tr>
					<h3><th><font color = magenta>MENU</font></th></h3>
					<h3><th><font color = grey>PRICE</font></tah></h3>
				       </tr>");	

				$col = 2 // 인덱스순서대로 0번째 메뉴, 1번째 가격이므로 2개필그만
				while($row = mysql_fetch_row($result)) {
					print("<tr>");

					for($i=0; $i<$col; $i++) {
						print("<td>".$row[$i]."</td>";
					}

				}
			}
			print("</table>");

			return true;
		}
	}
	listingMenu("a",1);

?>
