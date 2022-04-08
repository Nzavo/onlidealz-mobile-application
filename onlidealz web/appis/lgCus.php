<?php
require_once '../conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = $_POST['username'];
    $password = md5($_POST['password']);
    $sql = "SELECT * FROM customer WHERE username='$username' AND password='$password' ";
    $response = mysqli_query($db, $sql);
    if (mysqli_num_rows($response) === 1) {
        $row = mysqli_fetch_array($response);
        if ($row['status'] == 0) {
            $result['success'] = "0";
            $result['message'] = "Please wait for approval";
            echo json_encode($result);
        } elseif ($row['status'] == 2) {
            $result = array();
            $result['login'] = array();
            $index['comment'] = $row['comment'];
            $result['success'] = "2";
            $result['message'] = "Access Denied!!";
            array_push($result['login'], $index);
            echo json_encode($result);
        } else {
            $result = array(); //reg_id,fullname,username,mobile,address,email,approval,reg_date
            $result['login'] = array();
            $index['reg_id'] = $row['reg_id'];
            $index['fullname'] = $row['fullname'];
            $index['username'] = $row['username'];
            $index['mobile'] = $row['phone'];
            $index['address'] = $row['address'];
            $index['email'] = $row['email'];
            if ($row['status'] == 1) {
                $index['approval'] = 'Approved';
            }
            $index['reg_date'] = $row['reg_date'];
            array_push($result['login'], $index);
            $result['success'] = "1";
            $result['message'] = "Login was successful";
            echo json_encode($result);
            mysqli_close($db);
        }
    } else {
        $result['success'] = "0";
        $result['message'] = "Your credentials are invalid";
        echo json_encode($result);
        mysqli_close($db);
    }
}