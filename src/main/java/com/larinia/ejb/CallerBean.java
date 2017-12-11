package com.larinia.ejb;

import org.jboss.security.annotation.SecurityDomain;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

@Interceptors({HelloServerInterceptor.class})
@Stateless
//@RolesAllowed({"admin"})
@SecurityDomain("ApplicationSecurityDomain")
//@SecurityDomain("jmx-console")
@Remote(CallerLocal.class)
public class CallerBean implements CallerLocal {
   // @RolesAllowed("admin")
    public String testMethod(String name) {
        System.out.println("nnt Bean's testMethod(String name) called....");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "[CallerBean] returned Hello " + name+"from the server side";
    }


    public Integer ejbFindByTopic(String company, String language) {
        System.out.println("CallerBean.ejbFindByTopic: 1");
        return 0;
    }

    public void ejbRemove() {
        System.out.println("CallerBean.ejbRemove: testing");
    }
}