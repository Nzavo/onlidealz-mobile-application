<?php
require_once '../conn.php';
$supplier = $_POST['supplier'];
$find = mysqli_query($db, "SELECT SUM(quantity*price) as pesa from purchase where supplier_id='$supplier' and status='Approved'");
$dotUs = mysqli_fetch_assoc($find);
$query = "SELECT * FROM purchase WHERE supplier_id='$supplier' and status='Approved'";
$response = mysqli_query($db, $query);
if (mysqli_num_rows($response) > 0) {
    $results['trust'] = 1;
    $results['victory'] = array();
    while ($row = mysqli_fetch_array($response)) {
        $index['product'] = $row['category'];
        $index['quantity'] = $row['quantity'];
        $index['price'] = $row['price'];
        $index['supply'] = $row['quantity'] * $row['price'];
        $index['all_pay'] = $dotUs['pesa'];
        array_push($results['victory'], $index);
    }
} else {
    $results['trust'] = 0;
    $results['mine'] = "No Record Found";
    echo json_encode($results);
}
echo json_encode($results);