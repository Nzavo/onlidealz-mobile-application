<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $customer_id = $_POST['customer_id'];
    $query = "SELECT * FROM payment where status=1 and customer_id='$customer_id' and (disburse='Assigned' or shipment='Delivered' or delivery='No Action') order by customer asc";
    $response = mysqli_query($db, $query);
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //pay_id,mpesa,shipping,orders,total,quantity,customer_id,phone,location,status,disburse,shipper,shipment,customer,reg_date
        //pay_id,mpesa,shipping,orders,total,quantity,customer_id,phone,location,status,disburse,shipper,shipment,customer,reg_date
        while ($row = mysqli_fetch_array($response)) {
            $index['pay_id'] = $row['pay_id'];
            $index['mpesa'] = $row['mpesa'];
            $index['orders'] = $row['orders'];
            $index['shipping'] = $row['shipping'];
            $index['discount'] = $row['discount'];
            $index['discounted'] = $row['discounted'];
            $index['original'] = $row['original'];
            $index['quantity'] = $row['quantity'];
            $index['customer_id'] = $row['customer_id'];
            $index['phone'] = $row['phone'];
            $index['delivery'] = $row['delivery'];
            $index['location'] = $row['location'];
            $index['address'] = $row['address'];
            if ($row['status'] == 0) {
                $index['status'] = 'Pending';
            } else if ($row['status'] == 1) {
                $index['status'] = 'Approved';
            } elseif ($row['status'] == 2) {
                $index['status'] = 'Rejected';
            }
            $index['disburse'] = $row['disburse'];
            $index['shipper'] = $row['shipper'];
            $index['shipment'] = $row['shipment'];
            if ($row['customer'] == 0) {
                $index['customer'] = 'Pending';
            } else if ($row['customer'] == 1) {
                $index['customer'] = 'Approved';
            } elseif ($row['customer'] == 2) {
                $index['customer'] = 'Rejected';
            }
            if ($row['review'] == 0) {
                $index['review'] = 'Pending';
            } else if ($row['review'] == 1) {
                $index['review'] = 'Approved';
            } elseif ($row['review'] == 2) {
                $index['review'] = 'Rejected';
            }
            $index['reg_date'] = $row['reg_date'];
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No Record Found";
        echo json_encode($results);
    }
    echo json_encode($results);
}