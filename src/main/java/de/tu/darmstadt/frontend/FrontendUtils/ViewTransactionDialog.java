package de.tu.darmstadt.frontend.FrontendUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendOperations.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Transaction;

import java.time.format.DateTimeFormatter;

/**
 * A dialog for displaying detailed information about a transaction.
 * This dialog shows the sender, receiver, amount, date, and transaction text, with formatted output.
 */
public class ViewTransactionDialog extends Dialog {

    /**
     * Constructs a new ViewTransactionDialog for a given transaction.
     *
     * @param transaction The transaction whose details will be displayed.
     */
    public ViewTransactionDialog(Transaction transaction) {
        setWidth("50vw");

        // Create a div for the sender information
        Div senderDiv = new Div();
        try {
            // Retrieve and display the sender's full name
            String senderName = AccountOperations.getAccountByID(transaction.getSender()).getFirstName() + " " +
                    AccountOperations.getAccountByID(transaction.getSender()).getLastName();
            senderDiv.setText(LanguageManager.getLocalizedText("Sender") + ": " + senderName);
        } catch (AccountPolicyException e) {
            throw new RuntimeException(e); // Handle case where the sender cannot be retrieved
        }

        // Create a div for the receiver information
        Div receiverDiv = new Div();
        try {
            // Retrieve and display the receiver's full name
            String receiverName = AccountOperations.getAccountByID(transaction.getReceiver()).getFirstName() + " " +
                    AccountOperations.getAccountByID(transaction.getReceiver()).getLastName();
            receiverDiv.setText(LanguageManager.getLocalizedText("Receiver") + ": " + receiverName);
        } catch (AccountPolicyException e) {
            // Display an error message if the receiver cannot be retrieved
            receiverDiv.setText("Receiver: Error retrieving receiver information.");
        }

        // Create a div for the transaction amount, formatted with two decimal places and an euro sign
        Div amountDiv = new Div();
        amountDiv.setText(LanguageManager.getLocalizedText("Amount") + ": " + String.format("%.2f €", transaction.getAmount()));

        // Create a div for the transaction date, formatted as dd.MM.yyyy HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        Div dateDiv = new Div();
        dateDiv.setText(LanguageManager.getLocalizedText("Date") + ": " + transaction.getTransactionDate().format(formatter));

        // Create a div for the transaction text/description
        Div transactionTextDiv = new Div();
        transactionTextDiv.setText(LanguageManager.getLocalizedText("Transaction Text") + ": " + transaction.getTransactionText());

        // Use a VerticalLayout to organize the divs
        VerticalLayout divLayout = new VerticalLayout(senderDiv, receiverDiv, amountDiv, dateDiv, transactionTextDiv);

        // Create a button to close the dialog
        Button closeButton = new Button(LanguageManager.getLocalizedText("Close"), event -> this.close());

        // Add the div layout and the close button to the dialog layout
        VerticalLayout layout = new VerticalLayout(divLayout, closeButton);
        layout.setPadding(false);  // Optional: adjust padding as needed

        // Add the layout to the dialog
        add(layout);
    }
}



