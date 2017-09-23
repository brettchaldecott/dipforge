<!--
Date: Tue Jun 26 19:39:40 SAST 2012
File: sidebar.gsp
Author: brett chaldecott
-->

    <div class="span3">
      <div class="well sidebar-nav">
        <ul class="nav nav-list">
          <li class="nav-header">Package Management</li>
          <% 
          if (request.isUserInRole("package")) { %>
          <li class="${params.menuItem == "package" ? "active" : ""}"><a href="index.gsp"><i class="icon-shopping-cart"></i>Installed Packages</a></li>
          <% }
          if (request.isUserInRole("category_manager")) { %>
          <li class="${params.menuItem == "category" ? "active" : ""}"><a href="category.gsp"><i class="icon-list"></i>Category Configuration</a></li>
          <% }
          if (request.isUserInRole("category_manager")) { %>
          <li class="${params.menuItem == "vendor" ? "active" : ""}"><a href="vendor.gsp"><i class="icon-list"></i>Vendor Configuration</a></li>
          <% }
          if (request.isUserInRole("product_manager")) { %>
          <li class="${params.menuItem == "product" ? "active" : ""}"><a href="product.gsp"><i class="icon-list"></i>Product Configuration</a></li>
          <% }
          if (request.isUserInRole("package_manager")) { %>
          <li class="${params.menuItem == "configuration" ? "active" : ""}"><a href="configuration.gsp"><i class="icon-briefcase"></i>Package Configuration</a></li>
          <% }
          if (request.isUserInRole("package_manager")) { %>
          <li class="${params.menuItem == "catalog" ? "active" : ""}"><a href="catalog.gsp"><i class="icon-gift"></i>Catalog Configuration</a></li>
          <% } 
          if (request.isUserInRole("package_manager")) { %>
          <li class="${params.menuItem == "offering" ? "active" : ""}"><a href="offering.gsp"><i class="icon-tags"></i>Offering Configuration</a></li>
          <% }
          if (request.isUserInRole("isp_manager")) { %>
          <li class="${params.menuItem == "setup" ? "active" : ""}"><a href="setup.gsp"><i class="icon-play"></i>Setup</a></li>
          <% } 
          if (params.menuItem == "category") {%>
          <li class="nav-header">Category Management</li>
          <li><a data-toggle="modal" href="#categoryModal"><i class="icon-plus"></i>Add Category</a></li>
          <% } else if (params.menuItem == "vendor") {%>
          <li class="nav-header">Vendor Management</li>
          <li><a data-toggle="modal" href="#vendorModal"><i class="icon-plus"></i>Add Vendor</a></li>
          <% } else if (params.menuItem == "product") {%>
          <li class="nav-header">Product Management</li>
          <li><a data-toggle="modal" href="#productModal"><i class="icon-plus"></i>Add Product</a></li>
          <% } else if (params.menuItem == "configuration") { %>
          <li class="nav-header">Configuration Management</li>
          <li><a data-toggle="modal" href="#pckgModal"><i class="icon-plus"></i>Add Package</a></li>
          <% } else if (params.menuItem == "offering") { %>
          <li class="nav-header">Offering Management</li>
          <li><a data-toggle="modal" href="#offeringModal"><i class="icon-plus"></i>Add Offering</a></li>
          <% } else if (params.menuItem == "setup") { %>
          <li class="nav-header">ISP Management</li>
          <li><a data-toggle="modal" href="#setupModal"><i class="icon-play"></i>Setup</a></li>
          <% } %>
        </ul>
      </div><!--/.well -->
    </div><!--/span-->