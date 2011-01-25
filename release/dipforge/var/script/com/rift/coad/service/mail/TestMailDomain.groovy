/*
 * Thu Feb 11 11:24:42 SAST 2010
 * MailDomain.groovy
 * @author admin
 */

package com.rift.coad.service.mail

import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.email.EMailServerMBean
import java.util.Date
import java.util.List


EMailServerMBean daemon = (EMailServerMBean)ConnectionManager.getInstance().
                    getConnection(EMailServerMBean.class,"email/MBeanManager");
                    
result = "successfull"
try {
    List domains = daemon.listDomains()
    boolean duplicate = false
    for (int index = 0; index < domains.size(); index++) {
        String domain = domains[index].toString()
        if (domain.equals("testinginternal.com")) {
            duplicate = true
            break;
        }
    }
    if (!duplicate) {
        daemon.addDomain("testinginternal.com")
        result = "successfull"
    } else {
        result = "duplicate"
    }
    
} catch (Exception ex) {
    result = ex.getMessage()
}
print result
output = result

