<?php
include("../conn.php");
//counter_id,category,type,sup_price,price,quantity,description,image,comment,status
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $purchase_id = $_POST['purchase_id'];
    $counter_id = $_POST['counter_id'];
    $category = $_POST['category'];
    $type = $_POST['type'];
    $sup_price = $_POST['sup_price'];
    $price = $_POST['price'];
    $quantity = $_POST['quantity'];
    $comment = $_POST['comment'];
    $status = $_POST['status'];
    $description = $_POST['description'];
    if ($status == 'Rejected') {
        $sql = "UPDATE purchase set status='$status', comment='$comment' where purchase_id='$purchase_id'";
        if (mysqli_query($db, $sql)) {
            mysqli_query($db, "UPDATE orders set qnty=(qnty+$quantity) where counter_id='$counter_id'");
            $response["success"] = 1;
            $response["message"] = "Product Rejected successfully";
            echo json_encode($response);
            mysqli_close($db);
        }
    } else {
        if ($price < $sup_price) {
            $response["success"] = 9;
            $response["message"] = "Why Less than the Cost price";
            echo json_encode($response);
        } else {
            $image = mysqli_query($db, "SELECT image as img from purchase where purchase_id='$purchase_id'");
            $photo = mysqli_fetch_assoc($image);
            $sql = "UPDATE purchase set status='$status', comment='$comment' where purchase_id='$purchase_id'";
            if (mysqli_query($db, $sql)) {
                $getter = mysqli_query($db, "SELECT * from product where category='$category' and price='$price' and type='$type' and description='$description'");
                if (mysqli_num_rows($getter) > 0) {
                    $dm = mysqli_query($db, "UPDATE product set quantity='$quantity', image='$photo[img]' where category='$category' and price='$price' and type='$type' and description='$description'");
                    if ($dm) {
                        $response["success"] = 1;
                        $response["message"] = "An existing product was updated successfully";
                        echo json_encode($response);
                        mysqli_close($db);
                    } else {
                        $response["success"] = 0;
                        $response["message"] = "Failed to update an existing product";
                        echo json_encode($response);
                        mysqli_close($db);
                    }
                } else {
                    $count_my_page = ("serial.txt");
                    $hits = file($count_my_page);
                    $hits[0]++;
                    $fp = fopen($count_my_page, "w");
                    fputs($fp, "$hits[0]");
                    fclose($fp);
                    $values = $hits[0];
                    $per = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
                    $newS = substr(str_shuffle($per), 0, 5);
                    $id = $values . $newS;
                    $fiver = mysqli_query($db, "INSERT into product(product_id,category,type,description,image,quantity,price)values('$id','$category','$type','$description','$photo[img]','$quantity','$price')");
                    if ($fiver) {
                        $response["success"] = 1;
                        $response["message"] = "New product inserted successfully";
                        echo json_encode($response);
                        mysqli_close($db);
                    } else {
                        $response["success"] = 0;
                        $response["message"] = "Failed to insert a new Product";
                        echo json_encode($response);
                        mysqli_close($db);
                    }
                }
            } else {
                $response["success"] = 0;
                $response["message"] = " An Error occured with the root connection";
                echo json_encode($response);
                mysqli_close($db);
            }
        }
    }
}