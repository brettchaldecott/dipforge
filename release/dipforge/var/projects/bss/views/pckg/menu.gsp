<!--
Date: Tue Jun 26 19:16:04 SAST 2012
File: menu.gsp
Author: admin
-->
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="span12">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="../">Management</a>
          <div class="btn-group pull-right">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#"><i class="icon-user"></i>${request.getRemoteUser()}<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="#">Profile</a></li>
              <li class="divider"></li>
              <li><a href="#">Sign Out</a></li>
            </ul>
          </div>
          <div class="nav-collapse">
            <ul class="nav">
              <li><a href="../">Home</a></li>
              <li class="${params.menuItem == "package" ? "active" : ""}" ><a href="#">Package</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>