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
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#"><i class="icon-shopping-cart"></i>Checkout<span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="checkout.gsp"><i class="icon-shopping-cart"></i>&nbsp;Checkout</a></li>
                <li><a href="index.gsp"><i class="icon-user"></i>&nbsp;Profile</a></li>
            </ul>
          </div>
          <div class="nav-collapse">
            <ul class="nav">
              <li class="${params.menuItem == "home" ? "active" : ""}" ><a href="#">Home</a></li>
              <li><a href="pckg/">Package</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
