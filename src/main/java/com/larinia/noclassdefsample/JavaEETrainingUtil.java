package com.larinia.noclassdefsample;

import java.lang.ClassLoader;
import java.util.Stack;
/**
 * Created by lyu on 09/02/17.
 */
public class JavaEETrainingUtil {
    /**
     * getCurrentClassloaderDetail
     *
     * @return
     */

    public static String getCurrentClassloaderDetail() {


        StringBuffer classLoaderDetail = new StringBuffer();

        Stack<ClassLoader> classLoaderStack = new Stack<ClassLoader>();


        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();


        classLoaderDetail.append("\n-----------------------------------------------------------------\n");


        // Build a Stack of the current ClassLoader chain

        while (currentClassLoader != null) {


            classLoaderStack.push(currentClassLoader);


            currentClassLoader = currentClassLoader.getParent();

        }


        // Print ClassLoader parent chain

        while (classLoaderStack.size() > 0) {


            ClassLoader classLoader = classLoaderStack.pop();


            // Print current

            classLoaderDetail.append(classLoader);


            if (classLoaderStack.size() > 0) {

                classLoaderDetail.append("\n--- delegation ---\n");

            } else {

                classLoaderDetail.append(" **Current ClassLoader**");

            }

        }


        classLoaderDetail.append("\n-----------------------------------------------------------------\n");


        return classLoaderDetail.toString();

    }

}

