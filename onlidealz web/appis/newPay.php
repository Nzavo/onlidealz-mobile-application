<?php
require_once '../conn.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
     //id,ind,bank,account,cheque,supplier,amount,reg_date//disburse
    $supplier = $_POST["supplier"];
    $mpesa = $_POST["mpesa"];
    $amount = $_POST["amount"];
    $per = '4DEFGHIJKL56789ABCMNWXYZOPQR0123STUV';
    $ind = substr(str_shuffle($per), 0, 10);
    $naming = "/^[A-Z0-9]{10,}$/";
    $caps = "/^[A-Z]{10,}$/";
    $nums = "/^[0-9]{10,}$/";
    if (preg_match($caps, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "A valid mpesa cannot have Alphabet characters alone";
        echo json_encode($response);
    } elseif (preg_match($nums, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "A valid mpesa cannot have numerical digits alone";
        echo json_encode($response);
    } else {
    $select = "SELECT mpesa FROM disburse WHERE mpesa='$mpesa'";
    $query = mysqli_query($db, $select);
    if (mysqli_num_rows($query) > 0) {
        $response["success"] = 0;
        $response["message"] = "MPESA not Accepted";
        echo json_encode($response);
        mysqli_close($db);
    } else {
            $insert = mysqli_query($db, "INSERT into disburse(ind,mpesa,supplier,amount)
    values('$ind','$mpesa','$supplier','$amount')");
            if ($insert) {
                $sql = "UPDATE purchase set payment='Disbursed' where supplier_id='$supplier' and status='Approved' and payment='Pending'";
                if (mysqli_query($db, $sql)) {
                    $response["success"] = 1;
                    $response["message"] = "Amount disbursed successfully";
                    echo json_encode($response);
                    mysqli_close($db);
                } else {
                    $response["success"] = 0;
                    $response["message"] = "  Failed to submit";
                    echo json_encode($response);
                    mysqli_close($db);
                }
            } else {
                $response["success"] = 0;
                $response["message"] = "An error occurred while sending payment";
                echo json_encode($response);
                mysqli_close($db);
            }
        }
    }
}