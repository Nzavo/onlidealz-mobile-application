<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') 
$supplier=$_POST["supplier"];
    $response=mysqli_query($db,"SELECT * from disburse where supplier='$supplier'");
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        while ($row = mysqli_fetch_array($response)) {
            $index['id'] = $row['id'];
            $index['ind'] = $row['ind'];
            $index['mpesa'] = $row['mpesa'];
            $index['supplier'] = $row['supplier'];
            $index['amount'] = $row['amount'];
            $index['reg_date'] = $row['reg_date'];
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No record was Found";
        echo json_encode($results);
    }
    echo json_encode($results);