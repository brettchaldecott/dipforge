package applications

import groovy.json.*
import com.rift.coad.util.connection.ConnectionManager
import org.apache.log4j.Logger;
import com.rift.coad.daemon.event.FeedServer

def log = Logger.getLogger("feeds.FeedEvents");
def feeds = []
try {
    def daemon = ConnectionManager.getInstance().getConnection(
    		FeedServer.class,"event/Server")
    def events = daemon.getEvents("environment")
    for (def event : events) {
        feeds.add([
                    image: 'favicon.ico',
                    title: event.getName(),
                    author: event.getApplication(),
                    msg: event.getDescription(),
                    url: event.getUrl()
                ])
    }
    events = daemon.getEvents("news")
    log.info("The feed events are : " + events)
    for (def event : events) {
        feeds.add([
                    image: 'favicon.ico',
                    title: event.getName(),
                    author: event.getApplication(),
                    msg: event.getDescription(),
                    url: event.getUrl()
                ])
    }
} catch (Exception ex) {
    log.error("Failed to retrieve the feed information : " + ex.getMessage(),ex)
}
def builder = new JsonBuilder()
builder(feeds)

println builder.toString()