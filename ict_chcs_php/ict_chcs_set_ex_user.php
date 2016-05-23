<?php
	define("HOST","localhost");
	define("USER","ict_chcs");
	define("PASSWD","ict_chcs");
	define("DB_NAME","ict_chcs");
	define("TABLE_NAME","ex_user");

	$id = $_GET['id']; 
	$password = $_GET['password'];
	$name = $_GET['name'];
	$gender =  $_GET['gender'];
	$age = $_GET['age'];
	$weight = $_GET['weight'];
	$rfid = $_GET['rfid'];

	//echo "$id, $pw, $name, $gender, $age, $weight";
	$connect = mysql_connect(HOST, USER, PASSWD) or die("Fail to connect to SQL Server");

	mysql_select_db(DB_NAME, $connect);

	if($id) {
		$sql = "select * from ex_user where `id` = '$id'";
		
		session_start();

		// store the query execution result into the variable $result.
		$result = mysql_query($sql, $connect);

		//Save the the number of the records returned.
		$total_record = mysql_num_rows($result);

		if(!$total_record)  {	
			//insert
			$sql = "INSERT INTO `ex_user`(`id`, `password`, `name`, `gender`, `age`, `weight`, `rfid`) VALUES('$id', '$password', '$name', '$gender', '$age', '$weight', '$rfid')";
		}
		else  {
			//update
			$sql = "UPDATE `ex_user` SET ";
			if($password) $sql .= ",`password`='$password'";
			if($name) $sql .= ",`name`='$name'";
			if($gender) $sql .= ",`gender`='$gender'";
			if($age) $sql .= ",`age`='$age'";
			if($weight) $sql .= ",`weight`='$weight'";
			if($rfid) $sql .= ",`rfid`='$rfid'";
			
			$sql .= " WHERE `id` = '$id'";
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
