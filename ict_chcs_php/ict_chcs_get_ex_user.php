<?php
  define("HOST", "localhost");
  define("USER","ict_chcs");
  define("PASSWD","ict_chcs");
  define("DB_NAME","ict_chcs");
  define("TABLE_NAME","ex_user");

  $id = $_GET['id'];
  $password = $_GET['password'];
  $rfid = $_GET['rfid'];

  // the string connection to  database. (location of Database, username, password)
  $connect=mysql_connect(HOST, USER, PASSWD) or
  die( "Fail to connect to SQL Server");

  mysql_query("SET NAMES UTF8");
  // choose a database
  mysql_select_db(DB_NAME, $connect);

  // Start Session
  session_start();

  //echo "\"$id\" :: \"$password\"";
  //  Create a query sentence.
  if($id && $password) {
    $sql = "select * from ex_user where `id` = '$id' and `password` = '$password'";
  }
  else if($rfid) {
    $sql = "select * from ex_user where `rfid` = '$rfid'";
  }
  else {
    $sql = "select * from ex_user";
  }

  //echo "\"$sql\"";
  // store the query execution result into the variable $result.
  $result = mysql_query($sql, $connect);

  //Save the the number of the records returned.
  $total_record = mysql_num_rows($result);

  // In order to make the JSONArray format.....
  if(!$total_record)  {	
    echo "{\"status\":\"NOK\"}";
  }
  else  {
    echo "{\"status\":\"OK\",\"num_results\":\"$total_record\",\"results\":[";

    //Make the format of JSONArray from each record returned.
    for ($i=0; $i < $total_record; $i++) {	

      //move the pointer which will bring the record
      mysql_data_seek($result, $i);

      $row = mysql_fetch_array($result);
      echo "{\"id\":\"$row[id]\", \"password\":\"$row[password]\", \"name\":\"$row[name]\", \"gender\":\"$row[gender]\", \"age\":\"$row[age]\", \"weight\":\"$row[weight]\", \"rfid\":\"$row[rfid]\"}";

      // Put .(comma) just before the last record. That's because you can distinguish data. 
      if($i < $total_record-1) {
        echo ",";
      }
    }

    echo "]}";
  }

?>
