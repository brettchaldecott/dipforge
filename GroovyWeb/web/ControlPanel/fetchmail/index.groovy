/*
 * Tue Feb 23 04:26:58 SAST 2010
 * index.groovy
 * @author admin
 */

package ControlPanel.fetchmail

// imports
import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.rdf.objmapping.service.IPService



def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}

Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/service#IPService> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.service.FetchMailAccount\")}").execute();
List<String> accounts = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    IPService account = row.get(0).cast(IPService.class)
    if (account.getAssociatedObject() != null) {
        continue
    }
    accounts.add(account.getAttribute(com.rift.coad.rdf.objmapping.base.Email.class,"email").getValue());
}

request.setAttribute("fetchmail_accounts", accounts)


forward("view.gsp", request, response)
