<!--
Date: Tue Jun 26 19:46:11 SAST 2012
File: installed.gsp
Author: brett chaldecott
-->


<div class="span9">
    <div class="hero-unit">
    <legend>Installed Packages</legend>
        <table class="table">
        <thead>
          <tr>
            <th>&nbsp;</th>
            <th>Name</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody id="offerings">
        <%
        params.installed.each { orgOfferingList ->
            def id = orgOfferingList[0].getId()
            def offering = orgOfferingList[0].getOffering()
            %>
          <tr id="tr${id}">
            <td><a href="javascript:showRemoveOffering('${id}');"><i class="icon-minus-sign"></i></a></td>
            <td>${offering.getName()}</td>
            <td>${offering.getDescription()}</td>
          </tr>
        <% } %>
        </tbody>
      </table>
    </div>
</div>

    
    
    