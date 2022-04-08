<?php
include('rinkles.php');

$sql = "CREATE TABLE driver(
    reg_id VARCHAR(50) PRIMARY KEY,
    license varchar(50),
    fullname TEXT,
    username VARCHAR(50),
    gender text,
    phone VARCHAR(20),
    address VARCHAR(50),
    email VARCHAR(250),
    password VARCHAR(250),
    status int default 0,
    comment text,
    reg_date timestamp default current_timestamp 
    )";
//reg_id,fullname,username,phone,address,email,password,status,comment,reg_date
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);