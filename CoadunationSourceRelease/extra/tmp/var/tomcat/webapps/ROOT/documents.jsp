<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<html>
    <head>
        
        <title>Coadunation</title>
        <link rel="stylesheet" href="style.css">
        <meta name="author" content="RiftIt"/>
        <meta name="copyright" content="Copyright (c) 2006 by RiftIt.co.za"/>
    </head>
    <body topmargin="0" leftmargin="0">
        <table valign="top" cellspacing="0" cellpadding="0" height="0">
            <tr>
                
                <td width="800" height="70" background="images/header.jpg">
                    <table width="100%" height="100%" cellpadding="2" cellspacing="0">
                        <tr>
                            <td align="right" valign="bottom">
                                <font color="#FFFFFF" face="Arial, Helvetica, sans-serif" size="1">Proudly sponsored by: <a href="http://www.riftit.co.za" style="text-decoration:none;color:#FFFFFF">RiftIT</a></font>
                            </td>
                        </tr>
                    </table>
                </td>	
            </tr>
            <tr>
                <td width="800" height="25" bgcolor="#000000" class="menu">
                    <table height="25" cellspacing="0" cellpadding="0">
                        <tr><!-- home -->
                            <td align="center"><table cellspacing="4" cellpadding="0"><tr><td><a href="/" class="menuLink">Home</a></td></tr></table></td>
                            <td width="1" align="center"><img src="images/menu_break.jpg"></td>
                            <!-- Tomcat Manager -->
                            <td align="center"><table cellspacing="4" cellpadding="0"><tr><td><a href="/manager/html" class="menuLink">Tomcat Admin</a></td></tr></table></td>
                            <td width="1" align="center"><img src="images/menu_break.jpg"></td>
                            <!-- Coadunation Manager -->
                            <td align="center"><table cellspacing="4" cellpadding="0"><tr><td><a href="/CoadunationAdmin" class="menuLink">Coadunation Admin</a></td></tr></table></td>
                            <td width="1" align="center"><img src="images/menu_break.jpg"></td>
                            <!-- Documentation -->
                            <td align="center" class='menu_red'><table cellspacing="4" cellpadding="0"><tr><td><a href="documents.jsp" class="menuLink">Documents</a></td></tr></table></td>
                            <td width="1" align="center"><img src="images/menu_break.jpg"></td>
                        </tr>
                        
                    </table>
                </td>
            </tr>
            <tr>
                <td width="800">
                    <table width="800" height="100%" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="border_ver"></td>
                            <td width="798">
                                <table cellspacing="0" cellpading="0" width="100%" height="100%">
                                    <tr>
                                        <td height="15" width="100%" valign="center">
                                            <font class="news_heading">&nbsp;&nbsp;Documents</font>
                                            
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="border_hor"></td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <table width="100%" cellspacing="5" cellpadding="0">
                                                <tr>
                                                    
                                                    <td>
                                                        <a href="http://www.coadunation.net/downloads.php" class="Linkt2">Online Install Guide</a>
                                                        <br><a href="http://www.coadunation.net/gettingstarted.php" class="Linkt2">Online Getting Started Guide</a>
                                                        <br><font class="news_heading">Samples</font>
                                                        <br><font class="text">The Coadunation distribution comes with sample programs. These can be found in the samples directory within the Coadunation distribution.</font>
                                                        <br><font class="news_heading">API</font>
                                                        <br><font class="text">The API documentation for Coadunation is distributed with it and can be found in the distributions documents directory.</font>
                                                        <br><font class="news_heading">Manuals</font>
                                                        <br><font class="text">This is manual covers getting started, setting up basic beans, setting up a distributed environment and the use of the supply daemons.</font>
                                                        <font class="text"><center><a href="http://www.coadunation.net/manuals/gettingstartedhtml/gettingStarted.html" class="Linkt2">HTML</a> | <a href="http://www.coadunation.net/manuals/gettingstartedhtml.zip" class="Linkt2">Download</a> | <a href="http://www.coadunation.net/manuals/gettingStarted.pdf" class="Linkt2">PDF</a></center></font>
                                                        
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    
                                </table>
                            </td>
                            <td class="border_ver"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
