/*
 * Thu Feb 18 13:34:59 SAST 2010
 * index.groovy
 * @author admin
 */

package ControlPanel.users

import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.rdf.objmapping.person.User



def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}

Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/person#User> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.person.User\")}").execute();
List<String> users = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    User user = row.get(0).cast(User.class)
    if (user.getAssociatedObject() != null) {
        continue
    }
    users.add(user.getUsername());
}

request.setAttribute("usernames", users)


forward("view.gsp", request, response)
