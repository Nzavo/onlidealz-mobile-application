<?php
include('rinkles.php');

$sql = "CREATE TABLE disburse(
    id int auto_increment PRIMARY KEY,
    ind varchar(20),
    mpesa varchar(20),
    supplier varchar(50),
    amount float,
    reg_date timestamp default current_timestamp 
    )";
//id,ind,bank,account,cheque,supplier,amount,reg_date//disburse
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);