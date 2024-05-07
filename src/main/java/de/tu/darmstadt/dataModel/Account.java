package de.tu.darmstadt.dataModel;


import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.Exceptions.AccountPolicyException;

import java.time.LocalDate;

import static de.tu.darmstadt.backend.IceCreamShopProperties.*;


/**
 * An account for the ice cream shop.
 */
public class Account {

    private String email;

    private String password;

    private String first_name;

    private String last_name;

    private LocalDate birth_date;

    private String phone_number;

    private AccountStatus status;

    private double debt_limit;

    private double saldo = 0;


    // ::::::::::::::::::::::::::::::: CONSTRUCTORS :::::::::::::::::::::::::::::

    public Account(String email, String password, String first_name, String last_name,
                   LocalDate birth_date, String phone_number) throws AccountPolicyException
    {
        this(email, password, first_name, last_name, birth_date, phone_number,
                AccountStatus.STANDARD, DEFAULT_DEBT_LIMIT);
    }

    public Account(String email, String password, String first_name, String last_name,
                   LocalDate birth_date, String phone_number, AccountStatus status, double debt_limit

                   ) throws AccountPolicyException
    {
        if(!EMAIL_FORMAT.test(email)) {
            throw new AccountPolicyException("E-Mail is not in a valid format");
        }

        if(!PASSWORD_POLICY.test(password)) {
            throw new AccountPolicyException("Password is not safe. Enter a new password");
        }

        if(!VALID_NAME.test(first_name) || !VALID_NAME.test(last_name)) {
            throw new AccountPolicyException("Only a-z or A-Z are allowed in names.");
        }

        if(!AGE_REQUIREMENTS.test(birth_date)) {
            throw new AccountPolicyException("Age requirements not met");
        }

        if(!PHONE_NUMBER_FORMAT.test(phone_number)) {
            throw new AccountPolicyException("Phone number is not valid");
        }

        if(debt_limit > 0) {
            this.debt_limit = -debt_limit;
        }

        this.status = status;
    }

}
