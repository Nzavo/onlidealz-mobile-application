<?php
require_once '../conn.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $reply = $_POST["reply"];
    $id = $_POST["id"];
    $sql = "UPDATE chat set reply='$reply' where id='$id'";
    if (mysqli_query($db, $sql)) {
        $response["success"] = 1;
        $response["message"] = "Reply was successful";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "  Failed to send reply";
        echo json_encode($response);
        mysqli_close($db);
    }
}