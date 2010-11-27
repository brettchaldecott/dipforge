<!--
Date: Mon Feb 22 07:16:23 SAST 2010
File: view.gsp
Author: admin
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head style="padding=0px;">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control Panel</title>
        <script language="Javascript" type="text/javascript" src="../../../javascript/common/common_functions.js"></script>
        <link rel="stylesheet" type="text/css" href="../../css/controlpanel.css"/>
    </head>
    <body>
        <table width="100%" cellpadding=0 cellspacing=0 class="top-bar">
            <tr>
                <td>
                    <table cellpadding=0 cellspacing=0 border=0 class="top-bar-contents">
                        <tr>
                            <td>
                                <a href="../../"><img src="../../images/back.png" class="active-image-top-bar" border=0/></a>
                            </td>
                            <td>
                                <img src="../../images/forwards.png" class="inactive-image-top-bar" border=0/>
                            </td>
                            <td>
                                <h1 class="title"> Mail Accounts</h1>
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
                    <legend>Accounts</legend>
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
                                <SELECT NAME="account" multiple SIZE=6 style="width:200px;">
                                <%
                                request.getAttribute("mail_account").each { account ->
                                %>
                                    <OPTION VALUE="${account}">${account}
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
                    String mailbox = ""
                    if (request.getAttribute("mailbox") != null) {
                        mailbox = request.getAttribute("mailbox")
                    }
                    String domain = ""
                    if (request.getAttribute("domain") != null) {
                        domain = request.getAttribute("domain")
                    }
                    %>
                    <form method="post" action="create.groovy">
                    <legend>Create Mailbox</legend>
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
                                Mailbox
                            </td>
                            <td class="control-item">
                                <input type="text" name="mailbox" style="width:200px;background:#cccccc;"
                                values="${mailbox}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Domain
                            </td>
                            <td class="control-item">
                                <SELECT NAME="domain">
                                    <%
                                    request.getAttribute("mail_domains").each { maildomain ->
                                        %>
                                        <OPTION VALUE="${maildomain}" ${maildomain==domain? "SELECTED" : ""}>${maildomain}
                                        <%
                                    }
                                    %>
                                </SELECT>
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
