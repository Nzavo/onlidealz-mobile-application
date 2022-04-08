<?php
include("../conn.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $pay_id = $_POST['pay_id'];
    $sql = "UPDATE payment set shipment='Delivered' where pay_id='$pay_id'";
    if (mysqli_query($db, $sql)) {
        $response["success"] = 1;
        $response["message"] = "Delivery was successful";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to update rcord";
        echo json_encode($response);
        mysqli_close($db);
    }
}