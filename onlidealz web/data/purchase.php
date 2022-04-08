<?php
include('rinkles.php');

$sql = "CREATE TABLE purchase(
    purchase_id int auto_increment PRIMARY KEY,
    counter_id int,
    supplier_id varchar(50),
    business_name varchar(250),
    category varchar(250),
    type varchar(250),
    price float,
    quantity float,
    description varchar(250),
    image varchar(250),
    status varchar(50) default 'Pending',
    comment varchar(250),
    payment varchar(50) default 'Pending',
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