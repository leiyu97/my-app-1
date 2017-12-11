package com.larinia.ejb;


    /*
    To the extent possible under law, Red Hat, Inc. has dedicated all copyright to this software to the public domain worldwide, pursuant to the CC0 Public Domain Dedication. This software is distributed without any warranty.  See <http://creativecommons.org/publicdomain/zero/1.0/>.
*/

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

    /**
     * @author bmaxwell
     */
    @Singleton
    @Startup
    public class MonitorEJBThreadPoolQueueSingleton {

        private Logger log = Logger.getLogger(MonitorEJBThreadPoolQueueSingleton.class.getName());
        private int warnIfQueueReaches = Integer.getInteger("warnIfQueueReaches", 10);
        private int queueCheckSeconds = Integer.getInteger("queueCheckSeconds", 60);
        private static final String defaultThreadPool = "jboss.as:subsystem=ejb3,thread-pool=default";
        private static int maxQueueSize = 0;

        private MBeanServer platformMBeanServer;
        private ObjectName name;

        @Resource
        private TimerService timerService;

        @PostConstruct
        public void scheduleTimer() {
            log.info("Scheduled MonitorEJBThreadPoolQueue to check every " + queueCheckSeconds + " seconds and warning if the queue is greater than " + warnIfQueueReaches);
            timerService.createIntervalTimer(queueCheckSeconds*1000, queueCheckSeconds*1000, new TimerConfig(null, false));
        }

        @Timeout
        public void checkThreadPoolQueue() {
            try {
                int queueSize = getQueueSize();
                if(queueSize > maxQueueSize)
                    maxQueueSize = queueSize;
                if(queueSize >= warnIfQueueReaches)
                    log.warning("EJB3 Default Pool queue size: " + queueSize + " max queue size reached: " + maxQueueSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private int getQueueSize() throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException {
            return (Integer) getMBeanServer().getAttribute(getObjectName(), "queueSize");
        }

        private MBeanServer getMBeanServer() {
            if(platformMBeanServer == null)
                platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            return platformMBeanServer;
        }

        private ObjectName getObjectName() {
            if(name == null) {
                try {
                    name = new ObjectName(defaultThreadPool);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            return name;
        }
    }


