<!--
Date: Wed May 16 06:13:32 SAST 2012
File: list.gsp
Author: brett chaldecott
-->

<%
import com.dipforge.semantic.RDF
%>


<html>
<head>
<title>list test</title>
</head>
<boby>
<h>Test data</h>
<table cellpadding=0 cellspacing=0 width="100%">
    <tr>
        <td>
            id
        </td>
        <td>
            property1
        </td>
        <td>
            property2
        </td>
    </tr>
<%
    params.tests.each { testdata ->
        def data = testdata["s"]
    %>
    <tr>
        <td>
            ${data.getId()}
        </td>
        <td>
            ${data.property1}
        </td>
        <td>
            <% print(data.getProperty2()) %>
        </td>
    </tr>
    <%}
%>
</table>
<a href="index.groovy">back</a>
</boby>
</html>