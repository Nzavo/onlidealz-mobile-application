<?php
include("../conn.php");
//reg,serial,customer,product,price,quantity,quant,image,status,reg_date
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $product = $_POST['product'];
    $customer = $_POST['customer'];
    $price = $_POST['price'];
    $quantity = $_POST['quantity'];
    $details = $_POST['details'];
    $quant = $_POST['quant'];
    if ($quantity > $quant) {
        $response["success"] = 9;
        $response["message"] = "Your Order Quantity is High";
        echo json_encode($response);
    } else {
        $query = mysqli_query($db,"SELECT * FROM cart WHERE customer='$customer' and product='$product' and details='$details' and price='$price' and status='Pending'");
            if (mysqli_num_rows($query) > 0) {
                mysqli_query($db,"UPDATE cart set quantity=(quantity+$quantity) where customer='$customer' and product='$product' and details='$details' and price='$price' and status='Pending'");
                mysqli_query($db, "UPDATE product set quantity=(quantity-$quantity) where product_id='$product'");
                 $response["success"] = 1;
                $response["message"] = "Cart Edited successfully";
                echo json_encode($response);
                mysqli_close($db);
            } else {
        $image = mysqli_query($db, "SELECT image as img from product where product_id='$product'");
        $photo = mysqli_fetch_assoc($image);
        $sql = "INSERT INTO cart(customer,product,details,price,quantity,image)
    VALUES('$customer','$product','$details','$price','$quantity','$photo[img]')";
        if (mysqli_query($db, $sql)) {
            $nes = mysqli_query($db, "UPDATE product set quantity=(quantity-$quantity) where product_id='$product'");
            if ($nes) {
                $response["success"] = 1;
                $response["message"] = "Cart Added successfully";
                echo json_encode($response);
                mysqli_close($db);
            } else {
                $response["success"] = 0;
                $response["message"] = "An Error Occured while updating records";
                echo json_encode($response);
                mysqli_close($db);
            }
        } else {
            $response["success"] = 0;
            $response["message"] = "Failed to Add cart";
            echo json_encode($response);
            mysqli_close($db);
        }
    }
}
}