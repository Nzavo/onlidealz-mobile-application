<?php
include("../conn.php");
//reg,qty,product,quantity
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $product = $_POST['product'];
    $reg = $_POST['reg'];
    $qty = $_POST['qty'];
    $quantity = $_POST['quantity'];
    if($qty>$quantity){
         $response["success"] = 0;
            $response["message"] = "You have entered a quantity more than your cart";
            echo json_encode($response);
            mysqli_close($db);
    }elseif($qty==$quantity){
         $response["success"] = 0;
            $response["message"] = "Your cart cannot reduce to ZERO";
            echo json_encode($response);
            mysqli_close($db);
    }else{
       mysqli_query($db,"UPDATE cart set quantity=(quantity-$qty) where reg='$reg'");
       mysqli_query($db, "UPDATE product set quantity=(quantity+$qty) where product_id='$product'"); 
        $response["success"] = 1;
        $response["message"] = "Product Removed successfully";
        echo json_encode($response);
        mysqli_close($db); 
    }
}