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
            <% if (params.menuItem == "shopping") { %>
            <li class="nav-header">Catalog</li>
                <li class="${params.catalogId == null ? "active" : ""}"><a href="shopping.gsp"><i class="icon-play"></i>Home</a></li>
                <%
                if (params.catalog != null) {
                    params.catalog.getEntries()?.each { entry ->
                        buildCatalog(["Home"],entry)
                    }
                }
            } %>
        </ul>
    </div>
</div>


<%
def buildCatalog(def path, def entry) {
    
    if (entry.getName() == "Base") {
        return
    }
    // TODO: this is very lazy. It is done so that when a catalog entry is deleted
    // a black node does not appear in the list. Should perform a search during deletion
    // and updated the referencing entries.
    if (entry.getName() == null || entry.getName() == "") {
        return
    }
    %>
    <li class="${params.catalogId != null && params.catalogId == entry.getId() ? "active" : ""}"><a href="shopping.gsp?catalogId=${entry.getId()}"><i class="icon-play"></i><%
    path.each { val ->
         %>${val}&nbsp;/&nbsp;<%
    } %>${entry.getName()}</a></li>
    <%
    def subPath = []
    subPath.addAll(path);
    subPath.add(entry.getName())
    entry.getChildren()?.each { child ->
        buildCatalog(subPath,child)
    }
}
%>
