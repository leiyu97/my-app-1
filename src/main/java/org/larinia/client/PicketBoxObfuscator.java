package org.larinia.client;

import java.lang.reflect.Method;

/**
 *
 * @author Stefan Negrea
 */
public class PicketBoxObfuscator {

    //no instances, please
    private PicketBoxObfuscator() {

    }

    /**
     * Use the internal JBossAS mechanism to obfuscate a password. This is not true encryption.
     *
     * @param text the clear text to be obfuscated
     * @return the obfuscated text
     */
    public static String encode(String text) {
        // We need to do some mumbo jumbo, as the interesting method is private
        // in SecureIdentityLoginModule
        try {
            String className = "org.picketbox.datasource.security.SecureIdentityLoginModule";
            Class<?> clazz = Class.forName(className);
            Object object = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("encode", String.class);
            method.setAccessible(true);
            String result = method.invoke(object, text).toString();
            return result;
        } catch (Exception e) {
            throw new RuntimeException("obfuscating db password failed: ", e);
        }
    }

    /**
     * Use the internal JBossAS mechanism to de-obfuscate a password back to its
     * clear text form. This is not true encryption.
     *
     * @param text the obfuscated text
     * @return the clear text
     */
    public static String decode(String text) {
        // We need to do some mumbo jumbo, as the interesting method is private
        // in SecureIdentityLoginModule
        try {
            String className = "org.picketbox.datasource.security.SecureIdentityLoginModule";
            Class<?> clazz = Class.forName(className);
            Object object = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("decode", String.class);
            method.setAccessible(true);
            char[] result = (char[]) method.invoke(object, text);
            return new String(result);
        } catch (Exception e) {
            throw new RuntimeException("de-obfuscating db password failed: ", e);
        }
    }

    public static void main(String[] args) {
        String password = PicketBoxObfuscator.decode("-56655c822a15d801592df9ada41ef82e4b9326534217aba5");
        System.out.println("PicketBoxObfuscator.main: password is "+ password);

        long value = Runtime.getRuntime().maxMemory();
        long tmemory = Runtime.getRuntime().totalMemory();

        System.out.println("PicketBoxObfuscator.main: max memory is "+value + "total memory "+tmemory);
    }

}