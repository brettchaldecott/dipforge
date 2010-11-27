<!--
Date: Thu Feb 18 09:58:56 SAST 2010
File: view.gsp
Author: admin
-->

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
                                <h1 class="title"> DNS Domain Records ${request.getAttribute("dns_domain")}</h1>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <br>
        <table width="100%" cellpadding=0 cellspacing=0>
        <tr>
        <td class="master-td" valign="middle" align="center">
        <form method="post" action="delete.groovy" name="records">
        <legend>Records<legend>
        <input type="hidden" name="domain" value="${request.getAttribute("dns_domain")}"/>
        <br>
        <table class="data-table" cellpadding=0 cellspacing=0 >
            <thead>
                <tr class="data-table-header-row">
                    <th class="data-table-cell-border">Select</th>
                    <th class="data-table-cell-border">Prefix</th>
                    <th class="data-table-cell-border">TTL</th>
                    <th class="data-table-cell-border">TYPE</th>
                    <th class="data-table-cell-border">Suffix</th>
                </tr>
            </thead>
            <tr class="data-table-row">
                <td class="data-table-cell-border"></td>
                <td class="data-table-cell-border">${request.getAttribute("dns_domain")}</td>
                <td class="data-table-cell-border">
                ${request.getAttribute("domain").getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"ttl")}</td>
                <td class="data-table-cell-border">SOA</td>
                <td class="data-table-cell-border"></td>
            </tr>
            <%
            boolean alterRow = true
            int count = 0;
            String radioValue = ""
            request.getAttribute("records").each { record ->
                String recordId = record.getId()
                String type = record.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"type").getValue()
                String suffix = record.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"suffix").getValue()
                long ttl = record.getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"ttl").getValue()
                if (count >= 2) {
                    radioValue = "<INPUT TYPE=RADIO NAME=\"recordId\" VALUE=\"${recordId}=${type}=${suffix}\">"
                }
                if (alterRow == true) {
                    alterRow = false
                    %>
                        <tr class="alternative-data-table-row">
                    <%
                } else {
                    alterRow = true
                    %>
                        <tr class="data-table-row">
                    <%
                }
                %>
                    <td class="data-table-cell-border">${radioValue}</td>
                    <td class="data-table-cell-border">${recordId}</td>
                    <td class="data-table-cell-border">${ttl}</td>
                    <td class="data-table-cell-border">${type}</td>
                    <td class="data-table-cell-border">${suffix}</td>
                    </td>
                </tr>
                <%
                count++
            }
            %>
        </table>
        <br>
        <input type="submit" value="Delete"/>
        <br>
        </form>
        </td>
        </tr>
        <tr>
            <td class="master-td" valign="middle" align="center">
                <br>
                <form method="post" action="create.groovy" name="records">
                <input type="hidden" name="domain" value="${request.getAttribute("dns_domain")}"/>
                <legend>Add Record<legend>
                    <table cellpadding=0 cellspacing=8 class="general-table">
                        <tr>
                            <td class="control-item">
                                Prefix
                            </td>
                            <td class="control-item">
                                <input type="text" name="prefix" style="width:200px;background:#cccccc;"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Time To Live
                            </td>
                            <td class="control-item">
                                <input type="text" name="ttl" style="width:200px;background:#cccccc;" value="86400"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Type
                            </td>
                            <td class="control-item">
                                <select name="type">
                                    <option value="A">A</option>
                                    <option value="NS">NS</option>
                                    <option value="CNAME">CNAME [Hostname Only]</option>
                                    <option value="TXT">TXT</option>
                                    <option value="PTR">PTR [Reverse Zone Only]</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="control-item">
                                Suffix
                            </td>
                            <td class="control-item">
                                <input type="text" name="suffix" style="width:200px;background:#cccccc;"/>
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