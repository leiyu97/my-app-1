/*
    To the extent possible under law, Red Hat, Inc. has dedicated all copyright to this software to the public domain worldwide, pursuant to the CC0 Public Domain Dedication. This software is distributed without any warranty.  See <http://creativecommons.org/publicdomain/zero/1.0/>.
*/
package org.larinia.client;

import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.MBeanServer;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;

/**
 * @author bmaxwell
 */
@Singleton
@Startup
public class MBeanNotificationListener implements NotificationListener { 
    
    private NotificationFilter filterAllButDatasources = new NotificationFilter() {
    	public boolean isNotificationEnabled(Notification notification) {    		
    		if(! (notification instanceof MBeanServerNotification))
    			return false;    		
            MBeanServerNotification mbsn = (MBeanServerNotification) notification;
            if(mbsn.getMBeanName().getDomain().equals("jboss.as")) {
            	if(mbsn.getMBeanName().getKeyProperty("ejb3") != null)
            		return true;
            }
            return false;                		
    	};
    };

    @PostConstruct
    public void start() {
    	ObjectName name;
        MBeanServer platformMBeanServer;
        try {
        	System.out.println("************ MBeanNotificationListener started ***************");
            platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            name = new ObjectName("JMImplementation:type=MBeanServerDelegate");
            NotificationListener listener = this;            
            Object handback = null; // The context to be sent to the listener when a notification is emitted.
            platformMBeanServer.addNotificationListener(name, listener, filterAllButDatasources, handback);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

	@Override
	public void handleNotification(Notification notification, Object handback) {
        MBeanServerNotification mbsn = (MBeanServerNotification) notification;       
        if (mbsn.getType().equals(MBeanServerNotification.REGISTRATION_NOTIFICATION)) {
        	// This is the point where a new datasource mbean has been created
        	System.out.println("Registered datasource MBean: " + mbsn.getMBeanName());
        } else if (mbsn.getType().equals(MBeanServerNotification.UNREGISTRATION_NOTIFICATION)) {
        	// This is the point where a datasource mbean has been removed
        	System.out.println("UnRegistered datasource MBean: " + mbsn.getMBeanName());
        } else {
        	System.out.println("Unknown event for datasource MBean: " + mbsn.getMBeanName());
        }      
	}
}
