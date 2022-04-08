<?php
include("../conn.php");
//reg,product,quantity
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $product = $_POST['product'];
    $reg = $_POST['reg'];
    $quantity = $_POST['quantity'];
    $sql = "DELETE from cart where reg='$reg'";
    if (mysqli_query($db, $sql)) {
        $nes = mysqli_query($db, "UPDATE product set quantity=(quantity+$quantity) where product_id='$product'");
        if ($nes) {
            $response["success"] = 1;
            $response["message"] = "Product Removed successfully";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $response["success"] = 0;
            $response["message"] = "An error occured";
            echo json_encode($response);
            mysqli_close($db);
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to remove cart";
        echo json_encode($response);
        mysqli_close($db);
    }
}