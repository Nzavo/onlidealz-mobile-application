<?php

require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM chat where receiver='Finance' order by send_date desc";

    $response = mysqli_query($db, $query);

    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //id,rate,message,receiver,sender,name,phone,post,send_date,reply,reply_date
        while ($row = mysqli_fetch_array($response)) {
            $index['id'] = $row['id'];
            $index['rate'] = $row['rate'];
            $index['message'] = $row['message'];
            $index['receiver'] = $row['receiver'];
            $index['sender'] = $row['sender'];
            $index['name'] = $row['name'];
            $index['phone'] = $row['phone'];
            $index['post'] = $row['post'];
            $index['send_date'] = $row['send_date'];
            $index['reply'] = $row['reply'];
            $index['reply_date'] = $row['reply_date'];
            array_push($results['victory'], $index);
        }
    } else {

        $results['trust'] = 0;
        $results['mine'] = "No Message was found";
        echo json_encode($results);
    }
    echo json_encode($results);
}