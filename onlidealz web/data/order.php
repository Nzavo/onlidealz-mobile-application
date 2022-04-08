<?php
include('rinkles.php');

$sql = "CREATE TABLE orders(
    counter_id int auto_increment PRIMARY KEY,
    supplier_id varchar(50),
    business_name varchar(250),
    category varchar(250),
    type varchar(250), 
    quantity float,
    qnty float,
    reg_date timestamp default current_timestamp 
    )";
//counter_id,supplier_id,business_name,category,type,qnty,price,quantity,description,image,status,reg_date
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);