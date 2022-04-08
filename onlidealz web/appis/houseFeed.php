<?php
require_once '../conn.php';



$sender=$_POST['sender'];



$sql = "SELECT  * FROM chat WHERE sender='$sender' ";

$response = mysqli_query($db,$sql);

if(mysqli_num_rows($response)>0) {
     $results['status'] = 1;
    $results['getfeedback'] = array();
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

        array_push($results['getfeedback'], $index);
    }
}else{

    $results['status'] = "0";
    $results['message']="Type Text Some Message";
    echo json_encode($results);
}
echo json_encode($results);


?>