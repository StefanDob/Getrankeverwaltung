package de.tu.darmstadt.backend.exceptions;

import de.tu.darmstadt.dataModel.Account;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link DebtLimitExceedingException} is a superclass of those checked exceptions that is thrown if an
 * {@link Account} exceeds its permitted debt limit.
 */
public class DebtLimitExceedingException extends Exception {


    public DebtLimitExceedingException(@NotNull Account account, double paymentAmount) {
        super("This payment is exceeding the debt limit. Current balance is "
                + account.getBalance() + " but payment amount is " + paymentAmount
                + ". Balance after purchase: " + account.getBalance() + ". "
                + "Your debt limit: " + account.getDebt_limit()
        );
    }

}
