<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Dipforge Administration | Dipforge</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Dipforge Administration">
    <meta name="author" content="Dipforge">

    <!-- Le styles -->
    <link href="../bootstrap/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
    <link href="../bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
  </head>

  <body>

    <% PageManager.includeWithResult("Menu.groovy", request, response, ["menuItem" : "package"]) %>
    
    <% PageManager.includeWithResult("SideBar.groovy", request, response, ["menuItem" : "offering"]) %>
    
    <% PageManager.includeWithResult("Installed.groovy", request, response, [:]) %>
    
    <% PageManager.includeWithResult("Footer.groovy", request, response, ["menuItem" : "offering"]) %>
    
  </body>
</html>
