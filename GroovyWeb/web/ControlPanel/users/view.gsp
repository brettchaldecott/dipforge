<!--
Date: Thu Feb 18 13:38:15 SAST 2010
File: view.gsp
Author: admin
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head style="padding=0px;">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control Panel</title>
        <script language="Javascript" type="text/javascript" src="../../javascript/common/common_functions.js"></script>
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
                                <h1 class="title"> Users</h1>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table width="100%" cellpadding=0 cellspacing=0>
            <tr>
                <td class="master-td" valign="middle" align="center">
                    <form method="post" action="delete.groovy" name="listForm">
                    <legend>Usernames</legend>
                    <%
                    if (request.getAttribute("delete-error") != null) {
                        %>
                        Unable to delete the user because<br>
                        <ul>
                        <li>${request.getAttribute("delete-error")}
                        </ul>
                        <%
                    }
                    %>
                    <table cellpadding=0 cellspacing=8>
                        <tr>
                            <td class="control-item" align="center">
                                <SELECT NAME="username" multiple SIZE=6 style="width:200px;">
                                <%
                                request.getAttribute("usernames").each { username ->
                                %>
                                    <OPTION VALUE="${username}">${username}
                                    <%
                                }
                                %>
                                </SELECT>
                                <br><br>
                                <input type="submit" value="Delete"/>
                            </td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
            <tr>
                <td class="master-td" valign="middle" align="center">
                    <%
                    String firstname = ""
                    if (request.getAttribute("firstname") != null) {
                        firstname = request.getAttribute("firstname")
                    }
                    String surname = ""
                    if (request.getAttribute("surname") != null) {
                        surname = request.getAttribute("surname")
                    }
                    String username = ""
                    if (request.getAttribute("username") != null) {
                        username = request.getAttribute("username")
                    }
                    String contact = ""
                    if (request.getAttribute("contact") != null) {
                        contact = request.getAttribute("contact")
                    }
                    String profile = ""
                    if (request.getAttribute("profile") != null) {
                        profile = request.getAttribute("profile")
                    }
                    %>
                    <form method="post" action="create.groovy">
                    <legend>Create User</legend>
                    <%
                    if (request.getAttribute("error") != null) {
                        %>
                        Unable to create because<br>
                        <ul>
                        <li>${request.getAttribute("error")}
                        </ul>
                        <%
                    }
                    %>
                    <table cellpadding=0 cellspacing=8 class="general-table">
                        <tr>
                            <td class="control-item">
                                Firstname
                            </td>
                            <td class="control-item">
                                <input type="text" name="firstname" style="width:200px;background:#cccccc;"
                                values="${firstname}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Surname
                            </td>
                            <td class="control-item">
                                <input type="text" name="surname" style="width:200px;background:#cccccc;"
                                values="${surname}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Username
                            </td>
                            <td class="control-item">
                                <input type="text" name="username" style="width:200px;background:#cccccc;"
                                value="${username}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Contact-Email
                            </td>
                            <td class="control-item">
                                <input type="text" name="contact" style="width:200px;background:#cccccc;"
                                value="${contact}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Password
                            </td>
                            <td class="control-item">
                                <input type="password" name="password" style="width:200px;background:#cccccc;"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Profile
                            </td>
                            <td class="control-item">
                                <SELECT NAME="profile">
                                    <option value="admin" ${profile.equals("admin") ? "SELECTED" : ""}>admin</option>
                                    <option value="technical" ${profile.equals("technical") ? "SELECTED" : ""}>technical</option>
                                    <option value="user" ${profile.equals("user") ? "SELECTED" : ""}>user</option>
                                    <option value="guest" ${profile.equals("guest") ? "SELECTED" : ""}>guest</option>
                                </SELECT>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item" align="center" colspan=2>
                                <input type="submit" value="Create" />
                            </td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>