/*
 * Mon Feb 22 07:14:24 SAST 2010
 * index.groovy
 * @author admin
 */

package ControlPanel.mail.accounts

import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.rdf.objmapping.service.IPService
import com.rift.coad.rdf.objmapping.inventory.Network



def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}

Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/service#IPService> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.service.MailAccount\")}").execute();
List<String> accounts = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    IPService account = row.get(0).cast(IPService.class)
    if (account.getAssociatedObject() != null) {
        continue
    }
    accounts.add(account.getId());
}

request.setAttribute("mail_account", accounts)

entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/inventory#Network> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.inventory.MailDomain\")}").execute();
List<String> domains = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    Network network = row.get(0).cast(Network.class)
    if (network.getAssociatedObject() != null) {
        continue
    }
    domains.add(network.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue());
}

request.setAttribute("mail_domains", domains)


forward("view.gsp", request, response)
