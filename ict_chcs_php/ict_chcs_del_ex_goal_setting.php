<?php
	define("HOST","localhost");
	define("USER","ict_chcs");
	define("PASSWD","ict_chcs");
	define("DB_NAME","ict_chcs");
	define("TABLE_NAME","ex_goal_setting");

	$id = $_GET['id']; 
	$all = $_GET['all'];

	//echo "$id, $pw, $name, $gender, $age, $weight";
	$connect = mysql_connect(HOST, USER, PASSWD) or die("Fail to connect to SQL Server");

	mysql_select_db(DB_NAME, $connect);

	if($all) {
		$sql = "DELETE FROM `ex_goal_setting`";
		session_start();

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
	else if($id) {
		$sql = "DELETE FROM `ex_goal_setting` WHERE `id` = '$id'";
		session_start();

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