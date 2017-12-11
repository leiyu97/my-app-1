package com.larinia.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.logging.Logger;

@Stateless
public class MyFirstEjbTimerBean implements MyFirstEjbTimer {

    public static final Logger logger = Logger.getLogger(MyFirstEjbTimerBean.class.getName());

    @Resource
    private TimerService timerService;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void postConstruct() {
        logger.info("This is a notification on startup");
        ScheduleExpression expression = new ScheduleExpression();
        // every full hour
        expression.month("*").dayOfMonth("*").hour("*");
        // example every 10 seconds
        // expression.month("*").dayOfMonth("*").hour("*").minute("*").second("0/10");
        timerService.createCalendarTimer(expression, new TimerConfig("Test started at " + new Date(), false));

    }
    @PreDestroy
    private void preDestroy() {

    }

    /**
     * Schedule run every 10 minutes. <b>Notice</b> as it is persistent it will be fetched later if the server was down!
     */
    @Schedule(minute = "*/10", hour = "*", persistent = true)
    private void run(final Timer timer) {
        logger.info("run scheduled timer");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            logger.warning("Sleep interrupted");
        }
    }

}
