/*
 * Mon Feb 22 14:55:04 SAST 2010
 * delete.groovy
 * @author admin
 */

package ControlPanel.mail.relay

import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.Resource
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.service.IPService
import com.rift.coad.request.wrapper.RequestWrapper


def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}
String domainName = request.getParameter("domain")

Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/inventory#Network> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.inventory.MailRelay\")}").execute();
for (SPARQLResultRow row : entries) {
    Resource domainResource = row.get(0).getResource()
    Network network = domainResource.get(Network.class)
    if (network.getAssociatedObject() != null) {
        continue
    }
    String currentDomain = network.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()
    if (domainName.equals(currentDomain)) {
        session.remove(network)
        RequestWrapper wrapper = new RequestWrapper("delete", network)
        wrapper.makeRequest(session)
        break
    }
}


entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/inventory#Network> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.inventory.MailRelay\")}").execute();
List<String> domains = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    Network network = row.get(0).cast(Network.class)
    if (network.getAssociatedObject() != null) {
        continue
    }
    domains.add(network.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue());
}


request.setAttribute("mail_relays", domains)


forward("view.gsp", request, response)