<!--
Date: Mon Feb 08 14:51:06 SAST 2010
File: index.gsp
Author: admin

This is a simple control panel and an example of the COSS product suite. We do not advise the use of
this system in production environments.
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head style="padding=0px;">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control Panel</title>

        <link rel="stylesheet" type="text/css" href="css/controlpanel.css"/>
    </head>
    <body>
        <table width="100%" cellpadding=0 cellspacing=0 class="top-bar">
            <tr>
                <td>
                    <table cellpadding=0 cellspacing=0 border=0 class="top-bar-contents">
                        <tr>
                            <td>
                                <img src="images/back.png" class="inactive-image-top-bar"/>
                            </td>
                            <td>
                                <img src="images/forwards.png" class="inactive-image-top-bar"/>
                            </td>
                            <td>
                                <h1 class="title"> Control Panel</h1>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table width="100%" cellpadding=0 cellspacing=0>
            <tr class="alternate-table-row">
                <td class="master-td" valign="middle">
                    <h1 class="header">Administration</h1>
                    <table cellpadding=0 cellspacing=8>
                        <tr>
                            <td class="control-item" align="center">
                                <a href="requests/index.groovy">
                                    <img src="images/requests.png" border=0/><br>
                                    Requests
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            
            <tr class="table-row">
                <td class="master-td" valign="middle">
                    <h1 class="header">Domain</h1>
                    <table cellpadding=0 cellspacing=8>
                        <tr>
                            <td class="control-item" align="center">
                                <a href="mail/index.groovy">
                                    <img src="images/internet-mail.png" border=0/><br>
                                    Mail
                                </a>
                            </td>
                            <td class="control-item" align="center">
                                <a href="mail/relay/index.groovy">
                                    <img src="images/relay.png" border=0/><br>
                                    Relay
                                </a>
                            </td>
                            <td class="control-item" align="center">
                                <a href="dns/index.groovy">
                                    <img src="images/internet-web-browser.png" border=0/><br>
                                    DNS
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            
            <tr class="alternate-table-row">
                <td class="master-td" valign="middle">
                    <h1 class="header">Accounts</h1>
                    <table cellpadding=0 cellspacing=8>
                        <tr>
                            <td class="control-item" align="center">
                                <a href="users/index.groovy">
                                    <img src="images/system-users.png" border=0/><br>
                                    Users
                                </a>
                            </td>
                            <td class="control-item" align="center">
                                <a href="mail/accounts/index.groovy">
                                    <img src="images/internet-mail.png" border=0/><br>
                                    Mail
                                </a>
                            </td>
                            <td class="control-item" align="center">
                                <a href="fetchmail/index.groovy">
                                    <img src="images/fetch-mail.png" border=0/><br>
                                    Fetch Mail
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr class="table-row">
                <td class="master-td" valign="middle"></td>
            </tr>
            <tr class="alternate-table-row">
                <td class="master-td" valign="middle"></td>
            </tr>
            <tr class="table-row">
                <td class="master-td" valign="middle"></td>
            </tr>
        </table>
    </body>
</html>
