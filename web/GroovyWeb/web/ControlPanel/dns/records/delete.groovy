/*
 * Fri Feb 19 10:30:10 SAST 2010
 * delete.groovy
 * @author admin
 */

package ControlPanel.dns.records

import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.Resource
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.request.wrapper.RequestWrapper



def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}

String domainName = request.getParameter("domain")
String[] recordInfo = request.getParameter("recordId").split("[=]")
String recordId = request.getParameter("recordId")

Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/inventory#Network> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/inventory#NetworkId> ?NetworkId . " +
                    "FILTER ((?IdForDataType = \"com.rift.coad.rdf.objmapping.inventory.DNSDomain\") && " +
                    "(?NetworkId = \"${domainName}\"))}").execute();

boolean found = false
Network network = null
Resource domainResource
for (SPARQLResultRow row : entries) {
    domainResource = row.get(0).getResource()
    Network currentNetwork = row.get(0).cast(Network.class)
    if (currentNetwork.getAssociatedObject() != null) {
        continue
    }
    network = currentNetwork
    found = true;
    break
}

if (!found) {
    request.setAttribute("dns_domain", domainName)
    forward("unknown.gsp", request, response)
    return
}

// records
List<Network> records = new ArrayList<Network>();
records.add(network.getAttribute(com.rift.coad.rdf.objmapping.inventory.Network.class,"ns"))
records.add(network.getAttribute(com.rift.coad.rdf.objmapping.inventory.Network.class,"nsa"))


// persist
List<Resource> recordsResource = domainResource.listProperties("http://www.coadunation.net/schema/rdf/1.0/dns#Record")
found = false;
for (Resource recordResource : recordsResource) {
    Network dnsRecord = recordResource.get(com.rift.coad.rdf.objmapping.inventory.Network.class)
    String prefix = dnsRecord.getName()
    String suffix = dnsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"suffix").getValue()
    String type = dnsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"type").getValue()
    
    if (!found && (prefix.equals(recordInfo[0]) && type.equals(recordInfo[1]) && suffix.equals(recordInfo[2]))) {
        // make the request
        domainResource.removeProperty("http://www.coadunation.net/schema/rdf/1.0/dns#Record",recordResource)
        RequestWrapper wrapper = new RequestWrapper("delete", dnsRecord)
        wrapper.makeRequest(session);
        found = true;
    } else {
        records.add(recordResource.get(com.rift.coad.rdf.objmapping.inventory.Network.class))
    }
}


// setup the result
request.setAttribute("dns_domain", domainName)
request.setAttribute("domain",network)
request.setAttribute("records",records)
request.setAttribute("RecordId",recordId)
request.setAttribute("DelInfo",recordInfo[0] + "=" + recordInfo[1] + "=" + recordInfo[2])

forward("view.gsp", request, response)
