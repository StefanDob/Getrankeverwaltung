package de.tu.darmstadt.dataModel;

// DO NOT REMOVE ANY IMPORTED PACKAGES !!!
import de.tu.darmstadt.Utils.AccountUtils;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.ItemShopProperties;
import de.tu.darmstadt.backend.backendOperations.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.*;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCart;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

/**
 * The {@code Account} class represents an account in the drink shop, serving as a base for users
 * to perform {@link Item} purchases. Each account is uniquely identified by a set of attributes,
 * such as email and personal details, provided during account creation.
 */
@Entity
@Table(name = "account")
public class Account {

    /**
     * Unique identifier for the {@code Account}. This ID is auto-generated and serves as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * First name of the account owner. Must contain only letters and spaces, without numbers or special characters.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Last name of the account owner. Must contain only letters, without numbers or special characters.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * The password for the account. Must meet the requirements specified in the {@link ItemShopProperties}.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Email address, used as the primary key for the account. It is unique and required for registration and login.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Birthdate of the account owner in the format yyyy-mm-dd.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * Phone number of the account owner (optional). Must contain only digits.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Current status of the account, represented by {@link AccountStatus}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status = AccountStatus.STANDARD;

    /**
     * The debt limit for the account, indicating the maximum overdraft allowed.
     */
    @Column(name = "debt_limit", nullable = false)
    private Double debtLimit = 0.0;

    /**
     * The current balance of the account.
     */
    @Column(name = "saldo", nullable = false)
    private Double saldo = 0.0;

    /**
     * One-to-one relationship with the account's shopping cart.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id", unique = true)
    private ShoppingCart shoppingCart;

    // ::::::::::::::::::::::::::::::::::::::::: CONSTRUCTORS :::::::::::::::::::::::::::::::::::::::::::

    /**
     * Default constructor for the {@code Account} entity. Required by JPA.
     */
    public Account() throws AccountPolicyException {
        // DO NOT REMOVE THIS CONSTRUCTOR AND DO NOT ADD ANYTHING TO IT.
    }

    /**
     * Constructs a standard {@link Account} with the specified personal data.
     *
     * @param email the specified email address
     * @param password the specified password
     * @param firstName the specified first name
     * @param lastName the specified last name
     * @param birthDate the specified birth date
     * @param phoneNumber the specified phone number (optional, may be {@code null})
     * @throws AccountPolicyException if any personal data does not meet the {@link ItemShopProperties} policies
     */
    public Account(String email, String password, String firstName, String lastName, Date birthDate, String phoneNumber)
            throws AccountPolicyException {
        this(email, password, firstName, lastName, birthDate, phoneNumber, AccountStatus.STANDARD, 0.0);
    }

    /**
     * Constructs a new {@link Account} with the specified data, including status and debt limit.
     *
     * @param email the specified email address
     * @param password the specified password
     * @param firstName the specified first name
     * @param lastName the specified last name
     * @param birthDate the specified birth date
     * @param phoneNumber the specified phone number (optional)
     * @param status the specified {@link AccountStatus}
     * @param debtLimit the specified debt limit
     * @throws AccountPolicyException if the data does not meet the specified requirements
     */
    public Account(@NotNull String email, @NotNull String password, String firstName, String lastName,
                   Date birthDate, @Nullable String phoneNumber, AccountStatus status, double debtLimit)
            throws AccountPolicyException {

        // Trim whitespace from input fields
        email = email.trim();
        firstName = firstName.trim();
        lastName = lastName.trim();

        this.firstName = AccountUtils.checkIfFirstNameIsInValidFormat(firstName);
        this.lastName = AccountUtils.checkIfLastNameIsInValidFormat(lastName);

        // Validate email and ensure it is not already in use
        AccountUtils.isEmailAlreadyInUse(email); // throws EmailIsAlreadyInUseException

        this.email = AccountUtils.checkIfEmailIsInValidFormat(email);
        this.password = AccountUtils.checkIfPasswordIsValid(password);

        // TODO: Add logic to validate birth date
        this.birthDate = birthDate;

        this.phoneNumber = AccountUtils.check_if_phone_number_is_in_valid_format(phoneNumber);

        // Ensure the debt limit is negative
        this.debtLimit = (debtLimit > 0) ? -debtLimit : debtLimit;

        this.status = status;
    }

    // ::::::::::::::::::::::::::::::::::::::::: METHODS ::::::::::::::::::::::::::::::::::::::::::::

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InvalidNameFormatException {
        this.lastName = AccountUtils.checkIfNameIsInValidFormat(lastName);
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getDebtLimit() {
        return debtLimit;
    }

    public void setDebtLimit(double debtLimit) {
        // Ensure the debt limit is a negative value
        this.debtLimit = (debtLimit > 0) ? -debtLimit : debtLimit;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) throws DebtLimitExceedingException {
        if (saldo < -debtLimit) {
            throw new DebtLimitExceedingException(LanguageManager.getLocalizedText("Balance should not exceed the debt limit. ") +
                    LanguageManager.getLocalizedText("Current debt limit: ") + -debtLimit +
                    LanguageManager.getLocalizedText(" ; Balance after setting to new value: ") + saldo);
        }
        this.saldo = saldo;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Checks whether the account has sufficient coverage for a transaction.
     *
     * @param amount the transaction amount
     * @return {@code true} if the account has enough coverage, {@code false} otherwise
     */
    public boolean checkForCoverage(Double amount) {
        return (getSaldo() + getDebtLimit()) < amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Account acc) {
            return email.equals(acc.email);
        }
        return false;
    }

    /**
     * Returns the string representation of the {@code Account} in the format:
     * {@code "[email: <last name>, <first name>]"}
     *
     * @return the string representation of the {@code Account}
     */
    @Override
    public String toString() {
        return '[' + email + ": " + lastName + ", " + firstName + ']';
    }
}

