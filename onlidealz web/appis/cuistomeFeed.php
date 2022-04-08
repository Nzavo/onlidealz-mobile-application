<?php
require_once '../conn.php';
//reg_id,customer,phone,fullname,message,rate
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $reg_id = $_POST["reg_id"];
    $customer=$_POST["customer"];
    $phone = $_POST["phone"];
    $fullname = $_POST["fullname"];
    $message = $_POST["message"];
    $rate = $_POST["rate"];
    $sql = "UPDATE payment set customer='1' where pay_id='$reg_id'";
    if (mysqli_query($db, $sql)) {
        mysqli_query($db,"INSERT into feedback(reg_id,customer,phone,fullname,message,rate)
        values('$reg_id','$customer','$phone','$fullname','$message','$rate')");
        $response["success"] = 1;
        $response["message"] = "Feedback sent successfully";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Could not send feedback";
        echo json_encode($response);
        mysqli_close($db);
    }
}