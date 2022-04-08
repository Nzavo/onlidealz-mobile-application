<?php
include("../conn.php");
$username = $_POST['username'];
$password = md5($_POST['password']);
$sql = "SELECT * FROM staff WHERE username='$username' and role='Finance Manager'";
$response = mysqli_query($db, $sql);
if (mysqli_num_rows($response) === 1) {
    mysqli_query($db, "UPDATE staff set password='$password' where username='$username' and role='Finance Manager'");
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