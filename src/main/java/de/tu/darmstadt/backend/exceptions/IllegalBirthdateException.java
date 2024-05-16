package de.tu.darmstadt.backend.exceptions;

import de.tu.darmstadt.dataModel.Account;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

/**
 * An {@link IllegalBirthdateException} is a subclass of {@link AccountPolicyException}. It is a superclass of those
 * checked exceptions that is thrown if a birthdate does not meet the requirements specified by the {@link Account}
 * policy.
 */
public class IllegalBirthdateException extends AccountPolicyException {

    /**
     * Constructs a new {@link IllegalBirthdateException} with a specified {@link LocalDate birthdate}.
     * The detail message of this {@link IllegalBirthdateException} is in the following format:
     * <p>
     *
     * {@code "Invalid birthdate: yyyy-mm-dd"}
     * @param birthdate the specified birthdate
     */
    public IllegalBirthdateException(@NotNull LocalDate birthdate) {
        super("Invalid birthdate: " + birthdate.toString());
    }

    /**
     * @see AccountPolicyException#AccountPolicyException(String)
     */
    public IllegalBirthdateException(String message) {
        super(message);
    }

    /**
     * @see AccountPolicyException#AccountPolicyException()
     */
    public IllegalBirthdateException() {
        super();
    }

}
