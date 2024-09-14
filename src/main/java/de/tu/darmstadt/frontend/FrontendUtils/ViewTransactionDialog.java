package de.tu.darmstadt.frontend.FrontendUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Transaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ViewTransactionDialog extends Dialog {

    public ViewTransactionDialog(Transaction transaction) {
        setWidth("50vw");
        // Create divs for transaction data
        Div senderDiv = new Div();
        try {
            String senderName = AccountOperations.getAccountByID(transaction.getSender()).getFirstName() + " " +
                    AccountOperations.getAccountByID(transaction.getSender()).getLastName();
            senderDiv.setText(LanguageManager.getLocalizedText("Sender")  + ": " + senderName);
        } catch (AccountPolicyException e) {
            throw new RuntimeException(e);
        }

        Div receiverDiv = new Div();
        try {
            String receiverName = AccountOperations.getAccountByID(transaction.getReceiver()).getFirstName() + " " +
                    AccountOperations.getAccountByID(transaction.getReceiver()).getLastName();
            receiverDiv.setText(LanguageManager.getLocalizedText("Receiver") + ": " + receiverName);
        } catch (AccountPolicyException e) {
            receiverDiv.setText("Receiver: Error retrieving receiver information.");
        }

        // Format amount with two decimal places and euro sign
        Div amountDiv = new Div();
        amountDiv.setText(LanguageManager.getLocalizedText("Amount") + ": " + String.format("%.2f €", transaction.getAmount())); // Assuming getAmount() returns a numeric value

        // Format date to dd.MM.yyyy HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        Div dateDiv = new Div();
        dateDiv.setText(LanguageManager.getLocalizedText("Date") + ": " + transaction.getTransactionDate().format(formatter)); // Assuming getDate() returns a Date or LocalDate

        // Create a div for transaction text
        Div transactionTextDiv = new Div();
        transactionTextDiv.setText(LanguageManager.getLocalizedText("Transaction Text") + ": " + transaction.getTransactionText()); // Assuming getDescription() returns a string

        // Use a layout to organize the divs
        VerticalLayout divLayout = new VerticalLayout(senderDiv, receiverDiv, amountDiv, dateDiv, transactionTextDiv);

        // Create a button to close the dialog
        Button closeButton = new Button(LanguageManager.getLocalizedText("Close"), event -> this.close());

        // Add everything to the dialog layout
        VerticalLayout layout = new VerticalLayout(divLayout, closeButton);
        layout.setPadding(false);  // Optional: adjust padding/margins as needed

        add(layout);


    }
}


