<?php
include("../conn.php");
//orders,shipping,discount,discounted,original
//quantity,order,shipping,total,location,mpesa,customer_id,phone,reg,payment
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $quantity = $_POST['quantity'];
    $orders = $_POST['orders'];
    $shipping = $_POST['shipping'];
    $discount = $_POST['discount'];
    $discounted = $_POST['discounted'];
    $original = $_POST['original'];
    $delivery = $_POST['delivery'];
    $location = $_POST['location'];
    $address = $_POST['address'];
    $mpesa = $_POST['mpesa'];
    $customer_id = $_POST['customer_id'];
    $phone = $_POST['phone'];
    $disburse = $_POST['disburse'];
    $shipper = $_POST['shipper'];
    $shipment = $_POST['shipment'];
    $customer = $_POST['customer'];
    $per = 'EFGHIJKLMNO012ABCDPQRSTUV3456789WXYZ';
    $pay_id = substr(str_shuffle($per), 0, 10);
    $naming = "/^[A-Z0-9]{10,}$/";
    $caps = "/^[A-Z]{10,}$/";
    $nums = "/^[0-9]{10,}$/";
    if (preg_match($caps, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "A valid mpesa cannot have Alphabet characters alone";
        echo json_encode($response);
    } elseif (preg_match($nums, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "A valid mpesa cannot have numerical digits alone";
        echo json_encode($response);
    } else {
        $select = "SELECT mpesa FROM payment WHERE mpesa='$mpesa'";
        $query = mysqli_query($db, $select);
        if (mysqli_num_rows($query) > 0) {
            $response["success"] = 0;
            $response["message"] = "Your mpesa is not Available";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $sql = "INSERT INTO payment(pay_id,mpesa,orders,shipping,discount,discounted,original,quantity,customer_id,phone,delivery,location,address,disburse,shipper,shipment,customer)
    VALUES('$pay_id','$mpesa','$orders','$shipping','$discount','$discounted','$original','$quantity','$customer_id','$phone','$delivery','$location','$address','$disburse','$shipper','$shipment','$customer')";
            if (mysqli_query($db, $sql)) {
                $clear = mysqli_query($db, "UPDATE cart set status='Cleared', serial='$pay_id' where customer='$customer_id' and serial='Pending' and status='Pending'");
                if ($clear) {
                    $response["success"] = 1;
                    $response["message"] = "Payment was successful";
                    echo json_encode($response);
                    mysqli_close($db);
                } else {
                    $response["success"] = 0;
                    $response["message"] = "An error occurred while trying to update cart";
                    echo json_encode($response);
                    mysqli_close($db);
                }
            } else {
                $response["success"] = 0;
                $response["message"] = " Failed to add payament";
                echo json_encode($response);
                mysqli_close($db);
            }
        }
    }
}