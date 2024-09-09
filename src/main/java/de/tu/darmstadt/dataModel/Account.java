package de.tu.darmstadt.dataModel;

// DO NOT REMOVE ANY IMPORTED PACKAGES !!!
import de.tu.darmstadt.Utils.AccountUtils;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.ItemShopProperties;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.*;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCart;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

import static de.tu.darmstadt.backend.ItemShopProperties.*;
import static de.tu.darmstadt.Utils.ProjectUtils.*;
import static de.tu.darmstadt.Utils.ExceptionChecker.*;

/**
 * An {@link Account} for the drink shop is a superclass of those classes that grants a user of the drink shop an
 * environment for performing {@link Item} purchases.
 * <p>
 * Each account is clearly identified by a set of attributes (primary key) that is
 * specified by the user when creating an {@link Account}.
 */

@Entity
@Table(name = "account")
public class Account {

    /**
     * This immutable attribute is an ID of {@link Account}. An ID is a unique attribute that can clearly identify
     * an {@link Account} without any need of other attributes.
     * <p>
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The first name of the {@link Account} owner.
     * It must only contain letters, blank spaces and no numbers or other special characters.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The last name of the {@link Account} owner.
     * It must only contain letters and no numbers or other special characters.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * The password of the {@link Account}.
     * It must meet the requirements specified in the {@link ItemShopProperties}
     */
    @Column(name = "password", nullable = false)
    private String password;


    /**
     * The email, that is the primary key of {@link Account}, is used to register and to login into the account.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * The birthdate of the {@link Account} owner in the format yyyy-mm-dd.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * The phone number of the {@link Account} owner (optional) which may be {@code null}.
     * It must only contain numbers.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Specifies the {@link AccountStatus} of the {@link Account}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status = AccountStatus.STANDARD;

    /**
     * The debt limit of the {@link Account}.
     * It is the amount of money that the user can overdraw (überziehen).
     */
    @Column(name = "debt_limit", nullable = false)
    private Double debtLimit = 0.0;

    /**
     * The current amount of money the user of the {@link Account} is currently having.
     */
    @Column(name = "saldo", nullable = false)
    private Double saldo = 0.0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id", unique = true)
    private ShoppingCart shoppingCart;


    // ::::::::::::::::::::::::::::::::::::::::: CONSTRUCTORS :::::::::::::::::::::::::::::::::::::::::::


    /**
     * A default constructor is used for the entity class.
     */
    public Account() throws AccountPolicyException {
        // DO NOT REMOVE THIS CONSTRUCTOR AND DO NOT ADD ANYTHING TO IT !!!
    }

    /**
     * Constructs a new {@link AccountStatus#STANDARD standard} {@link Account} with specified personal data.
     * @param email the specified email
     * @param password the specified password
     * @param firstName the specified first name
     * @param lastName the specified last name
     * @param birthDate the specified birthdate
     * @param phoneNumber the specified phone number (optional). May be {@code null}
     *
     * TODO @Toni if possible remove this constructor as having such constructors is not the intended way for jpa mappings
     *
     * @throws AccountPolicyException is thrown if any personal data does not meet the {@link Account}
     * {@link ItemShopProperties policies}
     */
    public Account(String email, String password, String firstName, String lastName,
                   Date birthDate, String phoneNumber)
            throws AccountPolicyException
    {
        // Instantiates an account with AccountStatus = STANDARD and the DEFAULT_DEBT_LIMIT.
        this(email, password, firstName, lastName, birthDate, phoneNumber,
                AccountStatus.STANDARD, 0.0);
    }


    /**
     * Constructs a new {@link Account} with specified personal data.
     * @param email the specified email
     * @param password the specified password
     * @param firstName the specified first name
     * @param lastName the specified last name
     * @param birthDate the specified birthdate
     * @param phoneNumber the specified phone number (optional)
     * @param status the specified {@link AccountStatus}
     * @param debtLimit the specified debt limit
     *TODO @Toni if possible remove this constructor as having such constructors is not the intended way for jpa mappings
     *
     * @throws AccountPolicyException is thrown if any data does not meet the requirements specified in the policies.
     */
    public Account(@NotNull String email, @NotNull String password, String firstName, String lastName,
                   Date birthDate, @Nullable String phoneNumber, AccountStatus status, double debtLimit

    ) throws AccountPolicyException
    {

        // The method String#trim() removes all leading and trailing white spaces.
        email = email.trim();
        firstName = firstName.trim();
        lastName = lastName.trim();

        this.firstName = AccountUtils.checkIfFirstNameIsInValidFormat(firstName);
        this.lastName = AccountUtils.checkIfLastNameIsInValidFormat(lastName);

        // Checks if the specified email is already in use
        AccountUtils.isEmailAlreadyInUse(email); // throws EmailIsAlreadyInUseException

        this.email = AccountUtils.checkIfEmailIsInValidFormat(email);
        this.password = AccountUtils.checkIfPasswordIsValid(password);
        //TODO add the logic for checking birth date again
        this.birthDate = birthDate; // The birthdate is stored in String format
        this.phoneNumber = AccountUtils.check_if_phone_number_is_in_valid_format(phoneNumber);

        // The debt limit should always be a negative value.
        if(debtLimit > 0) {
            this.debtLimit = -debtLimit;  // if the debt limit is positive, it is numerically negated.
        } else {
            this.debtLimit = debtLimit;
        }

        this.status = status;

    }

    // :::::::::::::::::::::::::::::::::::::: AUXILIARY METHODS :::::::::::::::::::::::::::::::::::::::




    /**
     * Checks if a specified ID is already existing.
     * @param   ID the specified ID
     * @return  true if the ID is already existing
     * @throws  AccountPolicyException is thrown if the specified ID is not in a valid
     *          {@link ItemShopProperties#VALID_ACCOUNT_ID_FORMAT}.
     */
    private static boolean isIDAlreadyExisting(final Long ID) throws AccountPolicyException {
        return AccountOperations.getAccountByID(ID) != null;
    }






    // ::::::::::::::::::::::::::::::::::::::::::: METHODS ::::::::::::::::::::::::::::::::::::::::::::

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) throws DebtLimitExceedingException {
        if(saldo < -debtLimit) {
            throw new DebtLimitExceedingException(LanguageManager.getLocalizedText("Balance should not exceed the debt limit. ") +
                    LanguageManager.getLocalizedText("Current debt limit: ") + -debtLimit + LanguageManager.getLocalizedText(" ; Balance after setting to new value: ") + saldo );
        } // end of if

        this.saldo = saldo;
    }

    public double getDebtLimit() {
        return debtLimit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidEmailFormatException {
        this.email = AccountUtils.checkIfEmailIsInValidFormat(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidPasswordFormatException {
        this.password = AccountUtils.checkIfPasswordIsValid(password);
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InvalidNameFormatException {
        this.lastName = AccountUtils.checkIfNameIsInValidFormat(lastName);
    }

    public String getFirstName() {
        return firstName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setDebtLimit(double debt_limit) {
        // The debt limit should always be a negative value.
        if(debt_limit > 0) {
            this.debtLimit = debt_limit;
        } else {
            this.debtLimit = debt_limit;
        } // end of if-else
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
        return '[' + email + ": " + lastName + ", " + firstName + "]";
    }

    public Long getId() {
        return id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * this method checks wether the account has enough coverage for a transaction
     * @param amount
     * @return true if the account has enough coverage
     */
    public boolean checkForCoverage(Double amount) {
        return (getSaldo() + getDebtLimit()) < amount;
    }
}
