<?php
ob_start();
error_reporting(0);
session_start();
include("conn.php");
if (empty($_SESSION['user_id'])) {
    header('location:login');
} else {
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>onlidealz</title>
    <link href="css/lib/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="css/helper.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>

<body class="fix-header">
    <div class="preloader">
        <svg class="circular" viewBox="25 25 50 50">
            <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" />
        </svg>
    </div>
    <div id="main-wrapper">
        <div class="header">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index">
                        <b>OnliDealz</b>
                    </a>
                </div>
                <div class="navbar-collapse">
                    <ul class="navbar-nav mr-auto mt-md-0">
                        <li class="nav-item"> <a class="nav-link nav-toggler hidden-md-up text-muted  "
                                href="javascript:void(0)"><i class="mdi mdi-menu"></i></a> </li>
                        <li class="nav-item m-l-10"> <a class="nav-link sidebartoggler hidden-sm-down text-muted  "
                                href="javascript:void(0)"><i class="ti-menu"></i></a> </li>

                    </ul>
                    <ul class="navbar-nav my-lg-0">

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-muted  " href="#" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i> Logout</a>
                            <div class="dropdown-menu dropdown-menu-right animated zoomIn">
                                <ul class="dropdown-user">
                                    <li><a href="logout"><i class="fa fa-power-off"></i> Logout</a></li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="left-sidebar bg-danger">
            <div class="scroll-sidebar">
                <?php include("cornered.php") ?>
            </div>
        </div>
        <div class="page-wrapper bg-danger" style="height:100%">
            <div class="row page-titles">
                <div class="col-md-5 align-self-center">
                    <h3 class="text-primary">Home Page</h3>
                </div>

            </div>
            <div class="container-fluid">
                <div class="row">

                    <div class="col-md-3">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <h2><?php echo mysqli_num_rows(mysqli_query($db, "SELECT * from customer where status=0")); ?>
                                    </h2>
                                    <p class="m-b-0">New Customers</p>

                                </div>
                                <div class="media-body media-text-right">
                                    <span><i class="fa fa-bell f-s-40 color-danger"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <h2><?php echo mysqli_num_rows(mysqli_query($db, "SELECT * from supplier where status=0")); ?>
                                    </h2>
                                    <p class="m-b-0">New Suppliers</p>
                                </div>
                                <div class="media-body media-text-right">
                                    <span><i class="fa fa-car f-s-40 color-success" aria-hidden="true"></i></span>

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <h2><?php echo mysqli_num_rows(mysqli_query($db, "SELECT * from driver where status=0")); ?>
                                    </h2>
                                    <p class="m-b-0">New Drivers</p>
                                </div>
                                <div class="media-body media-text-right">
                                    <span><i class="fa fa-truck f-s-40 color-danger"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <h2><?php echo mysqli_num_rows(mysqli_query($db, "SELECT * from staff where status=0")); ?>
                                    </h2>
                                    <p class="m-b-0">Staff</p>
                                </div>
                                <div class="media-body media-text-right">
                                    <span><i class="fa fa-file f-s-40 color-primary" aria-hidden="true"></i></span>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <h2><?php echo (mysqli_num_rows(mysqli_query($db, "SELECT * from feedback")))+
                                    (mysqli_num_rows(mysqli_query($db, "SELECT * from chat where receiver='Admin'"))); ?>
                                    </h2>
                                    <p class="m-b-0">Feedback & Ratings</p>
                                </div>
                                <div class="media-body media-text-right">
                                    <span><i class="fa fa-comment f-s-40 color-primary" aria-hidden="true"></i></span>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="footer"> © Developed by Jimmy 2022. </footer>
        </div>
    </div>
    <script src="js/lib/jquery/jquery.min.js"></script>
    <script src="js/lib/bootstrap/js/popper.min.js"></script>
    <script src="js/lib/bootstrap/js/bootstrap.min.js"></script>
    <script src="js/jquery.slimscroll.js"></script>
    <script src="js/sidebarmenu.js"></script>
    <script src="js/lib/sticky-kit-master/dist/sticky-kit.min.js"></script>
    <script src="js/custom.min.js"></script>

</body>

</html>
<?php
}
?>