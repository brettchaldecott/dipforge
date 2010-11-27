/*
 * Tue Feb 23 04:38:25 SAST 2010
 * create.groovy
 * @author admin
 */

package ControlPanel.fetchmail

import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.Resource
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.request.wrapper.RequestWrapper
import com.rift.coad.lib.common.RandomGuid
import com.rift.coad.rdf.objmapping.base.password.ClearTextPassword
import com.rift.coad.rdf.objmapping.service.IPService
import com.rift.coad.rdf.objmapping.inventory.Network


def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}


String mailbox = request.getParameter("mailbox")
String server = request.getParameter("server")
String account = request.getParameter("account")
String password = request.getParameter("password")


Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

IPService fetchMailAccount = session.getType(IPService.class, "com.rift.coad.rdf.objmapping.service.FetchMailAccount")

fetchMailAccount.setId(mailbox)
fetchMailAccount.setHostname(server)
fetchMailAccount.setName(account)
fetchMailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.Email.class,"email").setValue(mailbox)
fetchMailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.password.ClearTextPassword,"password").
    setId(RandomGuid.getInstance().getGuid())
fetchMailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.password.ClearTextPassword,"password").
    setValue(password)

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/service#IPService> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/service#IPID> ?IPID . " +
                    "FILTER ((?IdForDataType = \"com.rift.coad.rdf.objmapping.service.FetchMailAccount\") && " + 
                    "(?IPID = \"${mailbox}\"))}").execute();

boolean found = false
for (SPARQLResultRow row : entries) {
    IPService ipService = row.get(0).cast(IPService.class)
    if (ipService.getAssociatedObject() != null) {
        continue
    }
    found = true;
    break
}

if (found) {
    // deal with duplicate error
    request.setAttribute("error","Duplicate Mailbox")
    request.setAttribute("mailbox",mailbox)
    request.setAttribute("server",server)
    request.setAttribute("account",account)
    request.setAttribute("password",password)
} else {
    // deal with account
    session.persist(fetchMailAccount)
    RequestWrapper wrapper = new RequestWrapper("create", fetchMailAccount)
    wrapper.makeRequest(session)
}


entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/service#IPService> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.service.FetchMailAccount\")}").execute();
List<String> accounts = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    IPService ipService = row.get(0).cast(IPService.class)
    if (ipService.getAssociatedObject() != null) {
        continue
    }
    accounts.add(ipService.getAttribute(com.rift.coad.rdf.objmapping.base.Email.class,"email").getValue());
}

request.setAttribute("fetchmail_accounts", accounts)


forward("view.gsp", request, response)