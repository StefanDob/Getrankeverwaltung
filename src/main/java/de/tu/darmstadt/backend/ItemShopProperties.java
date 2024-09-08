package de.tu.darmstadt.backend;

// DO NOT REMOVE ANY IMPORTED CLASSES !!!
import de.tu.darmstadt.dataModel.Account;
import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * This class is used to specify the properties of an ice cream shop.
 * //TODO @Toni this class seems to be used in the code, but change it to not include anything about an ice cream shop
 */
public final class ItemShopProperties {

    /**
     * The constructor of {@link ItemShopProperties} is hidden with the qualifier {@code private} in order
     * not to be instantiated.
     */
    private ItemShopProperties() {
        throw new RuntimeException("Class should not be instantiated");
    }

    // ::::::::::::::::::::::::::::::::::::: ATTRIBUTES ::::::::::::::::::::::::::::::::::::::::

    /**
     * The prefix of a data source URL used in this project.
     */
    private static final String URL_PREFIX = "jdbc:sqlite:";

    /**
     * The offset of a data source URL used in this project.
     */
    private static final String URL_OFFSET = "src\\main\\resources\\DataBase";

    /**
     * The URL of the currently used data source.
     */
    public static final String DATA_SOURCE_URL = URL_PREFIX + URL_OFFSET;

    /**
     * Specifies the minimum length of an {@link Account} password.
     */
    public static final int MINIMUM_PASSWORD_LENGTH = 8;


    // :::::::::::::::::::::::::::::::::::: PREDICATES ::::::::::::::::::::::::::::::::::::::::


    /**
     * This attribute specifies the password policies for creating a new {@link Account} or changing the password of an
     * existing {@link Account}.
     */
    public static final Predicate<? super String> PASSWORD_POLICY = s -> {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return s.length() >= MINIMUM_PASSWORD_LENGTH;
    };



    /**
     * This attribute specified the age requirements for creating a new {@link Account}.
     */
    public static final Predicate<? super LocalDate> AGE_REQUIREMENTS = date -> {
        if(date == null) {
            return false;
        }
        return Period.between(date, LocalDate.now()).getYears() >= 16;
    };



    /**
     * This attribute checks if an email for an {@link Account} is in a valid format.
     */
    public static final Predicate<? super String> EMAIL_FORMAT =
            s -> {

                if( s == null || s.isEmpty() || s.isBlank()) {
                    return false;
                } // end of if

                // The format (1st argument) that the email should have.
                return Pattern.matches("[a-zA-Z0-9-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,4}", s);
            };


    /**
     * Specifies the properties for a name. A name must only consist of A-Z, a-z, German umlauts, white spaces or '-'.
     */
    public static final Predicate<? super String> VALID_NAME =
            s -> {

                if(s == null || s.isEmpty() || s.isBlank()) {
                    return false;
                } // end of if

                for(int i = 0 ; i < s.length() ; i++) {
                    char c = s.charAt(i);
                    if( !(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'
                            || isUmlaut(c) || Character.isWhitespace(c) || c == '-') ) {
                        return false;
                    } // end of if
                } // end of for

                return true;
            };

    /**
     * Specifies the phone number format. A phone number must only have numbers and empty spaces.
     */
    public static final Predicate<? super String> PHONE_NUMBER_FORMAT =
            phone_number -> {

                // If the number is null, blank or empty, it is treated like an empty number
                if(phone_number == null || phone_number.isEmpty() || phone_number.isBlank()) {
                    return true;
                }

                // Replaces all white spaces first
                phone_number = phone_number.replaceAll("\\s", "");

                for (int i = 0; i < phone_number.length(); i++) {

                    // A phone number may begin with a '+'.
                    if(i == 0 && phone_number.charAt(i) == '+') {

                        // Checks if the phone number only consists of a '+'.
                        // In this case, the phone number format is not valid.
                        if( phone_number.length() == 1 ) {
                            return false;
                        }

                        continue;
                    }

                    char c = phone_number.charAt(i);
                    if( !( Character.isDigit(c) || Character.isSpaceChar(c) ) ) {
                        return false;
                    }

                }
                return true;
            };


    /**
     * Specifies the ID format for an {@link Account}. An ID must be in the following format:
     * <p>
     *     {@code XXXXX-XXXXX-XXXXX-XXXXX-XXXXX}
     */
    public static final Predicate<? super String> VALID_ACCOUNT_ID_FORMAT =
            id -> {
                // The format (1st argument) that the tested ID should have.
                return Pattern
                        .matches("[0-9A-Za-z]{5}-[0-9A-Za-z]{5}-[0-9A-Za-z]{5}-[0-9A-Za-z]{5}-[0-9A-Za-z]{5}",
                                id);
            };


    /**
     * The default debt limit of any account. This value specifies that a specified account never exceeds the specified
     * number of debts.
     */
    public static final double DEFAULT_DEBT_LIMIT = -200;



    // ::::::::::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::::::::

    /**
     * Checks if a specified character is a German umlaut (Ä, ä, Ö, ö, Ü, ü, ß).
     *
     * @param ch the specified character to be checked.
     * @return true if the specified character is an umlaut.
     */
    private static boolean isUmlaut(char ch) {
        return switch (ch) {
            case 'Ä', 'Ö', 'Ü', 'ä', 'ö', 'ü', 'ß' -> true;
            default -> false;
        };
    }

}
