/*
 * Thu Feb 18 07:17:22 SAST 2010
 * index.groovy
 * @author admin
 */

package ControlPanel.dns.records

// imports
import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.Resource
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.rdf.objmapping.inventory.Network



def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}

Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

String domainName = request.getParameter("domain")

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

// walk the extra resource
List<Resource> recordsResource = domainResource.listProperties("http://www.coadunation.net/schema/rdf/1.0/dns#Record")
for (Resource recordResource : recordsResource) {
    records.add(recordResource.get(com.rift.coad.rdf.objmapping.inventory.Network.class))
}


// set the results
request.setAttribute("dns_domain", domainName)
request.setAttribute("domain",network)
request.setAttribute("records",records)
forward("view.gsp", request, response)
