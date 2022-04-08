<?php
include("../conn.php"); //fullname,username,mobile,role,email,password
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
    $role = $_POST['role'];
    $year = date("Y");
    $mon = date("M");
    $naming = "/^[a-zA-Z ]{2,}$/";
    if (!preg_match($naming, $fullname)) {
        $result["success"] = 0;
        $result["message"] = "Your name is invalid";
        echo json_encode($result);
    } else {
        $select = "SELECT email FROM staff WHERE email='$email'";
        $query = mysqli_query($db, $select);
        if (mysqli_num_rows($query) > 0) {
            $result["success"] = 0;
            $result["message"] = "Failed!! Email already in use";
            echo json_encode($result);
            mysqli_close($db);
        } else {
            $select = "SELECT username FROM staff WHERE username='$username'";
            $query = mysqli_query($db, $select);
            if (mysqli_num_rows($query) > 0) {
                $response["success"] = 0;
                $response["message"] = "Failed!! Username is already taken";
                echo json_encode($response);
                mysqli_close($db);
            } else {
                $select = "SELECT role FROM staff WHERE role='$role' and status!=2";
                $query = mysqli_query($db, $select);
                if (mysqli_num_rows($query) > 0) {
                    $response["success"] = 0;
                    $response["message"] = "Failed!! role is already taken";
                    echo json_encode($response);
                    mysqli_close($db);
                } else {
                    $select = "SELECT phone FROM staff WHERE phone='$contact'";
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
                        $id = $values . '-' . $year;
                        $sql = "INSERT INTO staff(reg_id,fullname,username,phone,email,password,role)
    VALUES('$id','$fullname','$username','$contact','$email','$password','$role')";
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