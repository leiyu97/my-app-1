package com.larinia.app;

/**
 * Created by lyu on 24/01/17.
 */

import java.net.*;

public class InetAddressTest {
    public static void main(String args[]) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("GetLocalhost "+inetAddress);

        inetAddress = InetAddress.getByName(args[0]);
        System.out.println("getByName: " + inetAddress);

        String hostName = inetAddress.getHostName();
        System.out.println("getHostName: "+hostName);

        InetAddress SW[] = InetAddress.getAllByName(args[1]);
        for (int i = 0; i < SW.length; i++)
            System.out.println("GetAllbyName: "+SW[i]);

        System.out.println("InetAddress.getCanonicalHostName(): " + inetAddress.getCanonicalHostName());
    }
}