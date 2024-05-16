package de.tu.darmstadt.backend;

// DO NOT REMOVE ANY IMPORTED CLASSES !!!
import de.tu.darmstadt.dataModel.Account;
import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * This class is used to specify the properties of an ice cream shop.
 */
public abstract class IceCreamShopProperties {

    /**
     * The constructor of {@link IceCreamShopProperties} is hidden with the qualifier {@code private} in order
     * not to be instantiated.
     */
    private IceCreamShopProperties() {
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
     * This attribute specifies the password policies for creating a new {@link Account} or changing the password of an
     * existing {@link Account}.
     */
    public static final Predicate<? super String> PASSWORD_POLICY = s -> s.length() >= 6;


    /**
     * This attribute specified the age requirements for creating a new {@link Account}.
     */
    public static final Predicate<? super LocalDate> AGE_REQUIREMENTS
            = date -> Period.between(date, LocalDate.now()).getYears() >= 16;


    /**
     * This attribute checks if an email for an {@link Account} is in a valid format.
     */
    public static final Predicate<? super String> EMAIL_FORMAT =
            s -> {
                String pattern = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}";
                return Pattern.matches(pattern, s);
            };


    /**
     * Specifies the properties for a name. A name must only consist of A-Z, a-z or German umlauts.
     */
    public static final Predicate<? super String> VALID_NAME =
            s -> {
                for(int i = 0 ; i < s.length() ; i++) {
                    char c = s.charAt(i);
                    if( !(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || isUmlaut(c)) ) {
                        return false;
                    }
                }

                return true;
            };

    /**
     * Specifies the phone number format. A phone number must only have numbers and empty spaces.
     */
    public static final Predicate<? super String> PHONE_NUMBER_FORMAT =
            phone_number -> {
                for (int i = 0; i < phone_number.length(); i++) {
                    char c = phone_number.charAt(i);
                    if( !(c >= '0' && c <= '9' || c == ' ') ) {
                        return false;
                    }

                }
                return true;
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
