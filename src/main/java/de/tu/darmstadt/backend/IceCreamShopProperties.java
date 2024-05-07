package de.tu.darmstadt.backend;



import de.tu.darmstadt.dataModel.Account;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * This class is used to specify the properties of an ice cream shop.
 */
public abstract class IceCreamShopProperties {

    private IceCreamShopProperties() {
        throw new RuntimeException("Class should not be instantiated");
    }

    // ::::::::::::::::::::::::::::::::::::: ATTRIBUTES ::::::::::::::::::::::::::::::::::::::::


    private static final String PREFIX = "jdbc:sqlite:";

    /**
     * The currently used data source.
     */
    public static final String DATA_SOURCE_URL = PREFIX + "src\\main\\resources\\DataBase";


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
     * Specifies the properties for a name. A name must only consist of A-Z or a-z.
     */
    public static final Predicate<? super String> VALID_NAME =
            s -> {
                for(int i = 0 ; i < s.length() ; i++) {
                    char c = s.charAt(i);
                    if( !(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') ) {
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




}
