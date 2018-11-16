package com.larinia.app;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.web.AjaxWebClient;
import org.larinia.client.AlertsAjaxWebClient;

import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class AlertsAjaxServlet extends org.apache.activemq.web.MessageListenerServlet {

// The hash map to store AlertAjaxWebClient by sessionId
    private static HashMap<String, AlertsAjaxWebClient> pathAjaxWebClients = new HashMap<String, AlertsAjaxWebClient>();

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       try
       {

           // call parent servlet method
           super.doGet(request, response);
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
    }

@Override
    protected void doMessages(org.apache.activemq.web.AjaxWebClient client, HttpServletRequest request, HttpServletResponse response)
           throws JMSException, IOException
    {

       AlertsAjaxWebClient alertAjaxWebClient = (AlertsAjaxWebClient) client;

       super.doMessages(client, request, response);

       // check is its a session timeout and then close the client and remove the client from the map
       if(alertAjaxWebClient != null && alertAjaxWebClient.isSessionTimeout())
       {
           alertAjaxWebClient.close();
           removeAlertAjaxClient(alertAjaxWebClient.getSessionId());
       }
    }

    @Override
    protected AjaxWebClient getAjaxWebClient(HttpServletRequest request)
    {
       String sessionId = null;
       AlertsAjaxWebClient client = null;
       /*SessionCO sessCo = (SessionCO) request.getSession().getAttribute(ConstantsCommon.SESSION_VO_ATTR);
       //If the sessionCO is null, that's mean that the session is timeouted and removed from the ServletContext . the sesionId is then
       //taken from the request. In fact, the custom session listener will set the sessionId in the request attributes on sessionDestroy
       if(sessCo == null)
       {
           if(request.getAttribute(PATH_ALERT_TIMEOUT_SESSIONID) != null)
           {
              sessionId = (String) request.getAttribute(PATH_ALERT_TIMEOUT_SESSIONID);
           }

       }
       //If the sessionCo is not null, thats mean that the HttpSession is alive, then we can take the sessionId from the sessionCO
       else
       {
           sessionId = sessCo.getHttpSessionID();
       }*/

       sessionId = request.getSession().getId();
        System.out.println("AlertsAjaxServlet.getAjaxWebClient: sessionId is "+sessionId);
       long pathMaximumReadTimeout = 657646463L;

       //Check if the client exists inside the map
       client = getAlertAjaxClient(sessionId);
       //If it isnt then create a new client and store it in the map
       if(client == null)
       {
           client = new AlertsAjaxWebClient(request, pathMaximumReadTimeout);
           client.setSessionId(sessionId);
           putAlertAjaxClient(sessionId, client);
       }

       return client;
    }
     public static AlertsAjaxWebClient getAlertAjaxClient(String sessionId)
    {
       AlertsAjaxWebClient client = null;
       synchronized(pathAjaxWebClients)
       {
           client = pathAjaxWebClients.get(sessionId);
       }
       return client;
    }

    /**
     * This function will put an AlertsAjaxWebClient in the storing map
     * @param client
     */
    public static void putAlertAjaxClient(String sessionId, AlertsAjaxWebClient client)
    {
       synchronized(pathAjaxWebClients)
       {
           pathAjaxWebClients.put(sessionId,client);
       }
    }
    public static void removeAlertAjaxClient(String sessionId)
    {
       synchronized(pathAjaxWebClients)
       {
           pathAjaxWebClients.remove(sessionId);
       }
    }

}
