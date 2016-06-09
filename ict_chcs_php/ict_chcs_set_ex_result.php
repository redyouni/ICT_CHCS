<?php
	define("HOST","localhost");
	define("USER","ict_chcs");
	define("PASSWD","ict_chcs");
	define("DB_NAME","ict_chcs");
  	define("TABLE_NAME","ex_result");

	$id = $_GET['id']; 
	$ex_variety = $_GET['ex_variety'];
	$st_time = $_GET['st_time'];
	$en_time =  $_GET['en_time'];
	$ex_time =  $_GET['ex_time'];
	$ex_heartplus = $_GET['ex_heartplus'];
	$ex_calories = $_GET['ex_calories'];
	$ex_distance = $_GET['ex_distance'];
	$ex_speed = $_GET['ex_speed'];
	$ex_incline = $_GET['ex_incline'];
	$ex_level = $_GET['ex_level'];
	$ex_rpm = $_GET['ex_rpm'];
	$ex_watts = $_GET['ex_watts'];
	$ex_mets = $_GET['ex_mets'];

	$connect = mysql_connect(HOST, USER, PASSWD) or die("Fail to connect to SQL Server");

	mysql_select_db(DB_NAME, $connect);

	//_GET[fIELD1] is the first parameter that you have get from Adnroid Applcation 
	if($id && $ex_variety && $st_time) {
		$sql = "select * from ex_result where `id` = '$id' and `ex_variety` = '$ex_variety' and `st_time` = '$st_time'";
		//echo $sql;
		session_start();

		// store the query execution result into the variable $result.
		$result = mysql_query($sql, $connect);

		//Save the the number of the records returned.
		$total_record = mysql_num_rows($result);

		//echo $total_record;

		if(!$total_record)  {	
			//insert
			$sql = "INSERT INTO `ex_result`(`id`, `ex_variety`, `st_time`, `en_time`, `ex_time`, `ex_heartplus`, `ex_calories`, `ex_distance`, `ex_speed`, `ex_incline`, `ex_level`, `ex_rpm`, `ex_watts`, `ex_mets`) VALUES('$id', '$ex_variety', '$st_time', '$en_time', '$ex_time', '$ex_heartplus', '$ex_calories', '$ex_distance', '$ex_speed', '$ex_incline', '$ex_level', '$ex_rpm', '$ex_watts', '$ex_mets')";
		}
		else  {
			//update
			$sql = "UPDATE `ex_result` SET ";
			if($en_time) $sql .= ",`en_time`='$en_time'";
			if($ex_time) $sql .= ",`ex_time`='$ex_time'";
			if($ex_heartplus) $sql .= ",`ex_heartplus`='$ex_heartplus'";
			if($ex_calories) $sql .= ",`ex_calories`='$ex_calories'";
			if($ex_distance) $sql .= ",`ex_distance`='$ex_distance'";
			if($ex_speed) $sql .= ",`ex_speed`='$ex_speed'";
			if($ex_incline) $sql .= ",`ex_incline`='$ex_incline'";
			if($ex_level) $sql .= ",`ex_level`='$ex_level'";
			if($ex_rpm) $sql .= ",`ex_rpm`='$ex_rpm'";
			if($ex_watts) $sql .= ",`ex_watts`='$ex_watts'";
			if($ex_mets) $sql .= ",`ex_mets`='$ex_mets'";

			$sql .= " WHERE `id` = '$id' and `ex_variety` = '$ex_variety' and `st_time` = '$st_time'";
			$sql = str_replace("SET ,", "SET ", $sql);
			//$sql = "UPDATE `ex_result` SET `en_time`='$en_time',`ex_heartplus`='$ex_heartplus',`ex_heartplus_max`='$ex_heartplus_max',`ex_calories`='$ex_calories',`ex_calories_max`='$ex_calories_max',`ex_distance`='$ex_distance',`ex_distance_max`='$ex_distance_max',`ex_speed`='$ex_speed',`ex_speed_max`='$ex_speed_max',`ex_incline`='$ex_incline',`ex_incline_max`='$ex_incline_max' WHERE `id` = '$id' and `ex_variety` = '$ex_variety' and `st_time` = '$st_time'";
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
