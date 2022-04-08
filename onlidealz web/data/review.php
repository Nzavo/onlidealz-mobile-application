<?php
include('rinkles.php');

$sql = "CREATE TABLE review(
    reg int auto_increment PRIMARY KEY,
    quality float,
    rate float,
    price float,
    value float,
    reference VARCHAR(50),
    customer VARCHAR(50),
    name VARCHAR(50),
    phone VARCHAR(50),
    status VARCHAR(50) default 'No',
    reg_date timestamp default current_timestamp 
    )";
//reg,quality,rate,price,value,product,customer,category,type,name,phone,status,reg_date
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);