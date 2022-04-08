<?php
$host = "localhost";
$username = "root";
$password = "";

$db = new mysqli($host, $username, $password);
if ($db->connect_error) {
  die("Connection failed");
}

$sql = 'CREATE Database onlidealz';
if ($db->query($sql) == TRUE) {
  echo "Database created";
} else {
  echo "Error occurred:" . $db->error;
}

$db->close();