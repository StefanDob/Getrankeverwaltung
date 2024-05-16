package de.tu.darmstadt.dataModel;

// DO NOT REMOVE ANY IMPORTED PACKAGES !!!
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.exceptions.*;
import de.tu.darmstadt.backend.IceCreamShopProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

import static de.tu.darmstadt.backend.IceCreamShopProperties.*;

/**
 * An {@link Account} for the drink shop is a superclass of those classes that grants a user of the drink shop an
 * environment for performing {@link Item} purchases.
 * <p>
 * Each account is clearly identified by a set of attributes (primary key) that is
 * specified by the user when creating an {@link Account}.
 */
@Entity
public class Account {

    /**
     * The email, that is the primary key of {@link Account}, is used to register and to login into the account.
     */
    @Id
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * The password of the {@link Account}.
     * It must meet the requirements specified in the {@link IceCreamShopProperties}
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The first name of the {@link Account} owner.
     * It must only contain letters, blank spaces and no numbers or other special characters.
     */
    @Column(name = "first_name", nullable = false)
    private String first_name;

    /**
     * The last name of the {@link Account} owner.
     * It must only contain letters and no numbers or other special characters.
     */
    @Column(name = "last_name", nullable = false)
    private String last_name;

    /**
     * The birthdate of the {@link Account} owner in the format yyyy-mm-dd.
     */
    @Column(name = "birth_date", nullable = false)
    private LocalDate birth_date;

    /**
     * The phone number of the {@link Account} owner (optional) which may be {@code null}.
     * It must only contain numbers.
     */
    @Column(name = "phone_number")
    private @Nullable String phone_number;

    /**
     * Specifies the {@link AccountStatus} of the {@link Account}.
     */
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    /**
     * The debt limit of the {@link Account}.
     * It is the amount of money that the user can overdraw (überziehen).
     */
    @Column(name = "debt_limit", nullable = false)
    private double debt_limit;

    /**
     * The current amount of money the user of the {@link Account} is currently having.
     */
    @Column(name = "saldo", nullable = false)
    private double balance = 0;


    // ::::::::::::::::::::::::::::::: CONSTRUCTORS :::::::::::::::::::::::::::::

    /**
     * A default constructor is used for the entity class.
     */
    public Account() {
        // DO NOT REMOVE THIS CONSTRUCTOR AND DO NOT ADD ANYTHING TO IT !!!
    }

    /**
     * Constructs a new {@link Account} with specified personal data.
     * @param email the specified email
     * @param password the specified password
     * @param first_name the specified first name
     * @param last_name the specified last name
     * @param birth_date the specified birthdate
     * @param phone_number the specified phone number (optional). May be {@code null}
     *
     * @throws AccountPolicyException is thrown if any personal data does not meet the {@link Account}
     * {@link IceCreamShopProperties policies}
     */
    public Account(String email, String password, String first_name, String last_name,
                   LocalDate birth_date, String phone_number)
            throws AccountPolicyException
    {
        this(email, password, first_name, last_name, birth_date, phone_number,
                AccountStatus.STANDARD, DEFAULT_DEBT_LIMIT);
    }


    public Account(String email, String password, String first_name, String last_name,
                   LocalDate birth_date, String phone_number, AccountStatus status, double debt_limit

                   ) throws AccountPolicyException, InvalidPasswordFormatException
    {

        if(!VALID_NAME.test(first_name) || !VALID_NAME.test(last_name)) {
            throw new InvalidNameFormatException("Only a-z or A-Z, as well as '-' and white spaces are allowed in names.");
        }

        if(!EMAIL_FORMAT.test(email)) {
            throw new AccountPolicyException("E-Mail is not in a valid format");
        }

        if(!PASSWORD_POLICY.test(password)) {
            throw new InvalidPasswordFormatException("Password is not safe. Enter a new password");
        }

        if(!AGE_REQUIREMENTS.test(birth_date)) {
            throw new IllegalBirthdateException("Age requirements not met");
        }

        if(!PHONE_NUMBER_FORMAT.test(phone_number)) {
            throw new InvalidPhoneNumberFormatException("Phone number is not valid");
        }

        // The debt limit should always be a negative value.
        if(debt_limit > 0) {
            this.debt_limit = -debt_limit;
        }

        this.status = status;
    }


    // ::::::::::::::::::::::::::::::::::::::::::: METHODS ::::::::::::::::::::::::::::::::::::::::::::

    protected void setStatus(AccountStatus status) {
        this.status = status;
    }

    protected AccountStatus getStatus() {
        return status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getDebt_limit() {
        return debt_limit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public @Nullable String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(@Nullable String phone_number) {
        this.phone_number = phone_number;
    }

    public void setDebt_limit(double debt_limit) {
        this.debt_limit = debt_limit;
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } // end of if

        if(o instanceof Account acc) {
            return email.equals(acc.email);
        } // end of if

        return false;
    }

    /**
     * Returns the {@link String} representation of the {@link Account} in the following format (without '<' and '>'):
     * <p>
     * {@code "[example@mail.org: <last name>, <first name>]"}
     *
     * @return the {@link String} representation of the {@link Account}
     */
    @Override
    public String toString() {
        return '[' + email + ": " + last_name + ", " + first_name + "]";
    }
}
