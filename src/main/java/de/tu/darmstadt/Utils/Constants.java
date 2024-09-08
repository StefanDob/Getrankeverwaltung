package de.tu.darmstadt.Utils;

import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.dataModel.Account;

public class Constants {

    /**
     * Master account is the technical help account for counter-bookin when buying items
     * @return ID of master account
     */
    public static long getMasterID() {
        Account account = AccountOperations.getAccountByEmail("master@gmail.com");
        if(account != null){
            return account.getId();
        }
        return 0;
    }
}
