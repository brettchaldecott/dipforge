/*
 * public: Description
 * Copyright (C) Fri May 25 13:14:42 SAST 2012 owner 
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * NewsFeeds.groovy
 * @author admin
 */

package feeds

import groovy.json.*
import com.rift.coad.util.connection.ConnectionManager
import org.apache.log4j.Logger;
import com.rift.coad.daemon.event.FeedServer

def log = Logger.getLogger("files.FileList");
def feeds = []
try {
    def daemon = ConnectionManager.getInstance().getConnection(
        	FeedServer.class,"event/Server")
    def events = daemon.getEvents("news")
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

response.setContentType("application/json");

println builder.toString()
