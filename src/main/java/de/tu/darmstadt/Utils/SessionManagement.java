package de.tu.darmstadt.Utils;

import de.tu.darmstadt.dataModel.Account;

public class SessionManagement {

    public static Account account;

    public static void setAccount(Account account) {
        SessionManagement.account = account;
    }

    public static Account getAccount() {
        return account;
    }
}

