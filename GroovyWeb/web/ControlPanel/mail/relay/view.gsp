<!--
Date: Mon Feb 22 14:52:00 SAST 2010
File: view.gsp
Author: admin
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head style="padding=0px;">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control Panel</title>

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
                                <h1 class="title"> Mail Relays</h1>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table width="100%" cellpadding=0 cellspacing=0>
            <tr>
                <td class="master-td" valign="middle" align="center">
                    <form method="post" action="delete.groovy">
                    <legend>Relays</legend>
                    <table cellpadding=0 cellspacing=8>
                        <tr>
                            <td class="control-item" align="center">
                                <SELECT NAME="domain" multiple SIZE=6 style="width:200px;">
                                <%
                                request.getAttribute("mail_relays").each { domain ->
                                %>
                                    <OPTION VALUE="${domain}">${domain}
                                    <%
                                }
                                %>
                                </SELECT>
                                <br><br>
                                <input type="submit" value="Delete" />
                            </td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
            <tr>
                <td class="master-td" valign="middle" align="center">
                    <form method="post" action="create.groovy">
                    <legend>Create Relay</legend>
                    <table cellpadding=0 cellspacing=8>
                        <tr>
                            <td class="control-item" align="center">
                                
                                <input type="text" name="domain" style="width:200px;background:#cccccc;"
                                value="${java.net.InetAddress.getLocalHost().getHostName()}"/>
                                <br><br>
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