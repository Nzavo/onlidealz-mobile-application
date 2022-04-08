<?php
ob_start();
error_reporting(0);
session_start();
include("conn.php");
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>admin</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">

    <link rel='stylesheet prefetch'
        href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>

    <link rel="stylesheet" href="login.css">

    <style type="text/css">
    #buttn {
        color: #fff;
        background-color: #ff3300;
    }
    </style>

</head>

<body>
    <?php
    if (isset($_POST['submit'])) {
        $username = $_POST['username'];
        $password = $_POST['password'];

        if (!empty($_POST["submit"])) {
            $loginquery = "SELECT * FROM admin WHERE username='$username' && password='" . md5($password) . "'";
            $result = mysqli_query($db, $loginquery);
            $row = mysqli_fetch_array($result);

            if (is_array($row)) {
                $success = "Login was Successful!!";
                $_SESSION["user_id"] = $row['username'];
                header("refresh:1;url=index");
            } else {
                $message = "Invalid Username or Password!";
            }
        }
    }
    ob_end_flush();
    if (isset($_POST['timer'])) {
        if (
            empty($_POST['username']) ||
            empty($_POST['email']) ||
            empty($_POST['password']) ||
            empty($_POST['cpassword'])
        ) {
            $message = "Enter All fields";
        } else {
            if ($_POST['password'] != $_POST['cpassword']) {
                $message = "Password not match";
            } elseif (strlen($_POST['password']) < 6) {
                $message = "Password Must be greater than 6 characters";
            } elseif (!filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {
                $message = "Invalid email address please type a valid email!";
            } else {
                $mql = "INSERT INTO admin(username,email,password) VALUES('" . $_POST['username'] . "','" . $_POST['email'] . "','" . md5($_POST['password']) . "')";
                mysqli_query($db, $mql);
                $success = "Account Created successfully!";
                header("refresh:5;url=login");
            }
        }
    }


    ?>
    <div class="pen-title">
        <h1>Admin Only</h1>
    </div>
    <div class="module form-module">
        <div class="toggle">

        </div>
        <div class="form">
            <?php
            if (mysqli_num_rows(mysqli_query($db, "SELECT * from admin")) > 0) {
                echo '<h2>Login to your account</h2>
                <span style="color:blue;">' . $message . '</span>
            <span style="color:green;">' . $success . '</span>
            <form action="" method="post">
                <input type="text" placeholder="Username" name="username" />
                <input type="password" placeholder="Password" name="password" />
                <input type="submit" id="buttn" name="submit" value="login" style="background:blue" />
            </form>';
            } else {
                echo '<h2>Create Account</h2>
                <form action="" method="post">
                                 <div class="row">
								  <div class="form-group col-sm-12">
                                       <label for="exampleInputEmail1">User-Name</label>
                                       <input class="form-control" type="text" name="username" id="example-text-input" placeholder="UserName"> 
                                    </div>
                                    <div class="form-group col-sm-6">
                                       <label for="exampleInputEmail1">Email address</label>
                                       <input type="text" class="form-control" name="email" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email"> 
                                    </div>
                                    <div class="form-group col-sm-6">
                                       <label for="exampleInputPassword1">Password</label>
                                       <input type="password" class="form-control" name="password" id="exampleInputPassword1" placeholder="Password"> 
                                    </div>
                                    <div class="form-group col-sm-6">
                                       <label for="exampleInputPassword1">Repeat password</label>
                                       <input type="password" class="form-control" name="cpassword" id="exampleInputPassword2" placeholder="Password"> 
                                    </div>
                                   
                                 </div>
                                 <span style="color:blue;">' . $message . '</span>
                             <span style="color:green;">' . $success . '</span>
                                
                                 <div class="row">
                                    <div class="col-sm-4">
                                       <p> <input type="submit" value="Register" name="timer" class="btn theme-btn" style="background:blue"> </p>
                                    </div>
                                 </div>
                              </form>';
            }
            ?>

        </div>
    </div>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
</body>

</html>