<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') 
$try=mysqli_query($db,"SELECT distinct supplier_id as sup from purchase where status='Approved'");
$y=mysqli_fetch_assoc($try);
            $huge=mysqli_query($db,"SELECT sum(quantity*price) as tot from purchase where supplier_id='$y[sup]' and status='Approved'");
            $yes=mysqli_fetch_assoc($huge);
            $blog=mysqli_query($db,"SELECT payment as dis from purchase where supplier_id='$y[sup]' and status='Approved'");
            $lg=mysqli_fetch_assoc($blog);
    $response=mysqli_query($db,"SELECT distinct supplier_id from purchase where status='Approved'");
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();//supplier,payment,disburse
        while ($row = mysqli_fetch_array($response)) {
            $index['supplier'] = $row['supplier_id'];
            $index['payment'] = $yes['tot'];
            $index['disburse'] = $lg['dis'];
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No record was Found";
        echo json_encode($results);
    }
    echo json_encode($results);