<?php
include('../conn.php');
$sql = "SELECT * from supplier";
if (!$db->query($sql)) {
    echo "Error in creating connection";
} else {
    $result = $db->query($sql);
    if ($result->num_rows > 0) {
        $return_arr['supplier'] = array();
        while ($row = $result->fetch_array()) {
            array_push($return_arr['supplier'], array(
                'email' => $row['email']
            ));
        }
        echo json_encode($return_arr);
    }
}