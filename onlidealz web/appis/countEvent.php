<?php

include("../conn.php");
$cate = $_POST['email'];
$count = mysqli_query($db, "SELECT count(*) as tends from payment where status=1 and disburse='Assigned' and shipment='Pending' and shipper='$cate'");
$purpose = mysqli_fetch_assoc($count);
$query = "SELECT * FROM payment where status=1 and disburse='Assigned' and shipment='Pending' and shipper='$cate'";

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
    $results['message'] = "No Event";
    echo json_encode($results);
}
echo json_encode($results);