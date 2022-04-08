<?php
include('rinkles.php');

$sql = "CREATE TABLE payment(
    pay_id varchar(50) PRIMARY KEY,
    mpesa VARCHAR(20),
    shipping float,
    orders float,
    discount float,
    original float,
    discounted float,
    quantity float,
    customer_id varchar(50),
    phone varchar(50),
    delivery varchar(50),
    location varchar(50),
    address varchar(250),
    status float default 0,
    disburse varchar(50) default 0,
    shipper varchar(50) default 0,
    shipment varchar(50) default 0,
    customer varchar(50) default 0,
    review varchar(50) default 0,
    reg_date timestamp default current_timestamp 
    )";
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);