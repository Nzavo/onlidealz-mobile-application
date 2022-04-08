<?php
include('rinkles.php');

$sql = "CREATE TABLE admin(
  reg_id int AUTO_INCREMENT PRIMARY KEY,
  username varchar(50) NOT NULL,
  email varchar(50),
  password VARCHAR(50),
  reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)";

$result = mysqli_query($lib, $sql);
if (!$result) {
  die("Connection failed: " . $lib->connect_error);
} else {
  echo "admin table created successfully";
}
mysqli_close($lib);