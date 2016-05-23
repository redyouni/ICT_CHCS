<?php
  define("HOST", "localhost");
  define("USER","ict_chcs");
  define("PASSWD","ict_chcs");
  define("DB_NAME","ict_chcs");
  define("TABLE_NAME","ex_user");

  $connect=mysql_connect(HOST, USER, PASSWD) or
  die( "Fail to connect to SQL Server");

  mysql_query("SET NAMES UTF8");
  // choose a database
  mysql_select_db(DB_NAME, $connect);

  // Start Session
  session_start();

  $sql = "select * from ex_user";
  $result = mysql_query($sql, $connect);

  //Save the the number of the records returned.
  $total_record = mysql_num_rows($result);

  // In order to make the JSONArray format.....
  if(!$result)  {	
    echo "{\"status\":\"NOK\"}";
  }
  else  {
    echo "{\"status\":\"OK\"}";
  }

?>
