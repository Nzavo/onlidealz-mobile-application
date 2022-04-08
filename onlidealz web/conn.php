<?php
$router = "localhost";
$user = "id18696214_madadi";
$pass = "Sultan0110011410.";
$database = "id18696214_onlidealz";
$db = mysqli_connect($router, $user, $pass, $database);
if (!$db) {
    die("Connection failed: " . mysqli_connect_error());
}