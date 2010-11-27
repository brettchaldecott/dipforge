<!--
Date: Wed Feb 10 19:40:31 SAST 2010
File: view.gsp
Author: admin
-->



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head style="padding=0px;">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control Panel</title>

        <link rel="stylesheet" type="text/css" href="../css/controlpanel.css"/>
    </head>
    <body>
        <table width="100%" cellpadding=0 cellspacing=0 class="top-bar">
            <tr>
                <td>
                    <table cellpadding=0 cellspacing=0 border=0 class="top-bar-contents">
                        <tr>
                            <td>
                                <a href="../"><img src="../images/back.png" class="active-image-top-bar" border=0/></a>
                            </td>
                            <td>
                                <img src="../images/forwards.png" class="inactive-image-top-bar" border=0/>
                            </td>
                            <td>
                                <h1 class="title">Requests</h1>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        
        <%
        if (request.getAttribute("errors") != null) {
            %>
            <p>
            ${request.getAttribute("errors")}
            </p>
            <%
        }
        %>
        <br>
        <center>
        <table class="data-table" cellpadding=0 cellspacing=0 >
            <thead>
                <tr class="data-table-header-row">
                    <th class="data-table-cell-border">ID</th>
                    <th class="data-table-cell-border">Stats</th>
                    <th class="data-table-cell-border">Date</th>
                    <th class="data-table-cell-border">Description</th>
                </tr>
            </thead>
            <%
            boolean alterRow = false
            request.getAttribute("requests").each { changeRequest ->
                if (alterRow == true) {
                    alterRow = false
                    %>
                        <tr class="alternative-data-table-row">
                    <%
                } else {
                    alterRow = true
                    %>
                        <tr class="data-table-row">
                    <%
                }
                %>
                    <td class="data-table-cell-border">${changeRequest.getId()}</td>
                    <td class="data-table-cell-border">${changeRequest.getStatus()}</td>
                    <td class="data-table-cell-border">${changeRequest.getStart().toString()}</td>
                    <td class="data-table-cell-border">${changeRequest.getAction()}<br>
                    <%
                    if (changeRequest.getData() != null) {
                        %>
                        ${changeRequest.getData().getDataName()}<br>
                        ${changeRequest.getData().getObjId()}
                        <%
                    } else {
                        %>
                        Batch Request
                        <%
                    }
                    %>
                    </td>
                </tr>
                <%
            }
            %>
        </table>
        </center>
    </body>
</html>




