<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Dipforge Cloud | Version 4.1.a21</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="brett chaldecott">
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <script type="text/javascript" src="extjs-4.1.0/bootstrap.js"></script>
    <script type="text/javascript" src="js/DataGrid.js"></script>
    <style type="text/css">
        body{padding-top:20px;padding-bottom:60px;}
        /**/
        .container{margin:0 auto;max-width:1000px;}
        .container>hr{margin:60px 0;}
        /**/
        .jumbotron{margin:80px 0;text-align:center;}
        .jumbotron h1{font-size:100px;line-height:1;}
        .jumbotron .lead{font-size:24px;line-height:1.25;}
        .jumbotron .btn{font-size:21px;padding:14px 24px;}
        /**/
        .marketing{margin:60px 0;}
        .marketing p + h4{margin-top:28px;}
        /**/
        .navbar .navbar-inner{padding:0;}
        .navbar .nav{margin:0;display:table;width:100%;}
        .navbar .nav li{display:table-cell;width:1%;float:none;}
        .navbar .nav li a{font-weight:bold;text-align:center;border-left:1px solid rgba(255,255,255,.75);border-right:1px solid rgba(0,0,0,.1);}
        .navbar .nav li:first-child a{border-left:0;border-radius:3px 0 0 3px;}
        .navbar .nav li:last-child a{border-right:0;border-radius:0 3px 3px 0;}
            
    </style>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <!--[if lt IE 9]>
        <script src="../assets/js/html5shiv.js"></script>
    <![endif]--><!--//-->
        
</head>
<body>
    <div class="container">
        <div class="masthead">
            <h3 class="muted"><a href="."><image height="30px" width="30px" src="images/dipForgeLogo_small.png"/></a>&nbsp;dipforge&nbsp;cloud&nbsp;4.1.a21
                <a class="btn btn-medium btn-info pull-right" href="/DipforgeWeb/desktop/" style="margin-right:10px;margin-top:10px;">Sign In</a>
            </h3>
            <div class="navbar">
                <div class="navbar-inner">
                    <div class="container">
                        <ul class="nav" id="bottomNav">
                            <li class="active">
                                <a href=".">Welcome</a>
                            </li>
                            <li>
                                <a href="/DipforgeWeb/desktop/">Desktop</a>
                            </li>
                            <li>
                                <a href="http://dipforge.net/">Dipforge</a>
                            </li>
                            <li>
                                <a href="http://dipforge.net/introduction-to-dipforge/">Introduction</a>
                            </li>
                            <li>
                                <a href="http://dipforge.net/developing-in-dipforge/">Developing</a>
                            </li>
                            <li>
                                <a href="http://dipforge.net/hello-world-example/">Hello World</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="jumbotron">
            <h1>Welcome</h1>
            <p class="lead">Develop your Enterprise Applications Rapidly and Easily</p>
            <p class="lead">Turn it RED(Rapid Enterprise Development)</p>
            <a class="btn btn-large btn-info" href="/DipforgeWeb/desktop/">Sign In &raquo;</a>
        </div>
        <div class="row-fluid">
            <div class="span4">
                <h2>About Us</h2>
                <p>Dipforge is an Open Source Application server focused on Rapid Enterprise Development. This means that it is a platform focused on enabling enterprises to rapidly develop solutions that suite their requirements without having to focus on how they solve their problems
                <br>
                <br>It focuses on method over configuration, this means that it supplies a common way of developing  solutions rather than gluing complex components together to form a solution.
                <br><a class="btn btn-small btn-info" href="../desktop/">Sign In &raquo;</a>
                </p>
            </div>
            <div class="span4 pull-right">
                <h2>News</h2>
                <p><div id="data-grid"></div></p>
            </div>
        </div>
        <hr>
        <div class="footer">
            <p>&copy; BurntJam 2014 <a class="btn btn-medium btn-info pull-right" href="http://dipforge.sourceforge.net/">Powered by Dipforge</a></p>
        </div>

    </div>
    <script src="jquery/jquery-1.7.2.min.js"></script>
    <script src="bootstrap/js/bootstrap.js"></script>
</body>
</html>
