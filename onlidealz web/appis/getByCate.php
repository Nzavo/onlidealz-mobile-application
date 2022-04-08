<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $cate = $_POST['category'];

    $query = "SELECT * FROM product WHERE category='$cate' and quantity!=0 ORDER BY product_id ASC";

    $response = mysqli_query($db, $query);

    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //product_id,category,type,description,image,quantity,price,stock
        while ($row = mysqli_fetch_array($response)) {
            $index['product_id'] = $row['product_id'];
            $index['category'] = $row['category'];
            $index['type'] = $row['type'];
            $index['description'] = $row['description'];
            $index['image'] = $row['image'];
            $index['quantity'] = $row['quantity'];
            $index['price'] = $row['price'];
            if ($row['quantity'] == 0) {
                $index['stock'] = 'Stock-out';
            } else {
                $index['stock'] = 'Stock-in';
            }
            array_push($results['victory'], $index);
        }
    } else {

        $results['trust'] = 0;
        $results['mine'] = "Category not Found";
        echo json_encode($results);
    }
    echo json_encode($results);
}