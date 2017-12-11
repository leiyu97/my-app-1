package org.larinia.client;

import org.apache.log4j.Logger;

public class TomcatLogging extends Thread {
    private static final Logger logger = Logger.getLogger(TomcatLogging.class);
    boolean isAlive = true; //set false for now, don't want the logging
    int count=0;

    public void run() {
        logger.info(" In the run method: start");
        while (isAlive) { // isAlive is a global-level (static, even) boolean
            // you declared earlier as true, your app should set it to false
            // if your app decides to exit

            try {
                System.out.println("Rollover Log Text "+ count++ +" times");
                logger.info("in the run method and trying to roll over "+ count++ +" times");
                Thread.currentThread().sleep(1000 * 60); // 60 seconds
            } catch (InterruptedException ignore) {
            }
        }
    }

    public void setAlive(boolean a) {
        this.isAlive = a;
    }
}
