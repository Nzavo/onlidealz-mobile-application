<?php
include("../conn.php"); //fullname,username,mobile,address,email,password
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
    $contact = $_POST['mobile'];
    $email = $_POST['email'];
    $password = md5($_POST['password']);
    $address = $_POST['address'];
    $year = date("Y");
    $mon = date("M");
    $naming = "/^[a-zA-Z ]{2,}$/";
    if (!preg_match($naming, $fullname)) {
        $result["success"] = 0;
        $result["message"] = "Your name is invalid";
        echo json_encode($result);
    } else {
        $select = "SELECT email FROM customer WHERE email='$email'";
        $query = mysqli_query($db, $select);
        if (mysqli_num_rows($query) > 0) {
            $result["success"] = 0;
            $result["message"] = "Failed!! Email already in use";
            echo json_encode($result);
            mysqli_close($db);
        } else {
            $select = "SELECT username FROM customer WHERE username='$username'";
            $query = mysqli_query($db, $select);
            if (mysqli_num_rows($query) > 0) {
                $response["success"] = 0;
                $response["message"] = "Failed!! Username is already taken";
                echo json_encode($response);
                mysqli_close($db);
            } else {
                $select = "SELECT phone FROM customer WHERE phone='$contact'";
                $query = mysqli_query($db, $select);
                if (mysqli_num_rows($query) > 0) {
                    $response["success"] = 0;
                    $response["message"] = "Failed!! Phone number already taken";
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
                    $id = $values . '/' . $year;
                    $sql = "INSERT INTO customer(reg_id,fullname,username,phone,email,password,address)
    VALUES('$id','$fullname','$username','$contact','$email','$password','$address')";
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