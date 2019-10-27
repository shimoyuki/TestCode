package com.shizuku.mail.core.pop3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.shizuku.mail.util.EmailAddress;

public class Pop3UserMonitor implements Pop3User {

    /**
     * Store the online users.
     */
    private static List<String>    list;
    private static Pop3UserMonitor singletonInstance = null;

    /**
     * To make sure only one instance of Pop3UserList
     * @return singleton instance of Pop3UserList
     */
    public static Pop3UserMonitor getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new Pop3UserMonitor();
        }
        return singletonInstance;
    }

    private Pop3UserMonitor() {
        list = new ArrayList<String>();
    }

    /**
     * @see com.shizuku.mail.Pop3.Pop3User#isOnline(com.shizuku.mail.Util.EmailAddress) 
     * @param emailAddress User emailAddress Object
     * @return If this user is online, return true. Otherwise return false.
     */
    public boolean isOnline(EmailAddress emailAddress) {
        if (emailAddress == null) {
            return false;
        }
        for (Iterator<String> i = list.iterator(); i.hasNext();) {
            String email = i.next();
            if (email.equals(emailAddress.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Appends the user to the end of the list.
     * @see com.shizuku.mail.Pop3.Pop3User#lockUser(com.shizuku.mail.Util.EmailAddress) 
     * @param emailAddress User emailAddress Object
     */
    public void lockUser(EmailAddress emailAddress) {
        if (!isOnline(emailAddress)) {
            list.add(emailAddress.toString());
        }
    }

    /**
     * Remove the user from this list. If the list does not contain
     * the user, it is unchanged.
     * @see com.shizuku.mail.Pop3.Pop3User#unlockUser(com.shizuku.mail.Util.EmailAddress) 
     * @param emailAddress User emailAddress Object
     */
    public void unlockUser(EmailAddress emailAddress) {
        list.remove(emailAddress.toString());
    }
}
