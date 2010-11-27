<!--
Date: Wed Feb 17 05:40:30 SAST 2010
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
                                <h1 class="title"> DNS Domain</h1>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table width="100%" cellpadding=0 cellspacing=0>
            <tr>
                <td class="master-td" valign="middle" align="center">
                    <form method="post" action="" name="listForm">
                    <legend>Domains</legend>
                    <table cellpadding=0 cellspacing=8>
                        <tr>
                            <td class="control-item" align="center">
                                <SELECT NAME="domain" multiple SIZE=6 style="width:200px;">
                                <%
                                request.getAttribute("dns_domains").each { domain ->
                                %>
                                    <OPTION VALUE="${domain}">${domain}
                                    <%
                                }
                                %>
                                </SELECT>
                                <br><br>
                                <input type="submit" value="Delete" onclick="javascript:changeActionSubmitform(document.listForm,'delete.groovy')" />
                                <input type="submit" value="Update" 
                                onclick="javascript:changeActionSubmitform(document.listForm,'records/index.groovy')"/>
                            </td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
            <tr>
                <td class="master-td" valign="middle" align="center">
                    <form method="post" action="create.groovy">
                    <input type="hidden" name="serial" style="width:200px;background:#cccccc;"
                                value="${(long)new java.util.Date().getTime() / 1000}"/>

                    <legend>Create Domains</legend>
                    <table cellpadding=0 cellspacing=8 class="general-table">
                        <tr>
                            <td class="control-item">
                                Domain
                            </td>
                            <td class="control-item">
                                <input type="text" name="domain" style="width:200px;background:#cccccc;"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Contact-Email
                            </td>
                            <td class="control-item">
                                <input type="text" name="contact" style="width:200px;background:#cccccc;"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Time To Live
                            </td>
                            <td class="control-item">
                                <input type="text" name="ttl" style="width:200px;background:#cccccc;" value="86400"/>
                            </td>
                        </tr><tr>
                            <td class="control-item">
                                Name Server IP
                            </td>
                            <td class="control-item">
                                <input type="text" name="ipaddress" style="width:200px;background:#cccccc;"
                                value="${java.net.InetAddress.getLocalHost().getHostAddress()}"/>
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