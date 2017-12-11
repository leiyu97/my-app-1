package com.larinia.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.larinia.client.TomcatLogging;

@WebListener
public class AppContextListener implements ServletContextListener {


    private static final Logger logger = Logger.getLogger(AppContextListener.class);
    TomcatLogging tcl;

    //@Override
    public void contextInitialized(ServletContextEvent evt) {
        ServletContext ctx = evt.getServletContext();
        System.out.println("###### contextInitialized(): ServerInfo: " +
                ctx.getServerInfo() + "########### " + System.currentTimeMillis());
        System.out.println("contextInitialized(): ContextPath: " +
                ctx.getContextPath() + " " + System.currentTimeMillis());
        logger.info("contextInitialized(): ServerInfo: " +
                ctx.getServerInfo() + " " + System.currentTimeMillis());
        logger.info("contextInitialized(): ContextPath: " +
                ctx.getContextPath() + " " + System.currentTimeMillis());
        tcl = new TomcatLogging();
        tcl.start();
    }

    //@Override
    public void contextDestroyed(ServletContextEvent evt) {
        ServletContext ctx = evt.getServletContext();
        System.out.println("contextDestroyed(): ServerInfo: " +
                ctx.getServerInfo() + " " + System.currentTimeMillis());
        System.out.println("contextDestroyed(): ContextPath: " +
                ctx.getContextPath() + " " + System.currentTimeMillis());
        logger.info("contextDestroyed(): ServerInfo: " +
                ctx.getServerInfo() + " " + System.currentTimeMillis());
        logger.info("contextDestroyed(): ContextPath: " +
                ctx.getContextPath() + " " + System.currentTimeMillis());
        tcl.setAlive(false);
    }
}


