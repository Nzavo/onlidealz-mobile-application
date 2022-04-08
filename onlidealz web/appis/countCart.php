<?php

include("../conn.php");
$cate = $_POST['customer_id'];
$count = mysqli_query($db, "SELECT count(*) as tends from cart where customer='$cate' and serial='Pending' and status='Pending'");
$purpose = mysqli_fetch_assoc($count);
$query = "SELECT * FROM cart where customer='$cate' and serial='Pending' and status='Pending'";

$response = mysqli_query($db, $query);

if (mysqli_num_rows($response) > 0) {
    $results['trust'] = 1;
    $results['victory'] = array();
    while ($row = mysqli_fetch_array($response)) {
        $index['counter'] = $purpose['tends'];
        array_push($results['victory'], $index);
    }
} else {

    $results['trust'] = 0;
    $results['message'] = "No Item";
    echo json_encode($results);
}
echo json_encode($results);