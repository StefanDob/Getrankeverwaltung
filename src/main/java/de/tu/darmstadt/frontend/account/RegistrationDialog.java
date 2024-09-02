package de.tu.darmstadt.frontend.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.backendService.CookieOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.*;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Utils.AccountUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class RegistrationDialog extends Dialog {

    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final EmailField emailField = new EmailField("Email");
    private final PasswordField passwordField = new PasswordField("Password");
    private final DatePicker birthDateField = new DatePicker("Birth Date");
    private final TextField phoneNumberField = new TextField("Phone Number (optional)");
    private final Checkbox rememberMeCheckbox = new Checkbox("Remember Me");

    public RegistrationDialog() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2),
                new FormLayout.ResponsiveStep("32em", 3));

        formLayout.add(firstNameField, lastNameField);
        formLayout.add(emailField, 2);
        formLayout.add(passwordField, 2);
        formLayout.add(birthDateField, phoneNumberField);
        formLayout.add(rememberMeCheckbox, 2);  // Add the Remember Me checkbox

        Button createAccountButton = new Button("Create Account", event -> handleAccountCreation());
        Button cancelButton = new Button("Cancel", event -> close());
        cancelButton.getStyle().set("margin-left", "auto");

        FormLayout buttonLayout = new FormLayout(createAccountButton, cancelButton);
        buttonLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        buttonLayout.setWidthFull();
        buttonLayout.setColspan(createAccountButton, 1);
        buttonLayout.setColspan(cancelButton, 1);
        buttonLayout.setColspan(formLayout, 2);
        buttonLayout.setColspan(new H3("Create Account"), 2);

        add(new H3("Create Account"), formLayout, buttonLayout);
    }

    private void handleAccountCreation() {
        Account account = createAccount();
        if (account != null) {
            // Save the account in a cookie if the "Remember Me" checkbox is checked
            if (rememberMeCheckbox.getValue()) {
                CookieOperations.saveAccount(account);
            }
            SessionManagement.setAccount(account);
            UI.getCurrent().getPage().reload();
        }
    }

    private @NotNull ArrayList<? extends AccountPolicyException> account_data_checker(String email, String password,
                                                                                      String firstName, String last_name,
                                                                                      Date birthDate, String phone_number) {
        final ArrayList<AccountPolicyException> exceptions = new ArrayList<>(6);

        try {
            Account.checkIfEmailIsInValidFormat(email); // Checks if email is in a valid format
            Account.isEmailAlreadyInUse(email); // Checks if email is already in use
        } catch (AccountPolicyException e) {
            exceptions.add(e);
        }

        try {
            Account.checkIfPasswordIsValid(password);
        } catch (InvalidPasswordFormatException e) {
            exceptions.add(e);
        }

        try {
            Account.checkIfFirstNameIsInValidFormat(firstName);
        } catch (BadFirstNameException e) {
            exceptions.add(e);
        }

        try {
            Account.checkIfLastNameIsInValidFormat(last_name);
        } catch (BadLastNameException e) {
            exceptions.add(e);
        }

        try {
            Account.checkIfBirthdateIsLegal(birthDate);
        } catch (IllegalBirthdateException e) {
            exceptions.add(e);
        }

        try {
            Account.check_if_phone_number_is_in_valid_format(phone_number);
        } catch (InvalidPhoneNumberFormatException e) {
            exceptions.add(e);
        }

        return exceptions;
    }

    private Account createAccount() {
        return createAccountV1();
    }

    private Account createAccountV1() {

        // Reset field validity before validation
        firstNameField.setInvalid(false);
        lastNameField.setInvalid(false);
        emailField.setInvalid(false);
        passwordField.setInvalid(false);
        birthDateField.setInvalid(false);
        phoneNumberField.setInvalid(false);

        // Store entered values
        final String email = emailField.getValue();
        final String password = passwordField.getValue();
        final String firstName = firstNameField.getValue();
        final String lastName = lastNameField.getValue();
        final Date birthDate = AccountUtils.convertToDate(birthDateField.getValue());
        final String phoneNumber = phoneNumberField.getValue();

        Account account;
        try {
            account = new Account(email, password, firstName, lastName, birthDate, phoneNumber);
            account = AccountOperations.createAccount(account);

        } catch (AccountPolicyException ex) {
            validateInputFields(email, password, firstName, lastName, birthDate, phoneNumber);
            return null;
        }

        Notification.show("Account created successfully!", 3000, Notification.Position.MIDDLE);
        close();
        return account;
    }

    private void validateInputFields(String email, String password, String firstName, String lastName, Date birthDate, String phoneNumber) {
        // Validate email
        try {
            Account.checkIfEmailIsInValidFormat(email);
            Account.isEmailAlreadyInUse(email);
        } catch (AccountPolicyException e) {
            emailField.setInvalid(true);
            emailField.setErrorMessage(e.getMessage());
        }

        // Validate password
        try {
            Account.checkIfPasswordIsValid(password);
        } catch (InvalidPasswordFormatException e) {
            passwordField.setInvalid(true);
            passwordField.setErrorMessage(e.getMessage());
        }

        // Validate first name
        try {
            Account.checkIfFirstNameIsInValidFormat(firstName);
        } catch (BadFirstNameException e) {
            firstNameField.setInvalid(true);
            firstNameField.setErrorMessage(e.getMessage());
        }

        // Validate last name
        try {
            Account.checkIfLastNameIsInValidFormat(lastName);
        } catch (BadLastNameException e) {
            lastNameField.setInvalid(true);
            lastNameField.setErrorMessage(e.getMessage());
        }

        // Validate birth date
        try {
            Account.checkIfBirthdateIsLegal(birthDate);
        } catch (IllegalBirthdateException e) {
            birthDateField.setInvalid(true);
            birthDateField.setErrorMessage(e.getMessage());
        }

        // Validate phone number
        try {
            Account.check_if_phone_number_is_in_valid_format(phoneNumber);
        } catch (InvalidPhoneNumberFormatException e) {
            phoneNumberField.setInvalid(true);
            phoneNumberField.setErrorMessage(e.getMessage());
        }
    }
}
