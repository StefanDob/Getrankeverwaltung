package de.tu.darmstadt.dataModel;

// DO NOT REMOVE ANY IMPORTED PACKAGES !!!
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.ItemShopProperties;
import de.tu.darmstadt.backend.exceptions.accountPolicy.*;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

import static de.tu.darmstadt.backend.ItemShopProperties.*;
import static de.tu.darmstadt.ProjectUtils.*;
import static de.tu.darmstadt.dataModel.ExceptionChecker.*;

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



    public static void main(String[] args) {
        try {
            for(int i = 0 ; i < 50 ; i++)
                System.out.println(generateID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This immutable attribute is an ID of {@link Account}. An ID is a unique attribute that can clearly identify
     * an {@link Account} without any need of other attributes.
     * <p>
     *     TODO: TO BE IMPLEMENTED !!!
     */
    private final String id_of_account = "";

    /**
     * The email, that is the primary key of {@link Account}, is used to register and to login into the account.
     */
    @Id
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * The password of the {@link Account}.
     * It must meet the requirements specified in the {@link ItemShopProperties}
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
    //@Column(name = "birth_date", nullable = false)
    //private LocalDate birth_date;

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


    // ::::::::::::::::::::::::::::::::::::::::: CONSTRUCTORS :::::::::::::::::::::::::::::::::::::::::::


    /**
     * A default constructor is used for the entity class.
     */
    public Account() {
        // DO NOT REMOVE THIS CONSTRUCTOR AND DO NOT ADD ANYTHING TO IT !!!
    }

    /**
     * Constructs a new {@link AccountStatus#STANDARD standard} {@link Account} with specified personal data.
     * @param email the specified email
     * @param password the specified password
     * @param first_name the specified first name
     * @param last_name the specified last name
     * @param birth_date the specified birthdate
     * @param phone_number the specified phone number (optional). May be {@code null}
     *
     * @throws AccountPolicyException is thrown if any personal data does not meet the {@link Account}
     * {@link ItemShopProperties policies}
     */
    public Account(String email, String password, String first_name, String last_name,
                   LocalDate birth_date, String phone_number)
            throws AccountPolicyException
    {
        this(email, password, first_name, last_name, birth_date, phone_number,
                AccountStatus.STANDARD, DEFAULT_DEBT_LIMIT);
    }


    /**
     * Constructs a new {@link Account} with specified personal data.
     * @param email the specified email
     * @param password the specified password
     * @param first_name the specified first name
     * @param last_name the specified last name
     * @param birth_date the specified birthdate
     * @param phone_number the specified phone number (optional)
     * @param status the specified {@link AccountStatus}
     * @param debt_limit the specified debt limit
     *
     * @throws AccountPolicyException is thrown if any data does not meet the requirements specified in the policies.
     */
    public Account(@NotNull String email, @NotNull String password, String first_name, String last_name,
                   LocalDate birth_date, @Nullable String phone_number, AccountStatus status, double debt_limit

    ) throws AccountPolicyException
    {
        this.first_name = check_if_name_is_in_valid_format(first_name);
        this.last_name = check_if_name_is_in_valid_format(last_name);
        this.email = check_if_email_is_in_valid_format(email);
        this.password = check_if_password_is_valid(password);
       // this.birth_date = check_if_birthdate_is_legal(birth_date);
        this.phone_number = check_if_phone_number_is_in_valid_format(phone_number);

        // The debt limit should always be a negative value.
        if(debt_limit > 0) {
            this.debt_limit = -debt_limit;
        } else {
            this.debt_limit = debt_limit;
        }

        this.status = status;
    }

    // :::::::::::::::::::::::::::::::::::::: AUXILIARY METHODS :::::::::::::::::::::::::::::::::::::::

    /**
     * TODO: Implement. This method should check if an ID is already existing in the database!!!
     * This static method generates an {@link #id_of_account ID} as a {@link String} value for the {@link Account}.
     *
     * @return the ID generated for the {@link Account}
     */
    private static @NotNull String generateID() throws AccountPolicyException {

        // if(true) throw new RuntimeException("This static method is under construction and should not be called yet!");


        final char[] ch_set0 = new char[]{'a', 'z'}; // Set of chars: {'a', 'b', ... , 'z'}
        final char[] ch_set1 = new char[]{'A', 'Z'};
        final char[] ch_set2 = new char[]{'0', '9'};

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

        if( !VALID_ID_FORMAT.test(resulting_ID) ) {
            throw new AccountPolicyException("ID is not in a valid format: " + resulting_ID);
        } // end of if

        return resulting_ID;
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
    private static String check_if_name_is_in_valid_format(String name) throws InvalidNameFormatException {
        return check_if_instance_is_valid(
                name,
                VALID_NAME,
                new InvalidNameFormatException("Only a-z or A-Z, as well as '-' and white spaces are allowed in names.")
        );
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
    private static String check_if_email_is_in_valid_format(String email) throws InvalidEmailFormatException {
        return check_if_instance_is_valid(
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
    private static String check_if_password_is_valid(String password) throws InvalidPasswordFormatException {
        return check_if_instance_is_valid(
                password,
                PASSWORD_POLICY,
                new InvalidPasswordFormatException("Password is not safe. Enter a new password")
        );
    }

    /**
     * This static method is used to check if a specified {@link #birth_date} is legal.
     *
     * @param birth_date the specified birthdate to be checked
     * @return the specified birthdate if successfully checked
     * @throws IllegalBirthdateException is thrown if the birthdate is not legal
     */
    private static LocalDate check_if_birthdate_is_legal(LocalDate birth_date) throws IllegalBirthdateException {
        return check_if_instance_is_valid(
                birth_date,
                AGE_REQUIREMENTS,
                new IllegalBirthdateException("Age requirements not met")
        );
    }

    /**
     * This static method is used to check if a specified {@link #phone_number} is in a valid format.
     *
     * @param phone_number the specified phone number to be checked
     * @return the specified phone number if successfully checked
     * @throws InvalidPhoneNumberFormatException is thrown if the phone number is not in a valid format
     */
    private static @Nullable String check_if_phone_number_is_in_valid_format(@Nullable String phone_number)
            throws InvalidPhoneNumberFormatException
    {
        // This null case must be explicitly handled to avoid NullPointerExceptions when calling replaceAll().
        if(phone_number == null) return null;

        return check_if_instance_is_valid(
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) throws DebtLimitExceedingException {
        if(balance < debt_limit) {
            throw new DebtLimitExceedingException("Balance should not exceed the debt limit. " +
                    "Current debt limit: " + debt_limit + " ; Balance after setting to new value: " + balance );
        } // end of if

        this.balance = balance;
    }

    public double getDebt_limit() {
        return debt_limit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidEmailFormatException {
        this.email = check_if_email_is_in_valid_format(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidPasswordFormatException {
        this.password = check_if_password_is_valid(password);
    }
/*
    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) throws IllegalBirthdateException {
        this.birth_date = check_if_birthdate_is_legal(birth_date);
    }

 */

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) throws InvalidNameFormatException {
        this.last_name = check_if_name_is_in_valid_format(last_name);
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) throws InvalidNameFormatException {
        this.first_name = check_if_name_is_in_valid_format(first_name);
    }

    public @Nullable String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(@Nullable String phone_number) throws InvalidPhoneNumberFormatException {

        if(phone_number == null) {
            // This null case must be explicitly handled to avoid NullPointerExceptions when calling replaceAll().
            this.phone_number = null;
        } else {
            this.phone_number = check_if_phone_number_is_in_valid_format(phone_number)
                    .replaceAll("\\s", ""); // removes all whitespaces
        }
    }

    public void setDebt_limit(double debt_limit) {
        // The debt limit should always be a negative value.
        if(debt_limit > 0) {
            this.debt_limit = -debt_limit;
        } else {
            this.debt_limit = debt_limit;
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
        return '[' + email + ": " + last_name + ", " + first_name + "]";
    }



}
