<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM review order by reg_date desc";

    $response = mysqli_query($db, $query);

    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //reg,quality,rate,price,value,product,customer,category,type,name,phone,status,reg_date,seeView
        while ($row = mysqli_fetch_array($response)) {
            $index['reg'] = $row['reg'];
            $index['quality'] = $row['quality'];
            $index['rate'] = $row['rate'];
            $index['price'] = $row['price'];
            $index['value'] = $row['value'];
            $index['reference'] = $row['reference'];
            $index['customer'] = $row['customer'];
            $index['name'] = $row['name'];
            $index['phone'] = $row['phone'];
            $index['status'] = $row['status'];
            $index['reg_date'] = $row['reg_date'];
            array_push($results['victory'], $index);
        }
    } else {

        $results['trust'] = 0;
        $results['mine'] = "No review was found";
        echo json_encode($results);
    }
    echo json_encode($results);
}