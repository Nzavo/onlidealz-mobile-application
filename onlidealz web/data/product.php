<?php
include('rinkles.php');

$sql = "CREATE TABLE product(
    product_id VARCHAR(50) PRIMARY KEY,
    category varchar(250),
    type varchar(250),
    description VARCHAR(250),
    image varchar(250),
    quantity float,
    price float,
    reg_date timestamp default current_timestamp 
    )";
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);