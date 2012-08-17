<!--
Date: Fri Aug 17 07:28:19 SAST 2012
File: menu.gsp
Author: brett chaldecott
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
              <li><a href="index.gsp">Profile</a></li>
              <li class="divider"></li>
              <li><a href="#">Sign Out</a></li>
            </ul>
          </div>
          <div class="nav-collapse">
            <ul class="nav">
              <li class="${params.menuItem == "home" ? "active" : ""}" ><a href="#">Home</a></li>
              <li><a href="pckg/">Package</a></li>
              <li><a href="billing/">Billing</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>