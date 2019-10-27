package com.shizuku.mail.core.pop3;

import com.shizuku.mail.util.EmailAddress;

/**
 * This is an interface to define some functions about the pop users
 * who are online.
 */
public interface Pop3User {
    /**
     * Check user is online or not. A user can't login two times at the same
     * time before logout.
     * @param emailAddress User emailAddress Object
     * @return If current user is online, return true. Otherwise return false.
     */
    public boolean isOnline(EmailAddress emailAddress);

    /**
     * When a user login, don't lock he or she until it QUIT or connectless.
     * @param emailAddress User emailAddress Object
     */
    public void lockUser(EmailAddress emailAddress);

    /**
     * When a user logout or connectless, unlock it.
     * @param emailAddress User emailAddress Object
     */
    public void unlockUser(EmailAddress emailAddress);
}
