import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;

def log = Logger.getLogger("test.index.groovy");

def test1 = RDF.create("http://dipforge.sourceforge.net/test#type1")

test1.setProperty1("fred")
test1.setProperty2("bob")
log.info("##########################################")
log.info("Property 1 value : " +test1.getProperty1())
log.info("Property 2 value : " +test1.getProperty2())
log.info("Property 2 value : " +test1)
log.info("##########################################")


PageManager.forward("index.gsp", request, response)

