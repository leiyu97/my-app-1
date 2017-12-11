package org.larinia.client;



import org.jboss.logging.Logger;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class JmxListener {

    private static Logger logger = Logger.getLogger(JmxListener.class);

    private static final int USER_DATA_MAX_SIZE = 30;

    private MBeanServer server = ManagementFactory.getPlatformMBeanServer();

    private NotificationFilter filter = notification -> {
        return true;
    };

    private NotificationListener listener = (notification, handback) -> {
        String userData = notification.getUserData() != null ? notification.getUserData().toString() : null;
        if (userData != null && userData.length() > USER_DATA_MAX_SIZE) {
            userData = userData.substring(0, USER_DATA_MAX_SIZE) + "..."; // Truncate too long messages
        }
        logger.info("Received notification >>>>>>': type="+notification.getType()+
                " message="+notification.getMessage()+
                " userData="+ userData+
                " source="+notification.getSource()+
                " handback"+ handback);

        if (notification instanceof AttributeChangeNotification) {
            AttributeChangeNotification acn = (AttributeChangeNotification) notification;
            logger.info("atrribute is " + acn.getAttributeName());
            logger.info("old value is " + acn.getOldValue());
            logger.info("new value is " + acn.getNewValue());
        } else {
            System.out.println("Not Attribute Change Notification");
        }

    };

    private List<String> listenedNames = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        try {
            //listen("jboss.as:thread-pool=ejb-threadpool,subsystem=ejb3", "EJB thread pool"); // for VAD JBoss 6.4
            listen("jboss.as:deployment=my-app-1.war,subsystem=ejb3,stateless-session-bean=CallerBean", "EJB thread pool"); // for stock JBoss EAP 6.4.9

            listen("java.lang:type=GarbageCollector,name=PS Scavenge", "GC Sca");
            listen("java.lang:type=GarbageCollector,name=PS MarkSweep", "GC MS");

            //listen("jboss.as:deployment=risk-ds.xml,subsystem=datasources,xa-data-source=\"java:/jboss/datasource/riskAssessmentDS\"", "RiskDS 1");
            //listen("jboss.as.expr:deployment=risk-ds.xml,subsystem=datasources,xa-data-source=\"java:/jboss/datasource/riskAssessmentDS\"", "RiskDS 2");
            //listen("jboss.as:deployment=risk-ds.xml,subsystem=datasources,xa-data-source=\"java:/jboss/datasource/riskAssessmentDS\",statistics=jdbc", "RiskDS PrSt 1");
            //listen("jboss.as.expr:deployment=risk-ds.xml,subsystem=datasources,xa-data-source=\"java:/jboss/datasource/riskAssessmentDS\",statistics=jdbc", "RiskDS PrSt 2");
            //listen("jboss.as:deployment=risk-ds.xml,subsystem=datasources,xa-data-source=\"java:/jboss/datasource/riskAssessmentDS\",statistics=pool", "RiskDS Pool 1");
            //listen("jboss.as.expr:deployment=risk-ds.xml,subsystem=datasources,xa-data-source=\"java:/jboss/datasource/riskAssessmentDS\",statistics=pool", "RiskDS Pool 2");

            logger.info("Added JMX notification handlers");
        }
        catch (Exception e) {
            logger.error("Failed to add JMX notification handler", e);
        }
    }

    private void listen(String name, Object handback) throws MalformedObjectNameException, InstanceNotFoundException {
        server.addNotificationListener(ObjectName.getInstance(name), listener, filter, handback);
        listenedNames.add(name);
    }

    @PreDestroy
    private void preDestroy() {
        try {
            for (String name : listenedNames) {
                server.removeNotificationListener(ObjectName.getInstance(name), listener);
            }
        }
        catch (Exception e) {
            logger.error("Failed to remove JMX notification handler", e);
        }
    }
}
