package com.larinia.noclassdefsample;

/**
 * Created by lyu on 09/02/17.
 */
public class NoClassDefFoundErrorSimulator {

        public static void main(String[] args){

            System.out.println("java.lang.NoClassDefFoundError Simulator - Training 1");

            System.out.println("Copied from the internet");

            System.out.println("http://totoro.usersys.redhat.com");


            // Print current Classloader context

            System.out.println("\nCurrent ClassLoader chain: " + JavaEETrainingUtil.getCurrentClassloaderDetail());


            // 1. Create a new instance of CallerClassA

            CallerClassA caller = new CallerClassA();


            // 2. Execute method of the caller

            caller.doSomething();


            System.out.println("done!");

        }
    }

