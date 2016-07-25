/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.daemon.dns.server;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import static com.googlecode.cqengine.codegen.AttributeBytecodeGenerator.createAttributes;
import com.googlecode.cqengine.query.parser.cqn.CQNParser;
import com.googlecode.cqengine.query.parser.sql.SQLParser;
import com.googlecode.cqengine.resultset.ResultSet;
import com.rift.coad.daemon.dns.DNSRequestInfo;
import java.net.DatagramPacket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.xbill.DNS.Message;
import org.xbill.DNS.Record;

/**
 *
 * @author brett chaldecott
 */
public class DNSStatusManager {
    
    private static long currentId = 0;
    
    public static synchronized long getNextId() {
        return currentId++;
    }
    

    
    // class singleton methods
    private static DNSStatusManager singleton = null;
    private static Logger log = Logger.getLogger(DNSStatusManager.class);
    
    // class private member variables
    private SQLParser<DNSRequestInfo> parser;
    private IndexedCollection<DNSRequestInfo> dnsRequestInfos;
    
    /**
     * The constructor of the stats manager.
     */
    private DNSStatusManager() {
        parser = SQLParser.forPojoWithAttributes(DNSRequestInfo.class, createAttributes(DNSRequestInfo.class));
                //SQLParser.forPojoWithAttributes(DNSRequestInfo.class, 
                //createAttributes(DNSRequestInfo.class));
        parser.registerAttribute(DNSRequestInfo.REQUEST_ID);
        parser.registerAttribute(DNSRequestInfo.SOURCE_IP);
        parser.registerAttribute(DNSRequestInfo.TIME);
        parser.registerAttribute(DNSRequestInfo.DNS_QUERY);
        parser.registerAttribute(DNSRequestInfo.TYPE);
        dnsRequestInfos = new ConcurrentIndexedCollection<DNSRequestInfo>();
    }
    
    
    /**
     * This method returns the instance of the dns status manager.
     * 
     * @return The reference to the status manager
     */
    public synchronized static DNSStatusManager getInstance() {
        if (singleton == null) {
            singleton = new DNSStatusManager();
        }
        return singleton;
    }
    
    
    /**
     * This method adds a new dns request to be queried.
     * 
     * @param request The request to be queried.
     */
    public void addDNSRequestInfo(DatagramPacket indp, Message query, Message response) {
        
        
        Record queryRecord = query.getQuestion();
        DNSRequestInfo dnsRequestInfo = new DNSRequestInfo(getNextId(), indp.getAddress().getHostAddress(),
                new Date(), queryRecord.getName().toString(), queryRecord.getType(), response.toString());
        dnsRequestInfos.add(dnsRequestInfo);
        log.info("Added a new request [" + dnsRequestInfo + "]");
    }
    
    /**
     * This method is called to query the 
     * 
     * @param query
     * @return 
     */
    public List<DNSRequestInfo> queryRequests(String query) {
        
                
        
        ResultSet<DNSRequestInfo> queryResults = parser.retrieve(dnsRequestInfos, query);
        
        List<DNSRequestInfo> result = new ArrayList<>();
        for (DNSRequestInfo requests : queryResults) {
            result.add(requests);
        }
        return result;
    }
}
