<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM purchase where status!='Pending' order by purchase_id ASC";

    $response = mysqli_query($db, $query);

    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //counter_id,supplier_id,business_name,category,type,qnty,price,quantity,description,image,status,comment,reg_date
        while ($row = mysqli_fetch_array($response)) {
            $index['purchase_id'] = $row['purchase_id'];
            $index['counter_id'] = $row['counter_id'];
            $index['supplier_id'] = $row['supplier_id'];
            $index['business_name'] = $row['business_name'];
            $index['category'] = $row['category'];
            $index['type'] = $row['type'];
            $index['price'] = $row['price'];
            $index['quantity'] = $row['quantity'];
            $index['charge'] = $row['price'] * $row['quantity'];
            $index['description'] = $row['description'];
            $index['image'] = $row['image'];
            $index['status'] = $row['status'];
            $index['comment'] = $row['comment'];
            $index['reg_date'] = $row['reg_date'];
            $try = mysqli_query($db, "SELECT quantity as qunt from orders where counter_id='$row[counter_id]'");
            $fine = mysqli_fetch_assoc($try);
            $index['system'] = $fine['qunt'];
            array_push($results['victory'], $index);
        }
    } else {

        $results['trust'] = 0;
        $results['mine'] = "No Entry was Found";
        echo json_encode($results);
    }
    echo json_encode($results);
}