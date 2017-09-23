<!--
Date: Sun Jul 12 09:47:36 SAST 2015
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
          <a class="brand" href="./">Setup</a>
          <div class="btn-group pull-right">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#"><i class="icon-user"></i>${request.getRemoteUser()}<span class="caret"></span></a>
            <ul class="dropdown-menu">
            </ul>
          </div>
          <div class="nav-collapse">
            <ul class="nav">
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
