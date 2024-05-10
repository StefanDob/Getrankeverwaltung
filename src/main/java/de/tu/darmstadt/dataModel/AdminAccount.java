package de.tu.darmstadt.dataModel;

import static de.tu.darmstadt.backend.AccountStatus.*;

import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.exceptions.AccountPolicyException;
import de.tu.darmstadt.backend.exceptions.InvalidPasswordFormatException;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

/**
 * An {@link AdminAccount} is a subclass of {@link Account} that is granted with privileged actions.
 */
public class AdminAccount extends Account {

    public AdminAccount(String email, String password, String first_name, String last_name, LocalDate birth_date,
                        String phone_number, double debt_limit)
            throws AccountPolicyException, InvalidPasswordFormatException

    {
        super(email, password, first_name, last_name, birth_date, phone_number, PRIVILEGED, debt_limit);
    }


    public void setAccountStatus(@NotNull Account account, AccountStatus status) {
        account.setStatus(status);
    }

}
