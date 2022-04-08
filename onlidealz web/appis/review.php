<?php
include("../conn.php");
//quality,rate,price,value,product,customer//category,type,name,phone
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $quality = $_POST['quality'];
    $rate = $_POST['rate'];
    $price = $_POST['price'];
    $value = $_POST['value'];
    $reference = $_POST['reference'];
    $customer = $_POST['customer'];
    $name = $_POST['name'];
    $phone = $_POST['phone'];
    $sql = "UPDATE payment set review='1' where pay_id='$reference'";
    if (mysqli_query($db, $sql)) {
    $sql = "INSERT INTO review(quality,rate,price,value,reference,customer,name,phone)
    VALUES('$quality','$rate','$price','$value','$reference','$customer','$name','$phone')";
    if (mysqli_query($db, $sql)) {
        $response["success"] = 1;
        $response["message"] = "Review pubished successfully";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to publish review";
        echo json_encode($response);
        mysqli_close($db);
    }
}
}