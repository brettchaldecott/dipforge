<%
import com.dipforge.semantic.RDF
import com.dipforge.request.RequestHandler
%>

<html>
<head>
<title>Test application for Dipforge</title>
</head>
<body>
</head>
<body>
    hello bob<br>
    <br>
    <b>The test request form</b><BR>
    <form method="post" action="testrequest.groovy" align="center">
    <table width="800px" cellpadding=0 cellspacing=0>
        <tr>
        <td>
        Name:<br>
        <input type="text" name="name" value="">
        </td>
        </tr>
        <tr>
        <td>
        Description:<br>
        <input type="text" name="description" value="">
        </td>
        </tr>
        <tr>
        <td>
        <input type="submit" name="submit form" value="submit test form"/>
        </td>
        </tr>
    </table>
    </form>
    <a href="list.groovy">list test result</a>
</boby>
</html>
