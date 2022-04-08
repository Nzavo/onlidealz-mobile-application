<?php
$servername = "localhost";
$username = "id18696214_madadi";
$password = "Sultan0110011410.";
$dbname = "id18696214_onlidealz";
$lib = new mysqli($servername, $username, $password, $dbname);
if ($lib->connect_error) {
    die("Connection failed: " . $lib->connect_error);
}