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
                
                <td width="800" height="70" background="images/titlebar.png">
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
                            <td align="center" class='menu_red'><table cellspacing="4" cellpadding="0"><tr><td><a href="/" class="menuLink">Home</a></td></tr></table></td>
                            <td width="1" align="center"><img src="images/menu_break.jpg"></td>
                            <!-- Web Mail -->
                            <td align="center"><table cellspacing="4" cellpadding="0"><tr><td><a href="/WebMail" class="menuLink">Webmail</a></td></tr></table></td>
                            <td width="1" align="center"><img src="images/menu_break.jpg"></td>
                            <!-- Tomcat Manager -->
                            <td align="center"><table cellspacing="4" cellpadding="0"><tr><td><a href="/manager/html" class="menuLink">Tomcat</a></td></tr></table></td>
                            <td width="1" align="center"><img src="images/menu_break.jpg"></td>
                            <!-- Coadunation Manager -->
                            <td align="center"><table cellspacing="4" cellpadding="0"><tr><td><a href="/CoadunationAdmin" class="menuLink">Coadunation Admin</a></td></tr></table></td>
                            <td width="1" align="center"><img src="images/menu_break.jpg"></td>

                            <!-- Documentation -->
                            <td align="center"><table cellspacing="4" cellpadding="0"><tr><td><a href="http://www.coadunation.net/content/documents" class="menuLink">Documents</a></td></tr></table></td>
                            <td width="1" align="center"><img src="images/menu_break.jpg"></td>
                        </tr>
                        
                    </table>
                </td>
            </tr>
            <tr>
                <td width="800">
                    <table width="800" cellspacing="10" cellpadding="0">
                        <tr>
                            <td>
                                <table cellspacing="0" cellpadding="0" height="250" width="240">
                                    
                                    <tr>
                                        <td class="border_hor"></td>
                                    </tr>
                                    <tr>
                                        <td width="240">
                                            <table width="240" height="100%" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td class="border_ver"></td>
                                                    <td width="238">
                                                        
                                                        <table cellspacing="0" cellpading="0" width="100%" height="100%">
                                                            <tr>
                                                                <td height="15" width="100%" valign="center">
                                                                    <font class="news_heading">&nbsp;&nbsp;Welcome To Coadunation</font>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="border_hor"></td>
                                                                
                                                            </tr>
                                                            <tr><td valign="top">
                                                                    <table width="100%" cellspacing="5" cellpadding="0">
                                                                        <tr>
                                                                            <td>
                                                                                <font class="text">
                                                                                    Coadunation is an Open Source Java Server.
                                                                                    It has been designed from the ground up to provide an
                                                                                    easy way to develop and deploy both web applications and daemons in a
                                                                                    seamless standardized environment. This makes it easier for
                                                                                    sysadmins, developers and end users.
                                                                                </font>
                                                                            </td>
                                                                            
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                <font class="news_heading">
                                                                                    Features
                                                                                </font>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            
                                                                            <td>
                                                                                <font class="text">
                                                                                    &nbsp;&nbsp;<img src="images/bullet.jpg"> RMI/IIOP
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Automated Deployment
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Pluggable Authentication and Authorization
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Cache Management
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Pluggable Centralized Configuration
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Distributed Environment
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Container Based Security
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Cosnaming Service
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Datasource Support
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Factory Object Support
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> JNDI
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> JMX Support
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Transaction Support
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> User Based Threading
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Web Services
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Audit Trail
                                                                                </font>
                                                                                
                                                                            <br><a href="http://www.coadunation.net/content/documents" class="Link3">More Documentation >></a></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                <font class="news_heading">
                                                                                    Daemons
                                                                                </font>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            
                                                                            <td>
                                                                                <font class="text">
                                                                                    &nbsp;&nbsp;<img src="images/bullet.jpg"> DNS Server
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> SMTP Server
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> IMAP Server
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> POP3 Server
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Fetchmail Server
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Application Server
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Message Server
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> HsqlDB Database
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Deployment Daemon
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Service Broker
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Timer Daemon (Cron)
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> Jython Daemon (Run Python scripts)
                                                                                </font>
                                                                                
                                                                            <br><a href="http://www.coadunation.net/content/projects" class="Link3">Projects >></a></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                <font class="news_heading">
                                                                                    Web Applications
                                                                                </font>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            
                                                                            <td>
                                                                                <font class="text">
                                                                                    &nbsp;&nbsp;<img src="images/bullet.jpg"> <a href="/CoadunationAdmin">Admin Console</a>
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> <a href="/manager/html">Tomcat Admin</a>
                                                                                    <br>&nbsp;&nbsp;<img src="images/bullet.jpg"> <a href="/WebMail">Webmail</a>
                                                                                </font>
                                                                                
                                                                            <br><a href="http://www.coadunation.net/content/projects" class="Link3">Projects >></a></td>
                                                                        </tr>
                                                                    </table>
                                                            </td></tr>
                                                        </table>
                                                    </td>
                                                    <td class="border_ver"></td>
                                                </tr>
                                                
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="border_hor"></td>
                                    </tr>
                                </table>
                            </td>
                            <td valign="top"><img src="images/about_image.jpg"></td>
                            
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
