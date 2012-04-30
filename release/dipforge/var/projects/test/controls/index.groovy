import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;

def log = Logger.getLogger("test.index.groovy");

def test1 = RDF.create("http://dipforge.sourceforge.net/test1#type1")

test1.setId(RandomGuid.getInstance().getGuid())
test1.setProperty1("fred")
test1.setProperty2("bob")
log.info("##########################################")
log.info("ID value : " +test1.getId())
log.info("Property 1 value : " +test1.getProperty1())
log.info("Property 2 value : " +test1.getProperty2())
log.info("XML [" +test1.toXML())
log.info("##########################################")


def test2 = RDF.create("http://dipforge.sourceforge.net/test2#type2")

test2.setId(RandomGuid.getInstance().getGuid())
test2.setProperty3("fred")
test2.setProperty4("bob")
test2.getProperty5().setProperty1("man")
log.info("##########################################")
log.info("ID value : " +test2.getId())
log.info("Property 1 value : " +test2.getProperty3())
log.info("Property 2 value : " +test2.getProperty4())
log.info("Property 2 value : " +test2.getProperty5().getProperty1())
log.info("XML [" +test2.toXML())
log.info("##########################################")


log.info("################# retrieve from xml")
def test3 = RDF.getFromXML(test1.toXML(),"http://dipforge.sourceforge.net/test1#type1/" + test1.getId())
log.info("################# after retrieving from xml")

log.info("##########################################")
log.info("ID value : " +test3.getId())
log.info("Property 1 value : " +test3.getProperty1())
log.info("Property 2 value : " +test3.getProperty2())
log.info("XML [" +test3.toXML())
log.info("##########################################")

log.info("################# retrieve from xml")
def test4 = RDF.getFromXML(test2.toXML(),"http://dipforge.sourceforge.net/test2#type2/" + test2.getId())
log.info("################# after retrieving from xml")

log.info("##########################################")
log.info("ID value : " +test4.getId())
log.info("Property 1 value : " +test4.getProperty3())
log.info("Property 2 value : " +test4.getProperty4())
log.info("Property 2 value : " +test4.getProperty5().getProperty1())
log.info("XML [" +test4.toXML())
log.info("##########################################")

def results = RDF.queryXML(test2.toXML(),
    "SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/test2#type2> . }")

for (result in results) {
    test4 = result[0]
    log.info("##########################################")
    log.info("ID value : " +test4.getId())
    log.info("Property 1 value : " +test4.getProperty3())
    log.info("Property 2 value : " +test4.getProperty4())
    log.info("Property 2 value : " +test4.getProperty5().getProperty1())
    log.info("XML [" +test4.toXML())
    log.info("##########################################")
}


PageManager.forward("index.gsp", request, response)

