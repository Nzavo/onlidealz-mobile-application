<?php
include("../conn.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $pay_id = $_POST['pay_id'];
    $shipper = $_POST['shipper'];
    $sql = "UPDATE payment set disburse='Assigned',shipper='$shipper' where pay_id='$pay_id'";
    if (mysqli_query($db, $sql)) {
        $response["success"] = 1;
        $response["message"] = "Assignment was successful";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to assign Driver";
        echo json_encode($response);
        mysqli_close($db);
    }
}