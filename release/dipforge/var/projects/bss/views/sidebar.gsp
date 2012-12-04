<!--
Date: Fri Aug 17 07:28:32 SAST 2012
File: sidebar.gsp
Author: brett chaldecott
-->

<div class="span3">
    <div class="well sidebar-nav">
        <ul class="nav nav-list">
            <li class="nav-header">Management</li>
            <li class="${params.menuItem == "home" ? "active" : ""}"><a href="index.gsp"><i class="icon-list"></i>My Profile</a></li>
            <li class="${params.menuItem == "users" ? "active" : ""}"><a href="users.gsp"><i class="icon-user"></i>Users</a></li>
            <li class="${params.menuItem == "shopping" ? "active" : ""}"><a href="shopping.gsp"><i class="icon-shopping-cart"></i>Shopping</a></li>
        </ul>
    </div>
</div>