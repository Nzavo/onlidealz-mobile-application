<?php
require_once '../conn.php';
$quant=mysqli_query($db,"SELECT SUM(orders) as ingredients from payment where status=1");
$ingre=mysqli_fetch_assoc($quant);
$qunt=mysqli_query($db,"SELECT SUM(amount) as supplier from disburse");
$ordersCu=mysqli_fetch_assoc($qunt);
$results['trust'] = 1;
$results['victory'] = array();
        $index['timer']=date('Y-m-d');
        $index['summedOrder']=$ingre['ingredients'];
        $index['summedSupply']=$ordersCu['supplier'];
        $index['profit']=$ingre['ingredients']-$ordersCu['supplier'];
        array_push($results['victory'], $index);
        echo json_encode($results);

?>