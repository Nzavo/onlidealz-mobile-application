<?php
include('../conn.php');
$sql = "SELECT * from driver";
if (!$db->query($sql)) {
    echo "Error in creating connection";
} else {
    $result = $db->query($sql);
    if ($result->num_rows > 0) {
        $return_arr['driver'] = array();
        while ($row = $result->fetch_array()) {
            array_push($return_arr['driver'], array(
                'email' => $row['email']
            ));
        }
        echo json_encode($return_arr);
    }
}