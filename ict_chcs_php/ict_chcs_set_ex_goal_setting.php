<?php
	define("HOST","localhost");
	define("USER","ict_chcs");
	define("PASSWD","ict_chcs");
	define("DB_NAME","ict_chcs");
  	define("TABLE_NAME","ex_goal_setting");

	$id = $_GET['id']; 
	$ex_variety = $_GET['ex_variety'];
	$ex_heartplus = $_GET['ex_heartplus'];
	$ex_calories = $_GET['ex_calories'];
	$ex_distance = $_GET['ex_distance'];
	$ex_speed = $_GET['ex_speed'];
	
	$connect = mysql_connect(HOST, USER, PASSWD) or die("Fail to connect to SQL Server");

	mysql_select_db(DB_NAME, $connect);

	//_GET[fIELD1] is the first parameter that you have get from Adnroid Applcation 
	if($id && $ex_variety) {
		$sql = "select * from ex_goal_setting where `id` = '$id' and `ex_variety` = '$ex_variety'";
		
		session_start();

		// store the query execution result into the variable $result.
		$result = mysql_query($sql, $connect);

		//Save the the number of the records returned.
		$total_record = mysql_num_rows($result);

		if(!$total_record)  {	
			//insert
			$sql = "INSERT INTO `ex_goal_setting`(`id`, `ex_variety`, `ex_heartplus`, `ex_calories`, `ex_distance`, `ex_speed`) VALUES('$id', '$ex_variety', '$ex_heartplus', '$ex_calories', '$ex_distance', '$ex_speed')";
		}
		else  {
			//update
			$sql = "UPDATE `ex_goal_setting` SET ";
			if($ex_heartplus) $sql .= ",`ex_heartplus`='$ex_heartplus'";
			if($ex_calories) $sql .= ",`ex_calories`='$ex_calories'";
			if($ex_distance) $sql .= ",`ex_distance`='$ex_distance'";
			if($ex_speed) $sql .= ",`ex_speed`='$ex_speed'";
			
			$sql .= " WHERE `id` = '$id' AND `ex_variety` = '$ex_variety'";
			$sql = str_replace("SET ,", "SET ", $sql);
		}

		//echo "$sql";
		$result = mysql_query( $sql, $connect);
		mysql_close($connect);

		if(!$result) {
			echo "{\"status\":\"NOK\"}";
		}
		else {
			echo "{\"status\":\"OK\"}";
		}
	}
	else {
		echo "{\"status\":\"NOK\"}";
	}	

?>
