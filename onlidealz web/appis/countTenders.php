<?php

include("../conn.php");
$cate = $_POST['supplier_id'];
$count = mysqli_query($db, "SELECT count(*) as tends from orders where supplier_id='$cate' and qnty!=0");
$purpose = mysqli_fetch_assoc($count);
$query = "SELECT * FROM orders where supplier_id='$cate' and qnty!=0";

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
    $results['message'] = "No Tender";
    echo json_encode($results);
}
echo json_encode($results);