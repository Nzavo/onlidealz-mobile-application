<?php
include("../conn.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $pay_id = $_POST['pay_id'];
    if ($_POST["status"] == 'Approved') {
        $status = 1;
    } elseif ($_POST["status"] == 'Rejected') {
        $status = 2;
    }
    $sql = "UPDATE payment set status='$status' where pay_id='$pay_id'";
    if (mysqli_query($db, $sql)) {
        $response["success"] = 1;
        $response["message"] = "Verifation was successful";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to verify payment";
        echo json_encode($response);
        mysqli_close($db);
    }
}