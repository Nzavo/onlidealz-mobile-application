<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $identity = $_POST['identity'];
    $deal = mysqli_query($db, "SELECT quantity*price as totalShip from cart where serial='$identity'");
    $total = mysqli_fetch_assoc($deal);
    $system = mysqli_query($db, "SELECT quantity as totalShip from cart where serial='$identity'");
    $totQuant = mysqli_fetch_assoc($system);
    $query = "SELECT * FROM cart WHERE serial='$identity'";

    $response = mysqli_query($db, $query);

    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //reg,serial,customer,product,price,quantity,image,status,reg_date
        while ($row = mysqli_fetch_array($response)) {
            $index['reg'] = $row['reg'];
            $index['customer'] = $row['customer'];
            $index['product'] = $row['product'];
            $index['details'] = $row['details'];
            $index['price'] = $row['price'];
            $index['quantity'] = $row['quantity'];
            $index['cost'] = $row['price'] * $row['quantity'];
            $index['image'] = $row['image'];
            $index['status'] = $row['status'];
            $index['reg_date'] = $row['reg_date'];
            $index['total'] = $total['totalShip'];
            $index['sum_quant'] = $totQuant['totalShip'];
            array_push($results['victory'], $index);
        }
    } else {

        $results['trust'] = 0;
        $results['mine'] = "No Item was Found";
        echo json_encode($results);
    }
    echo json_encode($results);
}