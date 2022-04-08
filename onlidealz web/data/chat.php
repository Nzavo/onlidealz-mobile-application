<?php
include('rinkles.php');

$sql = "CREATE TABLE chat(
    id int auto_increment PRIMARY KEY,
    rate float,
    message varchar(250),
    receiver VARCHAR(20),
    sender VARCHAR(20),
    name varchar(50),
    phone VARCHAR(20),
    post VARCHAR(20),
    send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reply varchar(250) default 'Reply Pending',
    reply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    )";//id,rate,message,receiver,sender,name,phone,post,send_date,reply,reply_date//chat
$result = mysqli_query($lib, $sql);
if (!$result) {
    die("Connection failed: " . $lib->connect_error);
} else {
    echo "table created";
}
mysqli_close($lib);

  
  