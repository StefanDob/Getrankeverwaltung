package de.tu.darmstadt.dataModel;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * A transaction is a process where money is transferred from an {@link Account} to another {@link Account}.
 * This class is used to store the transaction history in a database.
 */
public class Transaction {

    /**
     * The ID of the transaction.
     */
    private Long transactionID;

    /**
     * The sender of this {@link Transaction}.
     */
    private Long sender;

    /**
     * The receiver of this {@link Transaction}.
     */
    private Long receiver;

    /**
     * The amount of money that is transferred during this {@link Transaction}.
     */
    private double amount;

    /**
     * The timestamp at which the {@link Transaction} is performed.
     */
    private LocalDateTime transactionDate;

    /**
     * The optional text of this {@link Transaction}.
     */
    @Nullable
    private String transactionText;


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

    public Long getTransactionID()
    {
        return transactionID;
    }

    public @Nullable String getTransactionText()
    {
        return transactionText;
    }

    public void setTransactionText(final @Nullable String transactionText)
    {
        this.transactionText =
                transactionText == null || transactionText.isBlank() || transactionText.isEmpty()
                        ? null : transactionText.trim(); // trim() removes all leading and trailing whitespaces
    }
}
