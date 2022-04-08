<?php
include("../conn.php"); //fullname,username,business_registry,business_name,existence,mobile,address,email,password
function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $fullname = test_input($_POST["fullname"]);
    $username = $_POST['username'];
    $business_registry = $_POST['business_registry'];
    $business_name = $_POST['business_name'];
    $contact = $_POST['mobile'];
    $email = $_POST['email'];
    $password = md5($_POST['password']);
    $address = $_POST['address'];
    $existence = $_POST['existence'];
    $year = date("Y");
    $mon = date("M");
    $naming = "/^[a-zA-Z ]{2,}$/";
    if (!preg_match($naming, $fullname)) {
        $result["success"] = 0;
        $result["message"] = "Your name is invalid";
        echo json_encode($result);
    } else {
        $select = "SELECT email FROM supplier WHERE email='$email'";
        $query = mysqli_query($db, $select);
        if (mysqli_num_rows($query) > 0) {
            $result["success"] = 0;
            $result["message"] = "Email already in use";
            echo json_encode($result);
            mysqli_close($db);
        } else {
            $select = "SELECT business_registry FROM supplier WHERE business_registry='$business_registry'";
            $query = mysqli_query($db, $select);
            if (mysqli_num_rows($query) > 0) {
                $result["success"] = 0;
                $result["message"] = "Business_RegNo not Accepted";
                echo json_encode($result);
                mysqli_close($db);
            } else {
                $select = "SELECT existence FROM supplier WHERE existence='$existence'";
                $query = mysqli_query($db, $select);
                if (mysqli_num_rows($query) > 2) {
                    $result["success"] = 0;
                    $result["message"] = "The Nature You selected has more than 2 other Suppliers.";
                    echo json_encode($result);
                    mysqli_close($db);
                } else {
                    $select = "SELECT username FROM supplier WHERE username='$username'";
                    $query = mysqli_query($db, $select);
                    if (mysqli_num_rows($query) > 0) {
                        $response["success"] = 0;
                        $response["message"] = " Username ia already taken";
                        echo json_encode($response);
                        mysqli_close($db);
                    } else {
                        $select = "SELECT phone FROM supplier WHERE phone='$contact'";
                        $query = mysqli_query($db, $select);
                        if (mysqli_num_rows($query) > 0) {
                            $response["success"] = 0;
                            $response["message"] = "Phone number already taken";
                            echo json_encode($response);
                            mysqli_close($db);
                        } else {
                            $count_my_page = ("user_key.txt");
                            $hits = file($count_my_page);
                            $hits[0]++;
                            $fp = fopen($count_my_page, "w");
                            fputs($fp, "$hits[0]");
                            fclose($fp);
                            $values = $hits[0];
                            $id = $values . '-' . $year;
                            $sql = "INSERT INTO supplier(business_registry,reg_id,fullname,username,business_name,phone,email,password,address,existence)
    VALUES('$business_registry','$id','$fullname','$username','$business_name','$contact','$email','$password','$address','$existence')";
                            if (mysqli_query($db, $sql)) {
                                $response["success"] = 1;
                                $response["message"] = "Your Account was created succesfully";
                                echo json_encode($response);
                                mysqli_close($db);
                            } else {
                                $response["success"] = 0;
                                $response["message"] = " Failed to create account";
                                echo json_encode($response);
                                mysqli_close($db);
                            }
                        }
                    }
                }
            }
        }
    }
}