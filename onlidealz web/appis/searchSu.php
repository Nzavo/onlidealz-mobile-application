<?php
require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $email = $_POST['email'];
    $sql = "SELECT * FROM supplier WHERE email='$email'";
    $response = mysqli_query($db, $sql);
    if (mysqli_num_rows($response) === 1) {
        $row = mysqli_fetch_array($response);
        $result = array();
        $result['login'] = array();
        $index['reg_id'] = $row['reg_id'];
        $index['fullname'] = $row['fullname'];
        $index['phone'] = $row['phone'];
        $index['business_name'] = $row['business_name'];
        $index['existence'] = $row['existence'];
        array_push($result['login'], $index);
        $result['success'] = "5";
        $result['message'] = "User Found";
        echo json_encode($result);
        mysqli_close($db);
    }
}