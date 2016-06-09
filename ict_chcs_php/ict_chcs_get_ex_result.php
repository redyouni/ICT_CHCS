<?php
  define("HOST", "localhost");
  define("USER","ict_chcs");
  define("PASSWD","ict_chcs");
  define("DB_NAME","ict_chcs");
  define("TABLE_NAME","ex_result");

  $id = $_GET['id'];
  $ex_variety = $_GET['ex_variety'];
  $st_time = $_GET['st_time'];
  $st_time2 = $_GET['st_time2'];

  $sum = $_GET['sum'];
  $limit = $_GET['limit'];
  $desc = $_GET['desc'];
  $graph = $_GET['graph'];

  $ex_calories = $_GET['ex_calories'];
  $ex_distance = $_GET['ex_distance'];
  $ex_time = $_GET['ex_time'];
  $ex_heartplus = $_GET['ex_heartplus'];

  // the string connection to  database. (location of Database, username, password)
  $connect=mysql_connect(HOST, USER, PASSWD) or
      die( "Fail to connect to SQL Server");

  mysql_query("SET NAMES UTF8");

  // choose a database
  mysql_select_db(DB_NAME, $connect);

  // Start Session
  session_start();

  //  Create a query sentence.
  if($st_time2) {
    if($sum) {
      if($ex_variety) {
        $sql = "SELECT SUM(`ex_calories`), SUM(`ex_distance`), SUM(`ex_time`) FROM `ex_result` WHERE `id` = '$id' AND `ex_variety` = '$ex_variety' AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2'";
        $name1 = "SUM(`ex_calories`)";
        $name2 = "SUM(`ex_time`)";
        $name3 = "SUM(`ex_distance`)";
      }
      else {
        $sql = "SELECT SUM(`ex_calories`), SUM(`ex_distance`), SUM(`ex_time`) FROM `ex_result` WHERE `id` = '$id' AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2'";
        $name1 = "SUM(`ex_calories`)";
        $name2 = "SUM(`ex_time`)";
        $name3 = "SUM(`ex_distance`)";
      }

      //echo $sql ;
      //echo $name ;

        // store the query execution result into the variable $result.
      $result = mysql_query($sql, $connect);

      //Save the the number of the records returned.
      $total_record = mysql_num_rows($result);

      //echo "total_record = $total_record";
      if(!$total_record)  { 
        echo "{\"status\":\"NOK\"}";
      }
      else  {
        echo "{\"status\":\"OK\",\"num_results\":\"$total_record\",\"results\":[";

        //Make the format of JSONArray from each record returned.
        for ($i=0; $i < $total_record; $i++)  { 

          //move the pointer which will bring the record
          mysql_data_seek($result, $i);

          $row = mysql_fetch_array($result);

          //echo "{\"$name\":\"$row\"}";

          echo "{\"$name1\":\"$row[$name1]\", \"$name2\":\"$row[$name2]\", \"$name3\":\"$row[$name3]\"}";

          // Put .(comma) just before the last record. That's because you can distinguish data. 
          if($i<$total_record-1){
            echo ",";
          }
        }

        echo "]}";
      }
    }
    else if($graph) {
      $sql1 = "SELECT SUM(`ex_calories`), SUM(`ex_heartplus`), SUM(`ex_time`), SUM(`ex_distance`) FROM `ex_result` WHERE `id` = '$id' AND  `ex_variety` = 'TREADMILL'  AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2'";
      $sql2 = "UNION";
      $sql3 = "SELECT SUM(`ex_calories`), SUM(`ex_heartplus`), SUM(`ex_time`), SUM(`ex_distance`) FROM `ex_result` WHERE `id` = '$id' AND `ex_variety` = 'BIKE' AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2'";
      $sql = sprintf("%s %s %s", $sql1, $sql2, $sql3);
      $name1 = "SUM(`ex_calories`)";
      $name2 = "SUM(`ex_heartplus`)";
      $name3 = "SUM(`ex_time`)";
      $name4 = "SUM(`ex_distance`)";


      //echo $sql ;
      //echo $name ;

      // store the query execution result into the variable $result.
      $result = mysql_query($sql, $connect);

      //Save the the number of the records returned.
      $total_record = mysql_num_rows($result);

      //echo "total_record = $total_record";
      if(!$total_record)  { 
        echo "{\"status\":\"NOK\"}";
      }
      else  {
        echo "{\"status\":\"OK\",\"num_results\":\"$total_record\",\"results\":[";

        //Make the format of JSONArray from each record returned.
        for ($i=0; $i < $total_record; $i++)  { 

          //move the pointer which will bring the record
          mysql_data_seek($result, $i);

          $row = mysql_fetch_array($result);

          echo "{\"$name1\":\"$row[$name1]\", \"$name2\":\"$row[$name2]\", \"$name3\":\"$row[$name3]\", \"$name4\":\"$row[$name4]\"}";
          //echo "{\"SUM(`ex_calories`)\":\"$row[SUM(`ex_calories`)]\", \"SUM(`ex_heartplus`)\":\"$row[SUM(`ex_heartplus`)]\", \"SUM(`ex_time`)\":\"$row[SUM(`ex_time`)]\", \"SUM(`ex_distance`)\":\"$row[SUM(`ex_distance`)]\"}";

          // Put .(comma) just before the last record. That's because you can distinguish data. 
          if($i<$total_record-1){
            echo ",";
          }
        }

        echo "]}";
      }
    }
    else {
      if($limit) {
        if($ex_variety) {
          if($desc) {
            $sql = "SELECT * FROM `ex_result` WHERE `id` = '$id' AND `ex_variety` = '$ex_variety' AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2' ORDER BY `st_time` DESC LIMIT $limit";
          }
          else {
            $sql = "SELECT * FROM `ex_result` WHERE `id` = '$id' AND `ex_variety` = '$ex_variety' AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2' ORDER BY `st_time` LIMIT $limit";
          }
        }
        else {
          if($desc) {
            $sql = "SELECT * FROM `ex_result` WHERE `id` = '$id' AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2' ORDER BY `st_time` DESC LIMIT $limit";
          }
          else {
            $sql = "SELECT * FROM `ex_result` WHERE `id` = '$id' AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2' ORDER BY `st_time` LIMIT $limit";
          }
        }
      }
      else {
        if($ex_variety) {
          $sql = "SELECT * FROM `ex_result` WHERE `id` = '$id' AND `ex_variety` = '$ex_variety' AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2'";
        }
        else {
          $sql = "SELECT * FROM `ex_result` WHERE `id` = '$id' AND `st_time` >= '$st_time' AND `st_time` <= '$st_time2'";
        }
      }

      //echo $sql ;

      // store the query execution result into the variable $result.
      $result = mysql_query($sql, $connect);

      //Save the the number of the records returned.
      $total_record = mysql_num_rows($result);

      //echo "total_record = $total_record";
      if(!$total_record)  { 
        echo "{\"status\":\"NOK\"}";
      }
      else  {
        echo "{\"status\":\"OK\",\"num_results\":\"$total_record\",\"results\":[";

        //Make the format of JSONArray from each record returned.
        for ($i=0; $i < $total_record; $i++)  { 

          //move the pointer which will bring the record
          mysql_data_seek($result, $i);

          $row = mysql_fetch_array($result);

          echo "{\"id\":\"$row[id]\", \"ex_variety\":\"$row[ex_variety]\", \"st_time\":\"$row[st_time]\", \"en_time\":\"$row[en_time]\", \"ex_time\":\"$row[ex_time]\", \"ex_heartplus\":\"$row[ex_heartplus]\",
           \"ex_calories\":\"$row[ex_calories]\", \"ex_distance\":\"$row[ex_distance]\", \"ex_speed\":\"$row[ex_speed]\", \"ex_incline\":\"$row[ex_incline]\",  
           \"ex_level\":\"$row[ex_level]\",  \"ex_rpm\":\"$row[ex_rpm]\",  \"ex_watts\":\"$row[ex_watts]\",  \"ex_mets\":\"$row[ex_mets]\"}";

          // Put .(comma) just before the last record. That's because you can distinguish data. 
          if($i<$total_record-1){
            echo ",";
          }
        }

        echo "]}";
      }
    }
  }
  else {

    if($id && $ex_variety && $st_time) {
      $sql = "select * from ex_result where `id` = '$id' and `ex_variety` = '$ex_variety' and `st_time` = '$st_time'";
    }
    else if($id && $st_time) {
      $sql = "select * from ex_result where `id` = '$id' and `st_time` = '$st_time'";
    }
    else if($id) {
      $sql = "select * from ex_result where `id` = '$id'";
    }
    else {
      $sql = "select * from ex_result";
    }

    //echo $sql ;

    // store the query execution result into the variable $result.
    $result = mysql_query($sql, $connect);

    //Save the the number of the records returned.
    $total_record = mysql_num_rows($result);

    //echo "total_record = $total_record";
    if(!$total_record)  {	
      echo "{\"status\":\"NOK\"}";
    }
    else  {
      echo "{\"status\":\"OK\",\"num_results\":\"$total_record\",\"results\":[";

      //Make the format of JSONArray from each record returned.
      for ($i=0; $i < $total_record; $i++)	{	

        //move the pointer which will bring the record
        mysql_data_seek($result, $i);

        $row = mysql_fetch_array($result);

        echo "{\"id\":\"$row[id]\", \"ex_variety\":\"$row[ex_variety]\", \"st_time\":\"$row[st_time]\", \"en_time\":\"$row[en_time]\", \"ex_time\":\"$row[ex_time]\", \"ex_heartplus\":\"$row[ex_heartplus]\",
         \"ex_calories\":\"$row[ex_calories]\", \"ex_distance\":\"$row[ex_distance]\", \"ex_speed\":\"$row[ex_speed]\", \"ex_incline\":\"$row[ex_incline]\",  
         \"ex_level\":\"$row[ex_level]\",  \"ex_rpm\":\"$row[ex_rpm]\",  \"ex_watts\":\"$row[ex_watts]\",  \"ex_mets\":\"$row[ex_mets]\"}";

        // Put .(comma) just before the last record. That's because you can distinguish data. 
        if($i<$total_record-1){
          echo ",";
        }
      }

      echo "]}";
    }
  }

?>
