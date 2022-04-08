<?php
include("../conn.php");

//counter_id,supplier_id,business_name,category,type,qnty,price,quantity,description,image,status,reg_date

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $supplier_id = $_POST['supplier_id'];
    $business_name = $_POST['business_name'];
    $category = $_POST['category'];
    $type = $_POST['type'];
    $qnty = $_POST['qnty'];
    $sql = "INSERT INTO orders(supplier_id,business_name,category,type,quantity,qnty)
    VALUES('$supplier_id','$business_name','$category','$type','$qnty','$qnty')";
    if (mysqli_query($db, $sql)) {
        $response["success"] = 1;
        $response["message"] = "Purchase Order placed successfully";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = " Failed to failed to submit";
        echo json_encode($response);
        mysqli_close($db);
    }
}