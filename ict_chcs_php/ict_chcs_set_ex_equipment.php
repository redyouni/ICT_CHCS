<?php
	define("HOST","localhost");
	define("USER","ict_chcs");
	define("PASSWD","ict_chcs");
	define("DB_NAME","ict_chcs");
	define("TABLE_NAME","ex_equipment");

	$id = $_GET['id'];
	$name = $_GET['name'];
	$in_date = $_GET['in_date'];
	
	$connect = mysql_connect(HOST, USER, PASSWD) or die("Fail to connect to SQL Server");

	mysql_select_db(DB_NAME, $connect);

	if($name) {
		$sql = "select * from ex_equipment where `name` = '$name'";
		
		session_start();

		// store the query execution result into the variable $result.
		$result = mysql_query($sql, $connect);

		//Save the the number of the records returned.
		$total_record = mysql_num_rows($result);

		if(!$total_record)  {	
			//insert
			$sql = "INSERT INTO `ex_equipment`(`id`, `name`, `in_date`) VALUES(NULL, '$name', '$in_date')";
		}
		else  {
			//update
			$sql = "UPDATE `ex_equipment` SET ";
			if($in_date) $sql .= ",`in_date`='$in_date'";
			
			$sql .= " WHERE `name` = '$name'";
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