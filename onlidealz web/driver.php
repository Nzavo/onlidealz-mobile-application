<?php
include("sess.php");
if (empty($_SESSION['user_id'])) {
    header('location:login');
} else {
    if (isset($_GET['inid'])) {
        $id = $_GET['inid'];
        $status = 2;
        mysqli_query($db, "UPDATE driver set status='$status' where reg_id='$id'");
        header('location:driver');
    }
    if (isset($_GET['id'])) {
        $id = $_GET['id'];
        $status = 1;
        mysqli_query($db, "UPDATE driver set status='$status' where reg_id='$id'");
        header('location:driver');
    }
    if (isset($_GET['idi'])) {
        $idi = $_GET['idi'];
        $status = 1;
        mysqli_query($db, "UPDATE driver set status='$status' where reg_id='$idi'");
        header('location:driver');
    }
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
                    <h3 class="text-primary">Manage Drivers</h3>
                </div>

            </div>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">


                        <div class="card">
                            <div class="card-body">

                                <div class="table-responsive m-t-40">
                                    <table id="myTable" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>RegId</th>
                                                <th>License</th>
                                                <th>Username</th>
                                                <th>Name</th>
                                                <th>Gender</th>
                                                <th>Email</th>
                                                <th>Phone</th>
                                                <th>Address</th>
                                                <th>Status</th>
                                                <!--<th>Comment</th>-->
                                                <th>Reg-Date</th>
                                                <th>Action</th>

                                            </tr>
                                        </thead>
                                        <tbody>


                                            <?php
                                                $sql = "SELECT * FROM driver order by reg_id desc";
                                                $query = mysqli_query($db, $sql);

                                                if (!mysqli_num_rows($query) > 0) {
                                                    echo '<td colspan="11"><center>No Record was Found</center></td>';
                                                } else {
                                                    while ($rows = mysqli_fetch_array($query)) {
                                                ?> <tr>
                                                <td><?php echo $rows['reg_id']; ?></td>
                                                <td><?php echo $rows['license']; ?></td>
                                                <td><?php echo $rows['username']; ?></td>
                                                <td><?php echo $rows['fullname']; ?></td>
                                                <td><?php echo $rows['gender']; ?></td>
                                                <td><?php echo $rows['email']; ?></td>
                                                <td><?php echo $rows['phone']; ?></td>
                                                <td><?php echo $rows['address']; ?></td>
                                                <td class="center"><?php if ($rows['status'] == 0) {
                                                                                    echo htmlentities("Pending");
                                                                                } elseif ($rows['status'] == 1) {
                                                                                    echo htmlentities("Approved");
                                                                                } else {
                                                                                    echo htmlentities("Rejected");
                                                                                }
                                                                                ?></td>
                                                <!--<td><php echo $rows['comment']; ?></td>-->
                                                <td><?php echo $rows['reg_date']; ?></td>
                                                <td class="center">
                                                    <?php if ($rows['status'] == 1) { ?>
                                                    <a href="driver?inid=<?php echo htmlentities($rows['reg_id']); ?>"
                                                        onclick="return confirm('Are you sure you want to block this driver?');">
                                                        <button class="btn btn-danger"> Block</button></a>
                                                    <?php } elseif ($rows['status'] == 0) { ?>
                                                    <a href="driver?idi=<?php echo htmlentities($rows['reg_id']); ?>"
                                                        onclick="return confirm('Are you sure you want to Accept this driver?');">
                                                        <button class="btn btn-success"> Accepted</button></a>
                                                    <a href="driver?inid=<?php echo htmlentities($rows['reg_id']); ?>"
                                                        onclick="return confirm('Are you sure you want to block this driver?');">
                                                        <button class="btn btn-danger"> Block</button></a>
                                                    <?php } else { ?>
                                                    <a href="driver?id=<?php echo htmlentities($rows['reg_id']); ?>"
                                                        onclick="return confirm('Are you sure you want to allow this blocked driver?');"><button
                                                            class="btn btn-success"> Accepted</button></a>
                                                    <?php } ?>
                                                </td>
                                            </tr>
                                            <?php }
                                                }
                                                ?>
                                        </tbody>
                                        <tfoot class="bg-info">
                                            <tr>
                                                <th>RegId</th>
                                                <th>License</th>
                                                <th>Username</th>
                                                <th>Name</th>
                                                <th>Gender</th>
                                                <th>Email</th>
                                                <th>Phone</th>
                                                <th>Address</th>
                                                <th>Status</th>
                                                <!--<th>Comment</th>-->
                                                <th>Reg-Date</th>
                                                <th>Action</th>

                                            </tr>
                                        </tfoot>
                                    </table>
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
    <script src="js/lib/datatables/datatables.min.js"></script>
    <script src="js/lib/datatables/cdn.datatables.net/buttons/1.2.2/js/dataTables.buttons.min.js"></script>
    <script src="js/lib/datatables/cdn.datatables.net/buttons/1.2.2/js/buttons.flash.min.js"></script>
    <script src="js/lib/datatables/cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
    <script src="js/lib/datatables/cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
    <script src="js/lib/datatables/cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
    <script src="js/lib/datatables/cdn.datatables.net/buttons/1.2.2/js/buttons.html5.min.js"></script>
    <script src="js/lib/datatables/cdn.datatables.net/buttons/1.2.2/js/buttons.print.min.js"></script>
    <script src="js/lib/datatables/datatables-init.js"></script>

</body>

</html>
<?php
}
?>