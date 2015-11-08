<?php

/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_GET["pid"])) {
    $pid = $_GET['pid'];

    // get a record from patient table
    $result = mysql_query("SELECT hbsab, hbsag, hbeab, hbeag, hbcab FROM patient WHERE pid = $pid");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

		$result = mysql_fetch_array($result);

            $record = array();
            $record["hbsab"] = $result["hbsab"];
			$record["hbsag"] = $result["hbsag"];
			$record["hbeab"] = $result["hbeab"];
			$record["hbeag"] = $result["hbeag"];
			$record["hbcab"] = $result["hbcab"];
            // success
            $response["success"] = 1;

            // user node
            $response["record"] = array();

            array_push($response["record"], $record);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No record found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No record found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>