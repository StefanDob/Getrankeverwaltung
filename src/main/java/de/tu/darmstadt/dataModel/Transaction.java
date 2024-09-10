package de.tu.darmstadt.dataModel;

import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * A transaction is a process where money is transferred from an {@link Account} to another {@link Account}.
 * This class is used to store the transaction history in a database.
 * Changed the name of the table transaction to transfer to not make it less
 * difficult for jpa to access the table
 */
@Entity
@Table(name = "transfer")
public class Transaction {

    /**
     * The ID of the transaction. It is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The sender of this {@link Transaction}.
     */
    @Column(name = "sender", nullable = false)
    private Long sender;

    /**
     * The receiver of this {@link Transaction}.
     */
    @Column(name = "receiver", nullable = false)
    private Long receiver;

    /**
     * The amount of money that is transferred during this {@link Transaction}.
     */
    @Column(name = "amount", nullable = false)
    private double amount;

    /**
     * The timestamp at which the {@link Transaction} is performed.
     */
    @Column(name = "transaction_Date")
    private LocalDateTime transactionDate;

    /**
     * The optional text of this {@link Transaction}. By default, this attribute is never null and stores
     * an empty {@link String}.
     */
    @NotNull
    @Column(name = "transaction_Text")
    private String transactionText = "";


    public Transaction() {
        // DO NOT REMOVE THIS DEFAULT CONSTRUCTOR AND DO NOT ADD ANYTHING TO IT !!!
    }

    /**
     * Constructs a new {@link Transaction} with a specified sender, receiver, a specified amount of money, a
     * specified transaction date and an optional transaction text.
     * @param sender the specified sender
     * @param receiver the specified receiver
     * @param amount the specified amount transferred
     * @param transactionDate the specified transaction date
     * @param transactionText the optional transaction text
     */
    public Transaction(final Long sender, final Long receiver, final double amount,
                       final LocalDateTime transactionDate, final @Nullable String transactionText)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.transactionDate = transactionDate;
        setTransactionText(transactionText);
    }

    /**
     * Constructs a new {@link Transaction} with a specified sender, receiver, a specified amount of money, a
     * specified transaction date and null as its optional transaction text.
     * @param sender the specified sender
     * @param receiver the specified receiver
     * @param amount the specified amount transferred
     * @param transactionDate the specified transaction date
     */
    public Transaction(Long sender, Long receiver, double amount, LocalDateTime transactionDate)
    {
        this(sender, receiver, amount, transactionDate, null);
    }

    // :::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::::

    public Long getSender() {
        return sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setSender(final Long sender) {
        this.sender = sender;
    }

    public void setReceiver(final Long receiver) {
        this.receiver = receiver;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public long getId(){
        return id;
    }

    public @NotNull String getTransactionText()
    {
        return transactionText;
    }

    /**
     * Sets the {@link #transactionText} to a new specified text. If the text is {@code null}, the attribute
     * {@link #transactionText} is set to an empty {@link String} instead of {@link null}
     *
     * @param transactionText the new transaction text
     */
    public void setTransactionText(final @Nullable String transactionText)
    {
        this.transactionText =
                transactionText == null || transactionText.isBlank() || transactionText.isEmpty()
                        ? "" : transactionText.trim(); // trim() removes all leading and trailing whitespaces
    }

    public String getSenderName() {
        try {
            Account account  = AccountOperations.getAccountByID(sender);
            return account.getFirstName() + " " + account.getLastName();
        } catch (AccountPolicyException e) {
            throw new RuntimeException(e);
        }
    }

    public String getReceiverName() {
        try {
            Account account  = AccountOperations.getAccountByID(receiver);
            return account.getFirstName() + " " + account.getLastName();
        } catch (AccountPolicyException e) {
            throw new RuntimeException(e);
        }
    }
}
