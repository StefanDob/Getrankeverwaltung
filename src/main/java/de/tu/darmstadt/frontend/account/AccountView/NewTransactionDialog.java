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
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.backendService.TransactionOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
import de.tu.darmstadt.Utils.SessionManagement;

import java.time.LocalDateTime;

/**
 * NewTransactionDialog provides a form for creating a new transaction.
 * The dialog collects information like receiver, amount, and transaction text,
 * and validates the data before committing the transaction.
 */
public class NewTransactionDialog extends Dialog {

    private final Account currentAccount;
    private final TextField receiverField;
    private final NumberField amountField;
    private final TextField transactionTextField;

    /**
     * Constructor for NewTransactionDialog.
     * Initializes the dialog with the current account information and sets up the form components.
     *
     * @param currentAccount The account from which the transaction is being made.
     */
    public NewTransactionDialog(Account currentAccount) {
        this.currentAccount = currentAccount;

        // Initialize form fields
        receiverField = new TextField(LanguageManager.getLocalizedText("Receiver"));
        amountField = new NumberField(LanguageManager.getLocalizedText("Amount"));
        transactionTextField = new TextField(LanguageManager.getLocalizedText("Transaction Text"));

        setupFormFields();
        addComponentsToDialog();
        setDialogProperties();
    }

    /**
     * Sets up form field properties, such as width and minimum amount constraints.
     */
    private void setupFormFields() {
        receiverField.setWidth("35vw");
        amountField.setWidth("35vw");
        transactionTextField.setWidth("35vw");

        // Configure NumberField to accept only positive numbers
        amountField.setMin(0);
    }

    /**
     * Adds the form fields and buttons to the dialog layout.
     */
    private void addComponentsToDialog() {
        // Layout for form fields
        VerticalLayout formLayout = new VerticalLayout(receiverField, amountField, transactionTextField);

        // Create buttons
        Button saveButton = new Button(LanguageManager.getLocalizedText("Save"), event -> saveTransaction());
        Button cancelButton = new Button(LanguageManager.getLocalizedText("Cancel"), event -> close());

        // Layout for buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // Add components to the dialog
        add(formLayout, buttonLayout);
    }

    /**
     * Sets dialog properties like size and other visual configurations.
     */
    private void setDialogProperties() {
        setWidth("40vw");
        setHeight("60vh");
    }

    /**
     * Saves the transaction after validating the input data.
     * Updates the current account and reloads the page upon success.
     */
    private void saveTransaction() {
        if (!validateTransactionData()) {
            return;
        }

        try {
            // Get receiver account and create a transaction
            long receiverId = AccountOperations.getAccountByEmail(receiverField.getValue()).getId();
            Transaction transaction = new Transaction(currentAccount.getId(), receiverId, amountField.getValue(), LocalDateTime.now(), transactionTextField.getValue());
            TransactionOperations.addTransaction(transaction);

            // Notify the user of a successful transaction
            Notification.show(LanguageManager.getLocalizedText("Transaction committed"), 3000, Notification.Position.MIDDLE);

            // Update current account and refresh the UI
            SessionManagement.setAccount(AccountOperations.getAccountByID(currentAccount.getId()));
            UI.getCurrent().getPage().reload();

            // Close the dialog after saving
            close();
        } catch (AccountPolicyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates the transaction data entered by the user.
     * Ensures that fields are filled and that the current account has sufficient balance.
     *
     * @return true if the data is valid, false otherwise.
     */
    private boolean validateTransactionData() {
        receiverField.setInvalid(false);
        amountField.setInvalid(false);

        if (receiverField.isEmpty()) {
            receiverField.setInvalid(true);
            receiverField.setErrorMessage(LanguageManager.getLocalizedText("Please Enter Receiver"));
            return false;
        } else if (AccountOperations.getAccountByEmail(receiverField.getValue()) == null) {
            receiverField.setInvalid(true);
            receiverField.setErrorMessage(LanguageManager.getLocalizedText("Receiver does not exist"));
            return false;
        } else if (amountField.isEmpty()) {
            amountField.setInvalid(true);
            amountField.setErrorMessage(LanguageManager.getLocalizedText("Enter Amount"));
            return false;
        } else if (amountField.getValue() == 0) {
            amountField.setInvalid(true);
            amountField.setErrorMessage(LanguageManager.getLocalizedText("Amount cannot be 0"));
            return false;
        } else if ((currentAccount.getSaldo() + currentAccount.getDebtLimit()) < amountField.getValue()) {
            amountField.setInvalid(true);
            amountField.setErrorMessage(LanguageManager.getLocalizedText("Your account does not have enough coverage"));
            return false;
        }

        return true;
    }
}


