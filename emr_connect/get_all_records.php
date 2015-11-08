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
if (isset($_GET["email"])) {
    $email = $_GET['email'];

    // get a product from products table
    $result = mysql_query("SELECT pid, liver, blood, urine FROM patient WHERE email = '$email'");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
		 $response["records"] = array();

           while ($row = mysql_fetch_array($result)) {
        // temp user array
        $record = array();
		$record["pid"]	 = $row["pid"];
        $record["liver"] = $row["liver"];
        $record["blood"] = $row["blood"];
        $record["urine"] = $row["urine"];
        



        // push single product into final response array
        array_push($response["records"], $record);
    }
	// success
    $response["success"] = 1;

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