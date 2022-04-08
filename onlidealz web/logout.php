<?php
session_start();
unset($_SESSION['user_id']);
$url = 'login';
header('Location: ' . $url);