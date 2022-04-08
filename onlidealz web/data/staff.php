<?php
include('rinkles.php');

$sql = "CREATE TABLE staff(
    reg_id VARCHAR(50) PRIMARY KEY,
    fullname TEXT,
    username VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(250),
    password VARCHAR(250),
    role text,
    status int default 0,
    comment text,
    reg_date timestamp default current_timestamp 
    )";
//id,fname,lname,dob,gender,contact,address,email,password,role,status,comment
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);