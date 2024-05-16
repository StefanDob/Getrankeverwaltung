package de.tu.darmstadt.backend.exceptions.accountPolicy;

import de.tu.darmstadt.dataModel.Account;

/**
 * An {@link InvalidPhoneNumberFormatException} is a subclass of {@link AccountPolicyException}. It is a superclass
 * of those checked exceptions that is thrown if a phone number of an {@link Account} is not in a valid format.
 */
public class InvalidPhoneNumberFormatException extends AccountPolicyException {

    /**
     * Constructs a new {@link InvalidPhoneNumberFormatException} with a specified phone number as its detail message.
     * @param phoneNumber the specified phone number as {@link String}
     */
    public InvalidPhoneNumberFormatException(String phoneNumber) {
        super("Invalid phone number format: "
                + phoneNumber
                + ". A phone number must only contain numbers and may contain white spaces for readability.");
    }

}
