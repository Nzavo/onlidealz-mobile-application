<?php
include('rinkles.php');

$sql = "CREATE TABLE cart(
    reg int auto_increment PRIMARY KEY,
    serial varchar(50) default 'Pending',
    customer VARCHAR(20),
    product VARCHAR(50),
    details VARCHAR(250),
    price float,
    quantity float,
    image VARCHAR(250),
    status varchar(20) default 'Pending',
    reg_date timestamp default current_timestamp 
    )";
//reg,serial,customer,product,details,price,quantity,image,status,reg_date
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);