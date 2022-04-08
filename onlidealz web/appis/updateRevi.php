<?php
include("../conn.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $reg = $_POST['reg'];
    $status = $_POST['status'];
                    $dm = mysqli_query($db, "UPDATE review set status='$status' where reg='$reg'");
                    if ($dm) {
                        $response["success"] = 1;
                        $response["message"] = "Update was successful";
                        echo json_encode($response);
                        mysqli_close($db);
                    } else {
                        $response["success"] = 0;
                        $response["message"] = "Operation Failed";
                        echo json_encode($response);
                        mysqli_close($db);
                    }
}