package de.tu.darmstadt.Utils;

import de.tu.darmstadt.dataModel.Account;

/**
 * Manages the session-related operations for user accounts.
 *
 * This class provides static methods to set and retrieve the current user account
 * for the session. The account information is stored in a static variable.
 */
public class SessionManagement {

    /**
     * The current user account for the session.
     *
     * This static variable holds the account information for the current session.
     */
    public static Account account;

    /**
     * Sets the current user account for the session.
     * This method updates the static variable {@link #account} with the provided
     * {@link Account} object.
     *
     * @param account the {@link Account} object to be set as the current session account
     */
    public static void setAccount(Account account) {
        SessionManagement.account = account;
    }

    /**
     * Retrieves the current user account for the session.
     * This method returns the {@link Account} object stored in the static variable
     * {@link #account}.
     *
     * @return the current {@link Account} object for the session, or {@code null} if no account is set
     */
    public static Account getAccount() {
        return account;
    }
}

