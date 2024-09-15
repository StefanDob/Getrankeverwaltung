package de.tu.darmstadt.frontend.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.backendOperations.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.*;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.Utils.AccountUtils;

import java.sql.Date;

/**
 * AdminAccountsDetailsView displays and allows editing of an account's details.
 */
public class AdminAccountsDetailsView extends Dialog {

    private final TextField firstNameField;
    private final TextField lastNameField;
    private final TextField emailField;
    private final PasswordField passwordField;
    private final DatePicker birthDatePicker;
    private final TextField phoneNumberField;
    private final TextField debtLimitField;
    private final TextField saldoField;
    private final ComboBox<AccountStatus> statusComboBox;
    private final Button saveButton;
    private final Button cancelButton;

    private final Account account;

    /**
     * Constructor for AdminAccountsDetailsView.
     * Initializes the dialog with fields for account details and populates them with the given account's data.
     *
     * @param account The account whose details are being displayed and edited.
     */
    public AdminAccountsDetailsView(Account account) {
        this.account = account;
        setWidth("40vw");

        // Initialize UI components
        firstNameField = new TextField(LanguageManager.getLocalizedText("First Name"));
        lastNameField = new TextField(LanguageManager.getLocalizedText("Last Name"));
        emailField = new TextField(LanguageManager.getLocalizedText("Email"));
        passwordField = new PasswordField(LanguageManager.getLocalizedText("Password"));
        birthDatePicker = new DatePicker(LanguageManager.getLocalizedText("Birth Date"));
        phoneNumberField = new TextField(LanguageManager.getLocalizedText("Phone Number"));
        debtLimitField = new TextField(LanguageManager.getLocalizedText("Debt Limit"));
        saldoField = new TextField(LanguageManager.getLocalizedText("Balance"));

        // Initialize ComboBox for account status
        statusComboBox = new ComboBox<>(LanguageManager.getLocalizedText("State"));
        statusComboBox.setItems(AccountStatus.values());
        statusComboBox.setWidthFull();

        // Initialize buttons
        saveButton = new Button(LanguageManager.getLocalizedText("Save"), event -> saveAccount());
        cancelButton = new Button(LanguageManager.getLocalizedText("Cancel"), event -> this.close());

        // Populate fields with account data
        populateFields();

        // Set up change listeners to update the account object when fields change
        setupChangeListeners();

        // Arrange components in layout
        add(createFormLayout(), createButtonLayout());
    }

    /**
     * Populates the fields with the account's current data.
     */
    private void populateFields() {
        firstNameField.setValue(account.getFirstName());
        lastNameField.setValue(account.getLastName());
        emailField.setValue(account.getEmail());
        passwordField.setValue(account.getPassword());
        birthDatePicker.setValue(AccountUtils.convertToLocalDate(account.getBirthDate()));
        phoneNumberField.setValue(account.getPhoneNumber() != null ? account.getPhoneNumber() : "");
        debtLimitField.setValue(String.valueOf(account.getDebtLimit()));
        saldoField.setValue(String.valueOf(account.getSaldo()));
        statusComboBox.setValue(account.getStatus());
    }

    /**
     * Sets up change listeners to update the account object when fields change.
     */
    private void setupChangeListeners() {
        firstNameField.addValueChangeListener(event -> account.setFirstName(event.getValue()));

        lastNameField.addValueChangeListener(event -> {
            try {
                account.setLastName(event.getValue());
            } catch (InvalidNameFormatException e) {
                Notification.show(LanguageManager.getLocalizedText("Invalid last name format"), 3000, Notification.Position.MIDDLE);
            }
        });

        emailField.addValueChangeListener(event -> {
            try {
                account.setEmail(event.getValue());
            } catch (InvalidEmailFormatException e) {
                Notification.show(LanguageManager.getLocalizedText("Invalid email format"), 3000, Notification.Position.MIDDLE);
            }
        });

        passwordField.addValueChangeListener(event -> {
            try {
                account.setPassword(event.getValue());
            } catch (InvalidPasswordFormatException e) {
                Notification.show(LanguageManager.getLocalizedText("Invalid password format"), 3000, Notification.Position.MIDDLE);
            }
        });

        birthDatePicker.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                account.setBirthDate(Date.valueOf(event.getValue()));
            }
        });

        phoneNumberField.addValueChangeListener(event -> account.setPhoneNumber(event.getValue()));

        debtLimitField.addValueChangeListener(event -> {
            try {
                account.setDebtLimit(Double.parseDouble(event.getValue()));
            } catch (NumberFormatException e) {
                Notification.show(LanguageManager.getLocalizedText("Invalid debt limit format"), 3000, Notification.Position.MIDDLE);
            }
        });

        saldoField.addValueChangeListener(event -> {
            try {
                account.setSaldo(Double.parseDouble(event.getValue()));
            } catch (NumberFormatException e) {
                Notification.show(LanguageManager.getLocalizedText("Invalid balance format"), 3000, Notification.Position.MIDDLE);
            } catch (DebtLimitExceedingException e) {
                Notification.show(LanguageManager.getLocalizedText("Debt limit exceeded"), 3000, Notification.Position.MIDDLE);
            }
        });

        statusComboBox.addValueChangeListener(event -> account.setStatus(event.getValue()));
    }

    /**
     * Saves the updated account data to the backend.
     */
    private void saveAccount() {
        AccountOperations.saveAccount(account);
        Notification.show(LanguageManager.getLocalizedText("Account updated successfully"), 3000, Notification.Position.MIDDLE);
        close();
        //Make sure that the current account gets updated in case the change has been made to the current account
        if(SessionManagement.getAccount() != null){
            try {
                SessionManagement.setAccount(AccountOperations.getAccountByID(SessionManagement.getAccount().getId()));
            } catch (AccountPolicyException e) {
                throw new RuntimeException(e);
            }
        }

        UI.getCurrent().getPage().reload();
    }

    /**
     * Creates the form layout for displaying account fields.
     *
     * @return the FormLayout with account fields.
     */
    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstNameField, lastNameField, emailField, passwordField, birthDatePicker, phoneNumberField, debtLimitField, saldoField, statusComboBox);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));  // Make form responsive
        return formLayout;
    }

    /**
     * Creates the button layout for the save and cancel buttons.
     *
     * @return the HorizontalLayout with buttons.
     */
    private HorizontalLayout createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return buttonLayout;
    }
}
