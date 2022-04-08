<?php
include("../conn.php");
$username = $_POST['username'];
$password = md5($_POST['password']);
$sql = "SELECT * FROM customer WHERE username='$username'";
$response = mysqli_query($db, $sql);
if (mysqli_num_rows($response) === 1) {
    mysqli_query($db, "UPDATE customer set password='$password' where username='$username'");
    $row = mysqli_fetch_assoc($response);
    $result['success'] = 1;
    $result['message'] =  "Operation was successful";
    echo json_encode($result);
    mysqli_close($db);
} else {
    $result['success'] = 0;
    $result['message'] = "Your account was not found";
    echo json_encode($result);
    mysqli_close($db);
}