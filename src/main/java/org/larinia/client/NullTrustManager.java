package org.larinia.client;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class NullTrustManager implements X509TrustManager {

    /**
     * @see javax.net.ssl.X509TrustManager
     */
    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {

        System.out.println("TrustAllManager.checkClientTrusted: certifcate " + xcs.toString() + " and String is " + string);
    }

    /**
     * @see javax.net.ssl.X509TrustManager
     */
    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        System.out.println("TrustAllManager.checkServerTrusted: xcs  " + xcs.toString() + "string is " + string);
    }

    /**
     * @see javax.net.ssl.X509TrustManager
     */
    public X509Certificate[] getAcceptedIssuers() {

        return null;
    }
}
