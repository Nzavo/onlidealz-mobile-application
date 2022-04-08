<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM feedback order by reg_date desc";

    $response = mysqli_query($db, $query);

    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //id,customer,phone,fullname,message,rate,reg_date
        while ($row = mysqli_fetch_array($response)) {
            $index['id'] = $row['id'];
            $index['customer'] = $row['customer'];
            $index['phone'] = $row['phone'];
            $index['fullname'] = $row['fullname'];
            $index['message'] = $row['message'];
            $index['rate'] = $row['rate'];
            $index['reg_date'] = $row['reg_date'];
            array_push($results['victory'], $index);
        }
    } else {

        $results['trust'] = 0;
        $results['mine'] = "No Feedback was found";
        echo json_encode($results);
    }
    echo json_encode($results);
}