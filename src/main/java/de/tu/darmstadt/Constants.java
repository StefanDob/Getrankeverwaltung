package de.tu.darmstadt;

import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.dataModel.Account;

public class Constants {
    public static long getMasterID() {
        Account account = AccountOperations.getAccountByEmail("master@gmail.com");
        if(account != null){
            return account.getId();
        }
        return 0;
    }
}
