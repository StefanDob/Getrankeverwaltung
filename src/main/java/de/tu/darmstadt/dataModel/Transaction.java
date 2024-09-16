package de.tu.darmstadt.dataModel;

import de.tu.darmstadt.backend.backendOperations.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * A {@link Transaction} represents a process where money is transferred from one {@link Account} to another.
 * This class is used to store the transaction history in the database. The table name is "transfer" to
 * avoid potential issues with JPA using "transaction" as the table name.
 */
@Entity
@Table(name = "transfer")
public class Transaction {

    /**
     * The ID of the {@link Transaction}. This field is automatically generated and serves as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The ID of the account sending the money in this {@link Transaction}.
     */
    @Column(name = "sender", nullable = false)
    private Long sender;

    /**
     * The ID of the account receiving the money in this {@link Transaction}.
     */
    @Column(name = "receiver", nullable = false)
    private Long receiver;

    /**
     * The amount of money transferred in this {@link Transaction}.
     */
    @Column(name = "amount", nullable = false)
    private double amount;

    /**
     * The date and time when this {@link Transaction} took place.
     */
    @Column(name = "transaction_Date")
    private LocalDateTime transactionDate;

    /**
     * The optional text description associated with this {@link Transaction}.
     * By default, it is never {@code null} and stores an empty {@link String} when no text is provided.
     */
    @NotNull
    @Column(name = "transaction_Text")
    private String transactionText = "";


    // :::::::::::::::::::::::::::::::::: CONSTRUCTORS ::::::::::::::::::::::::::::::::::::

    /**
     * Default constructor for JPA. This constructor should not be modified or extended.
     */
    public Transaction() {
        // Default constructor
    }

    /**
     * Constructs a new {@link Transaction} with a specified sender, receiver, amount, transaction date, and
     * optional transaction text.
     *
     * @param sender          the account ID of the sender
     * @param receiver        the account ID of the receiver
     * @param amount          the amount of money to be transferred
     * @param transactionDate the date and time of the transaction
     * @param transactionText optional description of the transaction
     */
    public Transaction(final Long sender, final Long receiver, final double amount,
                       final LocalDateTime transactionDate, final @Nullable String transactionText) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.transactionDate = transactionDate;
        setTransactionText(transactionText);
    }

    /**
     * Constructs a new {@link Transaction} with a specified sender, receiver, amount, and transaction date.
     * The transaction text is set to {@code null} by default.
     *
     * @param sender          the account ID of the sender
     * @param receiver        the account ID of the receiver
     * @param amount          the amount of money to be transferred
     * @param transactionDate the date and time of the transaction
     */
    public Transaction(Long sender, Long receiver, double amount, LocalDateTime transactionDate) {
        this(sender, receiver, amount, transactionDate, null);
    }


    // :::::::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::::::

    /**
     * Gets the ID of the sender's account in this {@link Transaction}.
     *
     * @return the sender's account ID
     */
    public Long getSender() {
        return sender;
    }

    /**
     * Gets the ID of the receiver's account in this {@link Transaction}.
     *
     * @return the receiver's account ID
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * Gets the amount of money transferred in this {@link Transaction}.
     *
     * @return the amount of money transferred
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the date and time when the {@link Transaction} took place.
     *
     * @return the transaction date
     */
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the ID of the sender's account in this {@link Transaction}.
     *
     * @param sender the new sender's account ID
     */
    public void setSender(final Long sender) {
        this.sender = sender;
    }

    /**
     * Sets the ID of the receiver's account in this {@link Transaction}.
     *
     * @param receiver the new receiver's account ID
     */
    public void setReceiver(final Long receiver) {
        this.receiver = receiver;
    }

    /**
     * Sets the amount of money transferred in this {@link Transaction}.
     *
     * @param amount the new amount of money to be transferred
     */
    public void setAmount(final double amount) {
        this.amount = amount;
    }

    /**
     * Sets the date and time when the {@link Transaction} took place.
     *
     * @param transactionDate the new transaction date
     */
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets the ID of this {@link Transaction}.
     *
     * @return the transaction ID
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the optional text description of this {@link Transaction}.
     *
     * @return the transaction text
     */
    public @NotNull String getTransactionText() {
        return transactionText;
    }

    /**
     * Sets the transaction text to a new value. If the provided text is {@code null}, an empty string is set instead.
     *
     * @param transactionText the new transaction text
     */
    public void setTransactionText(final @Nullable String transactionText) {
        this.transactionText = (transactionText == null || transactionText.isBlank()) ? "" : transactionText.trim();
    }

    /**
     * Retrieves the full name of the sender associated with this transaction.
     *
     * @return the sender's full name (first and last name)
     */
    public String getSenderName() {
        try {
            Account account = AccountOperations.getAccountByID(sender);
            return account.getFirstName() + " " + account.getLastName();
        } catch (AccountPolicyException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Retrieves the full name of the receiver associated with this transaction.
     *
     * @return the sender's full name (first and last name)
     */
    public String getReceiverName() {
        try {
            Account account = AccountOperations.getAccountByID(receiver);
            return account.getFirstName() + " " + account.getLastName();
        } catch (AccountPolicyException e) {
            throw new RuntimeException(e);
        }
    }
}