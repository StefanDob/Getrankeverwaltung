package de.tu.darmstadt.dataModel;

// DO NOT REMOVE ANY IMPORTED PACKAGES !!!
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.ItemShopProperties;
import de.tu.darmstadt.backend.exceptions.accountPolicy.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

import static de.tu.darmstadt.backend.ItemShopProperties.*;
import static de.tu.darmstadt.ProjectUtils.*;

/**
 * An {@link Account} for the drink shop is a superclass of those classes that grants a user of the drink shop an
 * environment for performing {@link Item} purchases.
 * <p>
 * Each account is clearly identified by a set of attributes (primary key) that is
 * specified by the user when creating an {@link Account}.
 */
@Entity
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
    @Column(name = "email", nullable = false)
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
     * {@link ItemShopProperties policies}
     */
    public Account(String email, String password, String first_name, String last_name,
                   LocalDate birth_date, String phone_number)
            throws AccountPolicyException
    {
        this(email, password, first_name, last_name, birth_date, phone_number,
                AccountStatus.STANDARD, DEFAULT_DEBT_LIMIT);
    }


    public Account(@NotNull String email, @NotNull String password, String first_name, String last_name,
                   LocalDate birth_date, @Nullable String phone_number, AccountStatus status, double debt_limit

                   ) throws AccountPolicyException
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

    // :::::::::::::::::::::::::::::::::::::: AUXILIARY METHODS :::::::::::::::::::::::::::::::::::::::

    /**
     * TODO: Implement. This method should check if an ID is already existing in the database!!!
     * This static method generates an {@link #id_of_account ID} as a {@link String} value for the {@link Account}.
     *
     * @return the ID generated for the {@link Account}
     */
    private static String generateID() throws AccountPolicyException {

        if(true) throw new RuntimeException("This static method is under construction and should not be called yet!");


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

    public void setEmail(String email) throws InvalidEmailFormatException {
        if(EMAIL_FORMAT.test(email)) {
            throw new InvalidEmailFormatException(email);
        } // end of if

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidPasswordFormatException {
        if(PASSWORD_POLICY.test(password)) {
            throw new InvalidPasswordFormatException("Password is not in a valid format");
        } // end of if

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

    public void setPhone_number(@Nullable String phone_number) throws InvalidPhoneNumberFormatException {
        if(!PHONE_NUMBER_FORMAT.test(phone_number)) {
            throw new InvalidPhoneNumberFormatException(phone_number);
        } // end of if

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
