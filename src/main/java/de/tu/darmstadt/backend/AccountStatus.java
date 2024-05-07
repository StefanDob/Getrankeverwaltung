package de.tu.darmstadt.backend;

import de.tu.darmstadt.dataModel.Account;

public enum AccountStatus {

    /**
     * The default status of an {@link Account}.
     */
    STANDARD,

    /**
     * A privileged {@link Account} is allowed to alter the {@link AccountStatus} of other {@link AccountStatus} and
     * to perform privileged tasks.
     */
    PRIVILEGED,

    /**
     * An {@link Account} is restricted if a condition required by an ice cream shop is not met yet.
     */
    RESTRICTED,

    /**
     * A closed {@link Account} cannot be logged in anymore, e.g., due to terms of use violations.
     */
    CLOSED


}
