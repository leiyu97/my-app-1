/*
    To the extent possible under law, Red Hat, Inc. has dedicated all copyright to this software to the public domain worldwide, pursuant to the CC0 Public Domain Dedication. This software is distributed without any warranty.  See <http://creativecommons.org/publicdomain/zero/1.0/>.
*/
package org.larinia.client;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author bmaxwell
 */
@Singleton
@Startup
public class MBeanLeiNotificationListener implements NotificationListener {
    
    private NotificationFilter filterAllButACN = new NotificationFilter() {
    	public boolean isNotificationEnabled(Notification notification) {    		
    		if(! (notification instanceof AttributeChangeNotification))
                return false;

    		//MBeanServerNotification mbsn = (MBeanServerNotification) notification;



            return true;
    	};
    };

    @PostConstruct
    public void start() {
    	ObjectName name;
        MBeanServer platformMBeanServer;
        try {
        	System.out.println(">>>>>>> MBeanNotificationLeiListener started >>>>>>>");
            platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
           // name = new ObjectName("JMImplementation:type=MBeanServerDelegate");
            name = new ObjectName("jboss.as:subsystem=ejb3,thread-pool=default");
            NotificationListener listener = this;            
            Object handback = null; // The context to be sent to the listener when a notification is emitted.
            platformMBeanServer.addNotificationListener(name, listener, filterAllButACN, handback);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

	@Override
	public void handleNotification(Notification notification, Object handback) {
        AttributeChangeNotification acn = (AttributeChangeNotification) notification;
        echo("\tAttributeName: " + acn.getAttributeName());
        echo("\tAttributeType: " + acn.getAttributeType());
        echo("\tNewValue: " + acn.getNewValue());
        echo("\tOldValue: " + acn.getOldValue());
	}

    private static void echo(String msg) {
        System.out.println(msg);
    }
}
