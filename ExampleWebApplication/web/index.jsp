<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="javax.naming.Context"%>
<%@page import="com.rift.coad.daemon.messageservice.MessageService"%>
<%@page import="javax.rmi.PortableRemoteObject"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="com.rift.coad.lib.configuration.Configuration"%>
<%@page import="com.rift.coad.lib.configuration.ConfigurationFactory"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simple Coadunation Example</title>
    </head>
    <body>
        
        <h1>This is a test page</h1>
        <%
        // retrieve a list of named queues from the messsage service
        Context context = new InitialContext();
        MessageService messageService = (MessageService)PortableRemoteObject.narrow(context.lookup("message/MessageService"),MessageService.class);
        List list = messageService.listNamedQueues();
        
        // retrieve the database connection
        javax.sql.DataSource ds = (javax.sql.DataSource)context.lookup("java:comp/env/jdbc/hsqldb");
        
        // instanciate a test object
        com.rift.test.TestObject bob = new com.rift.test.TestObject();
        
        Configuration conf = ConfigurationFactory.getInstance().getConfig(com.rift.test.TestObject.class);
        
        
        %>
        <br>testing number of queues[<%=list.size()%>]
        <br>Db source[<%=ds.toString()%>]
        <br>Id[<%=conf.getString("instance_identifier")%>]
        <br><a href="InvalidateSession">invalidate session</a>
        
    </body>
</html>
