package org.larinia.client;


import com.larinia.ejb.CallerLocal;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by lyu on 12/08/16.
 */

public class EJBTesterThread extends Thread {

    BufferedReader brConsoleReader = null;
    Properties props;
    InitialContext ctx;
/*
    {
        props = new Properties();
        try {
            props.load(new FileInputStream("jndi.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            ctx = new InitialContext(props);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        brConsoleReader =
                new BufferedReader(new InputStreamReader(System.in));
    }*/

    public static void main(String[] args) {

        for (int i=0 ; i<100; i++) {
            System.out.println("EJBTesterThread.main: running "+i+"times");

            EJBTesterThread ejbTesterThread = new EJBTesterThread();
            ejbTesterThread.start();
        }

    }


    public void run() {
        //  ejbTester.testInterceptedEjb();
        try {
            //  testRemoteNamingEAP7();
            //testRemoteNaming();
            testEJBClient();
            Thread.sleep(3000);

            // test methods from quick start
            //  invokeMyBean();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testInterceptedEjb() {

        try {

            String jndiString = "java:global/my-app-1/CallerBean!org.larinia.ejb.CallerLocal";

            CallerLocal callerLocal = (CallerLocal) ctx.lookup(jndiString);

            callerLocal.testMethod("Lei testing Interceptor");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (brConsoleReader != null) {
                    brConsoleReader.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    private static void testEJBClient() throws Exception {
        System.out.println("*** EJB Client API IN ***");

        // Hashtable<String, String> env = new Hashtable<String, String>();

        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        // create the context
        Context ctx = new InitialContext(jndiProperties);


        //  String lookupoString="ejb:/TestWar-1//AntherEJBImpl!"+AnotherEJB.class.getName();
        //  String lookupoString = "ejb:/TestWar-1//AntherEJBImpl!" + AnotherEJB.class.getName();


        //java:app/TestWar-1/AntherEJBImpl!org.larinia.ejb.AnotherEJB
        //  AnotherEJB aejb = (AnotherEJB) ctx.lookup(lookupoString);

        //System.out.println(aeb.testMyMethod());

        //    aejb.testMyMethod();

        //try callerbean

        String lookupCallString = "ejb:/my-app-1/CallerBean!" + CallerLocal.class.getName();

        CallerLocal callerLocal = (CallerLocal) ctx.lookup(lookupCallString);

        String resturnString = callerLocal.testMethod("Testing Caller Bean from the client side");
        System.out.println("response from callerbean is " + resturnString);

        System.out.println("*** EJB Client API Out ***");
    }

    private static void testRemoteNaming() throws Exception {
        System.out.println("*** Remote Naming API ***");
        Hashtable<String, String> env = new Hashtable<String, String>();

        env.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
        env.put("java.naming.provider.url", "remote://totoro.usersys.redhat.com:4447");
        // env.put("java.naming.provider.url", "https-remoting://totoro.usersys.redhat.com:8443");
        env.put("jboss.naming.client.ejb.context", "true");
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
        env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");

        // FIXME:  SSL related config parameters
        env.put("jboss.naming.client.remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "true");
        env.put("jboss.naming.client.connect.options.org.xnio.Options.SSL_STARTTLS", "true");
        // env.put("remote.connection.default.protocol","https-remoting");
        env.put("remote.connection.default.protocol", "remoting");
        env.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
//        env.put("remote.connections","default");
//        env.put("remote.connection.default.host","totoro.usersys.redhat.com");
//        env.put("remote.connection.default.port","8443");

        //env.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS","JBOSS-LOCAL-USER");
        // env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS","JBOSS-LOCAL-USER");


        env.put(Context.SECURITY_PRINCIPAL, "admin");
        env.put(Context.SECURITY_CREDENTIALS, "Admin123$");

        InitialContext ctx = new InitialContext(env);

    /*    NamingEnumeration<Binding> binding_list = ctx.listBindings("TestWar-1");

        while (binding_list.hasMore()) {
            Binding binding = (Binding) binding_list.next();
            Object obj1 = binding.getObject();
            System.out.println("read obj1 = " + obj1);
        }*/

        //       System.out.println("binding_list:"+binding_list);

        String lookupoString = "TestWar-1/CallerBean!org.larinia.ejb.CallerLocal";

        //java:global/TestWar-1/CallerBean!org.larinia.ejb.CallerLocal

        Object obj = ctx.lookup(lookupoString);
        System.out.println("EJBTester.testRemoteNaming: after look up");

        CallerLocal ejbObject = (CallerLocal) obj;

        String resultString = ejbObject.testMethod("Testing custom loggin module");

        System.out.println(resultString);

        System.out.println("*** end Remote Naming API ***");
    }

    // This method works with eap 7, the bean itself have annotations such as allowedroles
    private static void testRemoteNamingEAP7() throws Exception {
        System.out.println("*** Remote Naming API In ***");
        Hashtable<String, String> env = new Hashtable<String, String>();

        env.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
        env.put("java.naming.provider.url", "http-remoting://totoro.usersys.redhat.com:8080");
        env.put("jboss.naming.client.ejb.context", "true");
        //env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        //# The following setting is required when deferring to JAAS, must be false
        env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
        env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");

        // FIXME:  SSL related config parameters
        env.put("jboss.naming.client.remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        env.put("jboss.naming.client.connect.options.org.xnio.Options.SSL_STARTTLS", "false");

        env.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
        env.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");


        env.put("remote.cluster.ejb.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");

        env.put("remote.cluster.ejb.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");

        env.put(Context.SECURITY_PRINCIPAL, "admin");
        env.put(Context.SECURITY_CREDENTIALS, "Admin123$");

        InitialContext ctx = new InitialContext(env);

       /* NamingEnumeration<Binding> binding_list = ctx.listBindings("SimpleEAR_EJB3");

        while (binding_list.hasMore()) {
            Binding binding = (Binding) binding_list.next();
            Object obj1 = binding.getObject();
            System.out.println("read obj1 = " + obj1);
        }

        System.out.println("binding_list:" + binding_list);*/

        //java:global/my-app-1/CallerBean!com.larinia.ejb.CallerLocal


        String lookupoString = "my-app-1/CallerBean!org.larinia.ejb.CallerLocal";


        Object obj = ctx.lookup(lookupoString);

        CallerLocal ejbObject = (CallerLocal) obj;
        System.out.println(ejbObject.testMethod("Testing SSL ejb call"));

        System.out.println("*** Remote Naming API Out***");
    }

    // from quick start


}