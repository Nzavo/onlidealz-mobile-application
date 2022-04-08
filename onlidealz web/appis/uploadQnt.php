<?php
include("../conn.php");
//quant,counter_id,quantity,price,description,image
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $quant = $_POST['quant'];
    $counter_id = $_POST['counter_id'];
    $quantity = $_POST['quantity'];
    $price = $_POST['price'];
    $description = $_POST['description'];
    $supplier_id = $_POST['supplier_id'];
    $category = $_POST['category'];
    $business_name = $_POST['business_name'];
    $type = $_POST['type'];
    $image = $_POST['image'];
    $filename = "IMG" . rand() . ".PNG";
    if ($quantity ==0) {
        $response["success"] = 9;
        $response["message"] = "How can you upload (" . $quantity . ")";
        echo json_encode($response);
    } elseif ($quantity > $quant) {
        $response["success"] = 9;
        $response["message"] = "Not more than (" . $quant . ")";
        echo json_encode($response);
    } elseif ($price ==0) {
        $response["success"] = 8;
        $response["message"] = "The unit price for (" . $quantity . ") units cannot be ZERO";
        echo json_encode($response);
    } else {
        $sql = "INSERT INTO purchase(counter_id,supplier_id,business_name,category,type,price,quantity,description,image)
        VALUES('$counter_id','$supplier_id','$business_name','$category','$type','$price','$quantity','$description','$filename')";
        if (mysqli_query($db, $sql)) {
            mysqli_query($db, "UPDATE orders set qnty=(qnty-$quantity) where counter_id='$counter_id'");
            file_put_contents("../images/" . $filename, base64_decode($image));
            $response["success"] = 1;
            $response["message"] = "Your upload was successful";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $response["success"] = 0;
            $response["message"] = " Failed to failed to submit";
            echo json_encode($response);
            mysqli_close($db);
        }
    }
}