package de.tu.darmstadt.dataModel;

// DO NOT REMOVE ANY IMPORTED PACKAGES !!!
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
import static de.tu.darmstadt.ProjectUtils.*;
import static de.tu.darmstadt.dataModel.Utils.ExceptionChecker.*;

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

        this.firstName = checkIfFirstNameIsInValidFormat(firstName);
        this.lastName = checkIfLastNameIsInValidFormat(lastName);

        // Checks if the specified email is already in use
        isEmailAlreadyInUse(email); // throws EmailIsAlreadyInUseException

        this.email = checkIfEmailIsInValidFormat(email);
        this.password = checkIfPasswordIsValid(password);
        //TODO add the logic for checking birth date again
        this.birthDate = birthDate; // The birthdate is stored in String format
        this.phoneNumber = check_if_phone_number_is_in_valid_format(phoneNumber);

        // The debt limit should always be a negative value.
        if(debtLimit > 0) {
            this.debtLimit = -debtLimit;  // if the debt limit is positive, it is numerically negated.
        } else {
            this.debtLimit = debtLimit;
        }

        this.status = status;

    }

    // :::::::::::::::::::::::::::::::::::::: AUXILIARY METHODS :::::::::::::::::::::::::::::::::::::::
    //TODO check what this method does and delete if necessary
    /*
    private static String setID() {

        String id;
        try {
            do {
                id = generateID();
            } while ( is_ID_already_existing(id) ); // end of do-while

            return id;
        } catch (AccountPolicyException e) {
            throw new RuntimeException("Account ID is not in a valid format.");
        }

    }

 */

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

    /**
     * This static method generates an {@link #id ID} as a {@link String} value for the {@link Account}.
     *
     * @return the ID generated for the {@link Account}
     */
    private static @NotNull String generateID() throws AccountPolicyException {

        // if(true) throw new RuntimeException("This static method is under construction and should not be called yet!");

        final char[] ch_set0 = new char[]{'a', 'z'}; // Set of chars: {'a', 'b', ... , 'z'}
        final char[] ch_set1 = new char[]{'A', 'Z'}; // Set of chars: {'A', 'B', ... , 'Z'}
        final char[] ch_set2 = new char[]{'0', '9'}; // Set of chars: {'0', '1', ... , '9'}

        final int number_of_id_segments = 5;
        final int length_of_id_segment = 5;

        // The StringBuilder already contains the first ID segment.
        StringBuilder stringBuilder = new StringBuilder( randomString(length_of_id_segment, ch_set0, ch_set1, ch_set2) );

        // The for-loop appends further ID segments which are separated by a '-'.
        for (int i = 1; i < number_of_id_segments; i++) {
            stringBuilder
                    .append("-") // '-' separates ID segments
                    .append( randomString(length_of_id_segment, ch_set0, ch_set1, ch_set2) ); // new ID segment
        } // end of for

        // The result to be returned. First of all, it is checked if it is in a valid format in the following if-block.
        final String resulting_ID = stringBuilder.toString();

        if( !VALID_ACCOUNT_ID_FORMAT.test(resulting_ID) ) {
            throw new AccountPolicyException("ID is not in a valid format: " + resulting_ID);
        } // end of if

        return resulting_ID;
    }

    /**
     * This static method generates an {@link #id ID} as a long value for an {@link Account}.
     *
     * @return the ID generated for the {@link Account}
     */
    private static long generateLongID() {
        return RANDOM.nextLong();
    }

    /**
     * This static method is used to check if a specified name is in a valid format.
     *
     * @see ItemShopProperties#VALID_NAME
     *
     * @param name the specified name to be checked
     * @return the specified name if successfully checked
     * @throws InvalidNameFormatException is thrown if the name is not in a valid format
     */
    public static String checkIfNameIsInValidFormat(String name) throws InvalidNameFormatException {
        return checkIfInstanceIsValid(
                name,
                VALID_NAME,
                new InvalidNameFormatException("Only a-z or A-Z, as well as '-' and white spaces are allowed in names.")
        );
    }

    /**
     * This static method is used to check if a specified first name is in a valid format.
     *
     * @see ItemShopProperties#VALID_NAME
     *
     * @param name the specified first name to be checked
     * @return the specified first name if successfully checked
     * @throws BadFirstNameException is thrown if the first name is not in a valid format
     */
    public static String checkIfFirstNameIsInValidFormat(String name) throws BadFirstNameException {
        return checkIfInstanceIsValid(
                name,
                VALID_NAME,
                new BadFirstNameException("Only a-z or A-Z, as well as '-' and white spaces are allowed in names.")
        );
    }

    /**
     * This static method is used to check if a specified last name is in a valid format.
     *
     * @see ItemShopProperties#VALID_NAME
     *
     * @param name the specified last name to be checked
     * @return the specified last name if successfully checked
     * @throws BadLastNameException is thrown if the first name is not in a valid format
     */
    public static String checkIfLastNameIsInValidFormat(String name) throws BadLastNameException {
        return checkIfInstanceIsValid(
                name,
                VALID_NAME,
                new BadLastNameException("Only a-z or A-Z, as well as '-' and white spaces are allowed in names.")
        );
    }

    /**
     * This method checks if a specified email is already in use.
     *
     * @param email the specified email to be checked
     * @throws EmailAlreadyInUseException if the specified email is already in use
     */
    public static void isEmailAlreadyInUse(final @NotNull String email) throws EmailAlreadyInUseException {
        if( AccountOperations.getAccountByEmail(email) != null ) {
            throw new EmailAlreadyInUseException("This email is already in use: " + email);
        }

    }

    /**
     * This static method is used to check if a specified email is in a valid format.
     *
     * @see ItemShopProperties#EMAIL_FORMAT
     *
     * @param email the specified email to be checked
     * @return the specified email if successfully checked
     * @throws InvalidEmailFormatException is thrown if the email is not in a valid format
     */
    public static String checkIfEmailIsInValidFormat(String email) throws InvalidEmailFormatException {
        return checkIfInstanceIsValid(
                email,
                EMAIL_FORMAT,
                new InvalidEmailFormatException(email)
        );
    }

    /**
     * This static method is used to check if a specified password is valid.
     * @param password the password to be checked
     * @return the specified password if successfully checked
     * @throws InvalidPasswordFormatException is thrown if the password is not valid
     */
    public static String checkIfPasswordIsValid(final @NotNull String password) throws InvalidPasswordFormatException {

        if(password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidPasswordFormatException("Password must contain at least "
                    + MINIMUM_PASSWORD_LENGTH + " characters.");
        }

        return checkIfInstanceIsValid(
                password,
                PASSWORD_POLICY,
                new InvalidPasswordFormatException("Password is not safe. Enter a new password")
        );
    }

    /**
     * This static method is used to check if a specified {@link #birthDate} is legal.
     *
     * @param birthDate the specified birthdate to be checked
     * @return the specified birthdate if successfully checked
     * @throws IllegalBirthdateException is thrown if the birthdate is not legal
     */
    public static Date checkIfBirthdateIsLegal(Date birthDate) throws IllegalBirthdateException {
        return null;
        //TODO redo this
        /*
        return check_if_instance_is_valid(
                birth_date,
                AGE_REQUIREMENTS,
                new IllegalBirthdateException("Age requirements not met")
        );

         */
    }

    /**
     * This static method is used to check if a specified {@link #phoneNumber} is in a valid format.
     *
     * @param phone_number the specified phone number to be checked
     * @return the specified phone number if successfully checked
     * @throws InvalidPhoneNumberFormatException is thrown if the phone number is not in a valid format
     */
    public static @Nullable String check_if_phone_number_is_in_valid_format(@Nullable String phone_number)
            throws InvalidPhoneNumberFormatException
    {
        // This null case must be explicitly handled to avoid NullPointerExceptions when calling replaceAll().
        if(phone_number == null || phone_number.isBlank() || phone_number.isEmpty() ) return null;

        return checkIfInstanceIsValid(
                phone_number,
                PHONE_NUMBER_FORMAT,
                new InvalidPhoneNumberFormatException(phone_number)
        ).replaceAll("\\s", ""); // removes all whitespaces used for the number
    }

    // ::::::::::::::::::::::::::::::::::::::::::: METHODS ::::::::::::::::::::::::::::::::::::::::::::

    protected void setStatus(AccountStatus status) {
        this.status = status;
    }

    protected AccountStatus getStatus() {
        return status;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) throws DebtLimitExceedingException {
        if(saldo < debtLimit) {
            throw new DebtLimitExceedingException("Balance should not exceed the debt limit. " +
                    "Current debt limit: " + debtLimit + " ; Balance after setting to new value: " + saldo );
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
        this.email = checkIfEmailIsInValidFormat(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidPasswordFormatException {
        this.password = checkIfPasswordIsValid(password);
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InvalidNameFormatException {
        this.lastName = checkIfNameIsInValidFormat(lastName);
    }

    public String getFirstName() {
        return firstName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setDebt_limit(double debt_limit) {
        // The debt limit should always be a negative value.
        if(debt_limit > 0) {
            this.debtLimit = -debt_limit;
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
}
