package com.larinia.security;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLServerSocketFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/TestCipher")
public class TestCipher extends HttpServlet {

    protected void doGet(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {

        String algrim = testMyMethod();
        Map cipherMap = listCipher();

        PrintWriter out = var2.getWriter();

        out.println("<h1> TestCipher </h1>");

        out.println("Algorithm is: "+algrim+"\r\n");
        out.println("cipher lists are: " + cipherMap.toString()+"\r\n");
        out.println("System properties:"+System.getProperties()+"\r\n");
        //    out.println(docNew);
        out.close();
    }

    public String testMyMethod() {
        Cipher cipher =null;
        try {
             cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            System.out.println("TestCipher.testMyMethod:" + cipher.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
          return cipher.getAlgorithm();
    }

    public Map listCipher() {
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        String[] defaultCiphers = ssf.getDefaultCipherSuites();
        String[] availableCiphers = ssf.getSupportedCipherSuites();

        TreeMap ciphers = new TreeMap();

        for (int i = 0; i < availableCiphers.length; ++i)
            ciphers.put(availableCiphers[i], Boolean.FALSE);

        for (int i = 0; i < defaultCiphers.length; ++i)
            ciphers.put(defaultCiphers[i], Boolean.TRUE);

        System.out.println("Default\tCipher");
        for (Iterator i = ciphers.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry cipher = (Map.Entry) i.next();

            if (Boolean.TRUE.equals(cipher.getValue()))
                System.out.print('*');
            else
                System.out.print(' ');

            System.out.print('\t');
            System.out.println(cipher.getKey());
        }

        return ciphers;
    }

}
