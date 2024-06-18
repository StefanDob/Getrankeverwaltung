package de.tu.darmstadt.backend;

import de.tu.darmstadt.dataModel.Account;

public enum AccountStatus {

    /**
     * The default status of an {@link Account}.
     */
    STANDARD("Standard"),

    /**
     * A privileged {@link Account} is allowed to alter the {@link AccountStatus} of other {@link AccountStatus} and
     * to perform privileged tasks.
     */
    PRIVILEGED("Privileged"),

    /**
     * An {@link Account} is restricted if a condition required by an ice cream shop is not met yet.
     */
    RESTRICTED("Restricted"),

    /**
     * A closed {@link Account} cannot be logged in anymore, e.g., due to terms of use violations.
     */
    CLOSED("Closed");


    /**
     * This attribute stores the name of an enum.
     */
    private final String name;

    /**
     * Constructs a new {@link AccountStatus} with a specified name.
     * @param s the specified name
     */
    AccountStatus(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}
