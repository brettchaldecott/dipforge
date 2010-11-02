<!--
Date: Thu Feb 18 09:55:57 SAST 2010
File: unknown.gsp
Author: admin
-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head style="padding=0px;">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control Panel</title>
        <script language="Javascript" type="text/javascript" src="../../../javascript/common/common_functions.js"></script>
        <script language="Javascript" type="text/javascript" src="../../../javascript/common/gen_validatorv31.js"></script>
        <link rel="stylesheet" type="text/css" href="../../css/controlpanel.css"/>
    </head>
    <body>
        <table width="100%" cellpadding=0 cellspacing=0 class="top-bar">
            <tr>
                <td>
                    <table cellpadding=0 cellspacing=0 border=0 class="top-bar-contents">
                        <tr>
                            <td>
                                <a href="../"><img src="../../images/back.png" class="active-image-top-bar" border=0/></a>
                            </td>
                            <td>
                                <img src="../../images/forwards.png" class="inactive-image-top-bar" border=0/>
                            </td>
                            <td>
                                <h1 class="title"> Unknown DNS Domain</h1>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <center>The domain: ${request.getAttribute("dns_domain")} is unknown</center>
    </body>
</html>
