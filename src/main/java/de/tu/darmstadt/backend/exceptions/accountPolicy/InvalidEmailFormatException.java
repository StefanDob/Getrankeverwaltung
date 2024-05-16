package de.tu.darmstadt.backend.exceptions.accountPolicy;

import de.tu.darmstadt.dataModel.Account;

/**
 * An {@link InvalidEmailFormatException} is a subclass of {@link AccountPolicyException}. It is a superclass of those
 * checked exceptions that is thrown if the email of an {@link Account} does not meet the requirements specified
 * in the {@link Account} policy.
 */
public class InvalidEmailFormatException extends AccountPolicyException {

    /**
     * Constructs a new {@link InvalidEmailFormatException} with a specified email that may be invalid. It has
     * a detail message in the following format:
     * <p>
     *     {@code "Invalid email format: <email>"}
     *
     * @param email the specified email that may be invalid
     */
    public InvalidEmailFormatException(String email) {
        super("Invalid email format: " + email);
    }

}
