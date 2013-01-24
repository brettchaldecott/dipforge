<!--
Date: Tue Jan 22 06:16:09 SAST 2013
File: index.gsp
Author: brett chaldecott
-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <title>Social Stream</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta name="description" content=""/>
        <meta name="author" content="brett chaldecott"/>
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <style type="text/css">
          body {
            padding-top: 120px;
            padding-bottom: 40px;
          }
        </style>
        <link href="../public/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js">                
            </script>
        <![endif]--><!--//-->
    </head>
    <body>
        <div class="navbar navbar-fixed-top navbar-static-top">
            <div class="navbar-inner">
                    <div class="span2">
                        <a class="brand" href="../">Stream</a>
                    </div>
                    <div class="span8">
                        <form class="navbar-form">
                          <textarea placeholder="Message" class="span7" style="margin-top:7px;" id="messageText"></textarea>
                          <br>
                          <button type="submit" class="btn btn-primary" style="margin-bottom:7px;" id="createMessage">Submit</button>
                        </form>
                    </div>
            </div>
        </div>
        <div class="span10">
            <ul class="thumbnails">
                <div id="messageList">
                <li class="span9">
                    <div class="thumbnail">
                        <p><b>test</b> 2013-01-22</p>
                        <p>hello to the world</p>
                    </div>
                </li>
                <li class="span9">
                    <div class="thumbnail">
                        <p><b>test</b> 2013-01-22</p>
                        <p>hello to the world</p>
                    </div>
                </li>
                <li class="span9">
                    <div class="thumbnail">
                        <p><b>test</b> 2013-01-22</p>
                        <p>hello to the world</p>
                    </div>
                </li>
                <li class="span9">
                    <div class="thumbnail">
                        <p><b>test</b> 2013-01-22</p>
                        <p>hello to the world</p>
                    </div>
                </li>
                <li class="span9">
                    <div class="thumbnail">
                        <p><b>test</b> 2013-01-22</p>
                        <p>hello to the world</p>
                    </div>
                </li>
                <li class="span9">
                    <div class="thumbnail">
                        <p><b>test</b> 2013-01-22</p>
                        <p>hello to the world</p>
                    </div>
                </li>
                <li class="span9">
                    <div class="thumbnail">
                        <p><b>test</b> 2013-01-22</p>
                        <p>hello to the world</p>
                    </div>
                </li>
                <li class="span9">
                    <div class="thumbnail">
                        <p><b>test</b> 2013-01-22</p>
                        <p>hello to the world</p>
                    </div>
                </li>
                </div>
            </ul>
        </div>
        <script src="jquery/jquery-1.7.2.min.js"></script>
        <script src="bootstrap/js/bootstrap.js"></script>
        <script src="js/Messages.js"></script>
    </body>
</html>