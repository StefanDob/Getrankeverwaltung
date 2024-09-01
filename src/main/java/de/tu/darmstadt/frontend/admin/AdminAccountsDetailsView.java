package de.tu.darmstadt.frontend.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.DebtLimitExceedingException;
import de.tu.darmstadt.backend.exceptions.accountPolicy.InvalidEmailFormatException;
import de.tu.darmstadt.backend.exceptions.accountPolicy.InvalidNameFormatException;
import de.tu.darmstadt.backend.exceptions.accountPolicy.InvalidPasswordFormatException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Utils.AccountUtils;

public class AdminAccountsDetailsView extends Dialog {

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private PasswordField passwordField;
    private DatePicker birthDatePicker;
    private TextField phoneNumberField;
    private TextField debtLimitField;
    private TextField saldoField;
    private ComboBox<AccountStatus> statusComboBox;  // Change to ComboBox<Account.Status>
    private Button saveButton;
    private Button cancelButton;

    private Account account;

    public AdminAccountsDetailsView(Account account) {
        this.account = account;
        setWidth("40vw");

        // Create and configure the UI components
        firstNameField = new TextField("First Name");
        lastNameField = new TextField("Last Name");
        emailField = new TextField("Email");
        passwordField = new PasswordField("Password");
        birthDatePicker = new DatePicker("Birth Date");
        phoneNumberField = new TextField("Phone Number");
        debtLimitField = new TextField("Debt Limit");
        saldoField = new TextField("Saldo");

        // Add status field
        statusComboBox = new ComboBox<>("Status");
        statusComboBox.setItems(AccountStatus.values());  // Set items to enum values
        statusComboBox.setWidthFull();

        saveButton = new Button("Save", event -> saveAccount());
        cancelButton = new Button("Cancel", event -> this.close());

        // Populate fields with account data
        populateFields(account);

        setupChangeListeners();

        // Arrange buttons in a HorizontalLayout
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // Arrange components in the layout
        VerticalLayout layout = new VerticalLayout(
                firstNameField,
                lastNameField,
                emailField,
                passwordField,
                birthDatePicker,
                phoneNumberField,
                debtLimitField,
                saldoField,
                statusComboBox,
                buttonLayout
        );

        add(layout);
    }

    private void populateFields(Account account) {
        firstNameField.setValue(account.getFirstName());
        firstNameField.setWidthFull();
        lastNameField.setValue(account.getLastName());
        lastNameField.setWidthFull();
        emailField.setValue(account.getEmail());
        emailField.setWidthFull();
        passwordField.setValue(account.getPassword());
        passwordField.setWidthFull();
        birthDatePicker.setValue(AccountUtils.convertToLocalDate(account.getBirthDate()));
        birthDatePicker.setWidthFull();
        phoneNumberField.setValue(account.getPhoneNumber() != null ? account.getPhoneNumber() : "");
        phoneNumberField.setWidthFull();
        debtLimitField.setValue(String.valueOf(account.getDebtLimit()));
        debtLimitField.setWidthFull();
        saldoField.setValue(String.valueOf(account.getSaldo()));
        saldoField.setWidthFull();
        statusComboBox.setValue(account.getStatus());  // Set the account status using enum
    }

    private void setupChangeListeners() {
        firstNameField.addValueChangeListener(event -> account.setFirstName(event.getValue()));
        lastNameField.addValueChangeListener(event -> {
            try {
                account.setLastName(event.getValue());
            } catch (InvalidNameFormatException e) {
                // TODO make this better
                throw new RuntimeException(e);
            }
        });
        emailField.addValueChangeListener(event -> {
            try {
                account.setEmail(event.getValue());
            } catch (InvalidEmailFormatException e) {
                // TODO make this better
                throw new RuntimeException(e);
            }
        });
        passwordField.addValueChangeListener(event -> {
            try {
                account.setPassword(event.getValue());
            } catch (InvalidPasswordFormatException e) {
                // TODO make this better
                throw new RuntimeException(e);
            }
        });
        birthDatePicker.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                account.setBirthDate(java.sql.Date.valueOf(event.getValue()));
            }
        });
        phoneNumberField.addValueChangeListener(event -> account.setPhoneNumber(event.getValue()));
        debtLimitField.addValueChangeListener(event -> {
            try {
                account.setDebtLimit(Double.parseDouble(event.getValue()));
            } catch (NumberFormatException e) {
                Notification.show("Invalid debt limit format");
            }
        });
        saldoField.addValueChangeListener(event -> {
            try {
                account.setSaldo(Double.parseDouble(event.getValue()));
            } catch (NumberFormatException e) {
                Notification.show("Invalid saldo format");
            } catch (DebtLimitExceedingException e) {
                // TODO make this better
                throw new RuntimeException(e);
            }
        });
        statusComboBox.addValueChangeListener(event -> account.setStatus(event.getValue()));  // Use enum value directly
    }

    private void saveAccount() {
        // Update account with data from fields
        AccountOperations.saveAccount(account);
        Notification.show("Account updated successfully");
        close();
        UI.getCurrent().getPage().reload();
    }
}