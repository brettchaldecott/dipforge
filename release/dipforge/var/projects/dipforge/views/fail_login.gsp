<!--
Date: Mon Dec 31 06:05:25 SAST 2012
File: fail_login.gsp
Author: brett chaldecott
-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <title>Sign in &middot;</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta name="description" content=""/>
        <meta name="author" content="brett chaldecott"/>
        <link href="../public/bootstrap/css/bootstrap.css" rel="stylesheet">
        <style type="text/css">
            body{
                padding-top:40px;
                padding-bottom:40px;
                background-color:#f5f5f5;
            }
            .form-signin{
                max-width:300px;
                padding:19px 29px 29px;
                margin:0 auto 20px;
                background-color:#fff;
                border:1px solid #e5e5e5;
                -webkit-border-radius:5px;
                -moz-border-radius:5px;
                border-radius:5px;
                -webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);
                -moz-box-shadow:0 1px 2px rgba(0,0,0,.05);
                box-shadow:0 1px 2px rgba(0,0,0,.05);
            }
            .form-signin .form-signin-heading,.form-signin .checkbox{
                margin-bottom:10px;
            }
            .form-signin input[type="text"],.form-signin input[type="password"]{
                font-size:16px;
                height:auto;
                margin-bottom:15px;
                padding:7px 9px;
            }
            
        </style>
        <link href="../public/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js">                
            </script>
        <![endif]--><!--//-->
    </head>
    <body>
        <div class="container">
            <form class="form-signin" method="POST" action="j_security_check">
                <h2 class="form-signin-heading">Please sign in</h2>
                <p class="text-error">Invalid Username or Password.</p>
                <input type="text" class="input-block-level" placeholder="Username" name= "j_username">
                <input type="password" class="input-block-level" placeholder="Password" name= "j_password">
                <br/>
                <button class="btn btn-large btn-primary" type="submit">Sign in</button>
            </form>
        </div>
        <script src="../public/jquery/jquery-1.7.2.min.js"></script>
        <script src="../public/bootstrap/js/bootstrap.js"></script>
    </body>
</html>