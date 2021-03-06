<?php
include("sess.php");
if (empty($_SESSION['user_id'])) {
    header('location:login');
} else {
     if (isset($_POST['rejectBtn'])) {
        $customerid = $_POST['identity'];
        $remarks = $_POST['remarks'];
        $sql = mysqli_query($db,"update chat set reply='$remarks' WHERE id='$customerid'");
        if($sql){
        header('location:feedback');}
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
                    <h3 class="text-primary">Cusotmer Feedback</h3>
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
                                                <th></th>
                                                <th>rate</th>
                                                <th>message</th>
                                                <th>sender</th>
                                                <th>name</th>
                                                <th>phone</th>
                                                <th>post</th>
                                                <th>send_date</th>
                                                <th>reply</th>
                                                <th>reply_date</th>

                                            </tr>

                                        </thead>
                                        <tbody>


                                            <?php
                                                $sql = "SELECT * FROM chat where receiver='Admin' order by send_date desc";
                                                $query = mysqli_query($db, $sql);

                                                if (!mysqli_num_rows($query) > 0) {
                                                    echo '<td colspan="10"><center>No Record was Found</center></td>';
                                                } else {
                                        $cnt = 1;
                                                    while ($rows = mysqli_fetch_array($query)) {
                                                ?> <tr>
                                                <td><?php echo htmlentities($cnt); ?></td>
                                                <td><?php echo $rows['rate'].'/5'; ?></td>
                                                <td><?php echo $rows['message']; ?></td>
                                                <td><?php echo $rows['sender']; ?></td>
                                                <td><?php echo $rows['name']; ?></td>
                                                <td><?php echo $rows['phone']; ?></td>
                                                <td><?php echo $rows['post']; ?></td>
                                                <td><?php echo $rows['send_date']; ?></td>
                                                <td>
                                                    <?php if($rows['reply']=='Reply Pending'){
                                                        echo '<div class="dropdown" style="float: right;">
                                                        <button class="btn btn-success" type="button"
                                                            data-toggle="dropdown">Reply</button>
                                                        <ul class="dropdown-menu alert panel-footer">
                                                            <li>
                                                                <form method="post">
                                                                    <input type="hidden" name="identity"
                                                                        value="'.$rows["id"].'"
                                                    />
                                                    <textarea class="form-control" rows="15" name="remarks"
                                                        placeholder="Type some message" required></textarea>
                                                    <input type="submit" name="rejectBtn" value="Reply"
                                                        class="btn btn-success" />
                                                    </form>
                                                    </li>
                                                    </ul>
                                </div>';
                                }else{echo $rows['reply']; 
                                }?>

                                                </td>
                                                <td><?php echo $rows['reply_date']; ?></td>


                                            </tr>
                                            <?php $cnt = $cnt + 1; }
                                                }
                                                ?>
                                        </tbody>
                                        <tfoot class="bg-info">
                                            <tr>
                                                <th></th>
                                                <th>rate</th>
                                                <th>message</th>
                                                <th>sender</th>
                                                <th>name</th>
                                                <th>phone</th>
                                                <th>post</th>
                                                <th>send_date</th>
                                                <th>reply</th>
                                                <th>reply_date</th>

                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <footer class="footer"> ?? Developed by Jimmy 2022. </footer>
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