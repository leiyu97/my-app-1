package org.larinia.client;

import javax.servlet.http.HttpServletRequest;

public class AlertsAjaxWebClient extends org.apache.activemq.web.AjaxWebClient  {

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String sessionId;

    public AlertsAjaxWebClient(HttpServletRequest request, long maximumReadTimeout)
    {
        super(request, maximumReadTimeout);
    }

    public boolean isSessionTimeout() {
        boolean timedOut= false;

        return timedOut;
    }
}
