<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $cate = $_POST['supplier_id'];

    $query = "SELECT * FROM orders WHERE supplier_id='$cate' and qnty!=0 ORDER BY counter_id ASC";

    $response = mysqli_query($db, $query);

    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //counter_id,supplier_id,business_name,category,type,qnty,get_Order
        while ($row = mysqli_fetch_array($response)) {
            $index['counter_id'] = $row['counter_id'];
            $index['supplier_id'] = $row['supplier_id'];
            $index['business_name'] = $row['business_name'];
            $index['category'] = $row['category'];
            $index['type'] = $row['type'];
            $index['qnty'] = $row['qnty'];
            array_push($results['victory'], $index);
        }
    } else {

        $results['trust'] = 0;
        $results['mine'] = "No Orders were Found";
        echo json_encode($results);
    }
    echo json_encode($results);
}