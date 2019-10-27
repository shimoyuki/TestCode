package com.shizuku.mail.util;

import java.util.UUID;

/**
 * <tt>SMSUtils</tt> defines some useful methods.
 */
public class SMSUtils {
    /**
     * Check a string is contained in the array or not.
     * @param array a string array
     * @param text a string
     * @return If text in the array then return true, otherwise return false.
     */
    public static boolean contains(String array[], String text) {
        if (array == null) {
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a unique ID. It will be used in POP3 protocol. (APOP command)
     * @return a unique ID
     */
    public static String getID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 
     * @return
     */
    public static String getClasspath() {
        String path = new SMSUtils().getClass().getResource("/").getPath();
        path = path.replace('\\', '/');
        if (!path.endsWith("/")) {
            path += "/";
        }
        return path;
    }
}
