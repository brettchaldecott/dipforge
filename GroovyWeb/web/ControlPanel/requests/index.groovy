/*
 * Wed Feb 10 19:38:44 SAST 2010
 * index.groovy
 * @author admin
 */

package ControlPanel.requests

import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.change.rdf.objmapping.change.Request
import com.rift.coad.request.wrapper.RequestWrapper
import com.rift.coad.request.wrapper.RequestBrokerConnector


def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}

int limit = 14
if (request.getParameter("limit") != null) {
    limit = Integer.parseInt(request.getParameter("limit"))
}

int offset = 0
if (request.getParameter("offset") != null) {
    limit = Integer.parseInt(request.getParameter("offset"))
}


Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/change#RequestDefinition> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/change#RequestStart> ?RequestStart . }" +
                    "ORDER BY DESC (?RequestStart) LIMIT ${limit}").execute();

List<Request> requests = new ArrayList<Request>();
String errors = ""
RequestBrokerConnector connector = new RequestBrokerConnector()
for (SPARQLResultRow row : entries) {
    Request request = row.get(0).cast(Request.class)
    try {
        request = connector.getRequest(request)
    } catch (Exception ex) {
        errors += ex.getMessage()
    }
    requests.add(request)
}

request.setAttribute("requests", requests)
if (errors.length() != 0) {
    request.setAttribute("errors", errors)
}

forward("view.gsp", request, response)


