package com.larinia.app; /**
 * Created by lyu on 15/06/16.
 */
//import org.jboss.security.JBossJSSESecurityDomain;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.List;

public class SecondServlet extends HttpServlet {
    private String message;
    KeyStore truststore;
    FileInputStream input;

   // private org.apache.log4j.Logger log4jlogger = org.apache.log4j.Logger.getLogger(this.getClass());

    //private Logger log = LoggerFactory.getLogger(this.getClass());
    public void init() throws ServletException {
        // Do required initialization
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();

        myMethod();

        String message = "I am here ";

        out.print(message);


        out.println("<h1> Test </h1>");
    }

    public void destroy() {
        // do nothing.
    }
    public void myMethod() {
        System.out.println("SecondServlet.myMethod: I am here XXXXXXXXXXXXXXXX hope it works");
    }

}


