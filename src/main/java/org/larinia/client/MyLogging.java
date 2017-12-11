package org.larinia.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyLogging {

    private static MyLogging myLogging;
    private static Logger logger;

    private static Object lock = new Object();

    public static void main(final String[] args) throws Exception {
        //LogManager.getLogger("main");

        Thread th1 = new Thread(new Runnable() {
            public void run() {
                final String name = "thread-1";
                final MyLogging myLog = getInstance();
                System.out.println("write " + name);
                logger.info("message" + name);
                System.out.println("done " + name);
            }
        });

        Thread th2 = new Thread(new Runnable() {
            public void run() {
                final String name = "thread-2";
                final MyLogging myLog = getInstance();
                System.out.println("write " + name);
                logger.info("message" + name);
                System.out.println("done " + name);
            }
        });

        th1.start();
        th2.start();
        th1.join();
        th2.join();
    }

    public static MyLogging getInstance() {
        synchronized (lock) {
            if (myLogging == null) {
                myLogging = new MyLogging();
            }
            if (logger == null) {
                logger = LogManager.getLogger();
            }
        }

        return myLogging;
    }
}
