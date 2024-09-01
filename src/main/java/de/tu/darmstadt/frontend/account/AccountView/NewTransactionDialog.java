package de.tu.darmstadt.frontend.account.AccountView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.backendService.TransactionOperations;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;

import java.time.LocalDateTime;

public class NewTransactionDialog extends Dialog {

    Account currentAccount;

    private TextField receiverField;
    private NumberField amountField;
    private TextField transactionTextField;

    public NewTransactionDialog(Account currentAccount) {
        this.currentAccount = currentAccount;
        // Initialize components
        receiverField = new TextField("Receiver");
        amountField = new NumberField("Amount");
        transactionTextField = new TextField("Transaction Text");

        receiverField.setWidth("35vw");
        amountField.setWidth("35vw");
        transactionTextField.setWidth("35vw");

        // Configure NumberField to accept only positive numbers
        amountField.setMin(0);

        // Create buttons
        Button saveButton = new Button("Save", event -> saveTransaction());
        Button cancelButton = new Button("Cancel", event -> close());

        // Layout for form fields
        VerticalLayout formLayout = new VerticalLayout(receiverField, amountField, transactionTextField);

        // Layout for buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // Add components to the dialog
        add(formLayout, buttonLayout);

        // Set dialog properties
        setWidth("40vw");
        setHeight("60vh");
    }

    private void saveTransaction() {
        String receiver = receiverField.getValue();
        Double amount = amountField.getValue();
        String transactionText = transactionTextField.getValue();

        if (receiver.isEmpty() || amount == null || transactionText.isEmpty()) {
            Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
            return;
        }

        long receiverId = AccountOperations.getAccountByEmail(receiver).getId();

        Transaction transaction = new Transaction(currentAccount.getId(), receiverId, amount, LocalDateTime.now(),transactionText);
        TransactionOperations.addTransaction(transaction);

        Notification.show("Transaction commited", 3000, Notification.Position.MIDDLE);
        UI.getCurrent().getPage().reload();
        // Close the dialog after saving
        close();
    }
}
