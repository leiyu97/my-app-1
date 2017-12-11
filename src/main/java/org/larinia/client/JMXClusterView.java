package org.larinia.client;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.StringTokenizer;

public class JMXClusterView {
    public static void main(String[] args) throws Exception {

        String port = "9999"; // "10090"
        //String urlString = "service:jmx:remote+http://totoro.usersys.redhat.com:" + port;
        String urlString ="service:jmx:remoting-jmx://totoro.usersys.redhat.com:"  + port;
                System.out.println("  \n\n\t****  urlString: " + urlString);

        JMXServiceURL serviceURL = new JMXServiceURL(urlString);
        JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL, null);
        MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
        // String jgroupsString="jgroups:type=channel,cluster=\"ee\"";
      //  String jgroupsString="jgroups:type=protocol,cluster=\"ee\",protocol=FD_ALL";
      //  ObjectName objectName = new ObjectName(jgroupsString);
       // String clusterView = (String) connection.getAttribute(objectName, "view");
      //  String clusterView = (String) connection.getAttribute(objectName, "members");

        String threadPool="jboss.as:subsystem=ejb3,thread-pool=default";
         ObjectName on = new ObjectName(threadPool);
         String ejb3String = connection.getAttribute(on,"currentThreadCount").toString();

       //         Long receivedMessages = (Long) connection.getAttribute(objectName, "received_messages");
       // String name = (String) connection.getAttribute(objectName, "name");
       // String clusterName = (String) connection.getAttribute(objectName, "cluster_name");
      //  System.out.println(" ######################### clusterView       = " + clusterView);
        System.out.println("ejbsstring*************" + ejb3String);
       // System.out.println(" receivedMessages  = " + receivedMessages);
       // System.out.println(" name              = " + name);
       // System.out.println(" clusterName       = " + clusterName);
        jmxConnector.close();

        int i = 0;

    /*    StringTokenizer st2 = new StringTokenizer(clusterView, ",");

        while (st2.hasMoreElements()) {
            i++;
            st2.nextElement();
        }

        System.out.println("Total number of nodes in cluster = " + i);*/
    }
}
