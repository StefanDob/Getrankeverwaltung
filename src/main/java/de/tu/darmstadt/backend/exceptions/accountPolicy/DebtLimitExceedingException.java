package de.tu.darmstadt.backend.exceptions.accountPolicy;

import de.tu.darmstadt.dataModel.Account;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link DebtLimitExceedingException} is a subclass of {@link AccountPolicyException}. It is a subclass of those
 * checked exceptions that is thrown if an {@link Account} exceeds its permitted debt limit.
 */
public class DebtLimitExceedingException extends AccountPolicyException {

    /**
     * Constructs a new {@link DebtLimitExceedingException} with a specified {@link Account} and a specified amount of
     * money that the {@link Account} user attempted to pay with. It has a detail message in the following format:
     * <p>
     * {@code "This payment is exceeding the debt limit. Current balance is <Account#getBalance()>, but the amount of
     * money tried to spend with is <paymentAmount>. Balance after purchase: -XXX. Your debt limit is
     * <Account#getDebt_limit()>"}
     *
     * @param account the specified {@link Account}
     * @param paymentAmount the specified amount of money
     */
    public DebtLimitExceedingException(@NotNull Account account, double paymentAmount) {
        super("This payment is exceeding the debt limit. Current balance is "
                + account.getBalance() + ", but amount of money tried to spend with is " + paymentAmount
                + ". Balance after purchase: " + (account.getBalance() - paymentAmount) + ". "
                + "Your debt limit: " + account.getDebt_limit()
        );
    }

}
