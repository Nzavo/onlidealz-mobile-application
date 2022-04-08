<?php
include('rinkles.php');

$sql = "CREATE TABLE feedback(
    id int auto_increment PRIMARY KEY,
    reg_id int,
    customer varchar(50),
    phone VARCHAR(20),
    fullname varchar(50),
    message VARCHAR(250),
    rate float,
    reg_date timestamp default current_timestamp 
    )";//id,customer,phone,fullname,message,rate,reg_date
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);