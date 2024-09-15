package de.tu.darmstadt.frontend.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.backend.backendOperations.AccountOperations;
import de.tu.darmstadt.backend.backendOperations.CookieOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.*;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.Utils.AccountUtils;

import java.util.ArrayList;
import java.util.Date;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


/**
 * RegistrationDialog is responsible for handling user account creation.
 * It provides a form to gather user details such as name, email, password, and birth date.
 */
public class RegistrationDialog extends Dialog {

    private final TextField firstNameField = new TextField(LanguageManager.getLocalizedText("First Name"));
    private final TextField lastNameField = new TextField(LanguageManager.getLocalizedText("Last Name"));
    private final EmailField emailField = new EmailField(LanguageManager.getLocalizedText("Email"));
    private final PasswordField passwordField = new PasswordField(LanguageManager.getLocalizedText("Password"));
    private final DatePicker birthDateField = new DatePicker(LanguageManager.getLocalizedText("Birth Date"));
    private final TextField phoneNumberField = new TextField(LanguageManager.getLocalizedText("Phone Number (optional)"));
    private final Checkbox rememberMeCheckbox = new Checkbox(LanguageManager.getLocalizedText("Remember Me"));

    /**
     * Constructor for RegistrationDialog.
     * Sets up the form layout and buttons for account creation.
     */
    public RegistrationDialog() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        FormLayout formLayout = createFormLayout();
        HorizontalLayout buttonLayout = createButtonLayout();

        add(new H3(LanguageManager.getLocalizedText("Create Account")), formLayout, buttonLayout);
    }

    /**
     * Creates the form layout with user input fields.
     *
     * @return the form layout with all input fields for registration.
     */
    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2),
                new FormLayout.ResponsiveStep("32em", 3)
        );

        formLayout.add(firstNameField, lastNameField);
        formLayout.add(emailField, 2);
        formLayout.add(passwordField, 2);
        formLayout.add(birthDateField, phoneNumberField);
        formLayout.add(rememberMeCheckbox, 2);

        return formLayout;
    }

    /**
     * Creates the layout for the Create Account and Cancel buttons.
     *
     * @return a HorizontalLayout with the buttons.
     */
    private HorizontalLayout createButtonLayout() {
        Button createAccountButton = new Button(LanguageManager.getLocalizedText("Create Account"), event -> handleAccountCreation());
        Button cancelButton = new Button(LanguageManager.getLocalizedText("Cancel"), event -> close());
        cancelButton.getStyle().set("margin-left", "auto");

        HorizontalLayout buttonLayout = new HorizontalLayout(createAccountButton, cancelButton);
        buttonLayout.setWidthFull();
        return buttonLayout;
    }

    /**
     * Handles the account creation process, validates user input, and saves the account.
     * If the input is valid, the account is created and session or cookie is updated.
     */
    private void handleAccountCreation() {
        Account account = createAccount();
        if (account != null) {
            // Save the account in a cookie if "Remember Me" is checked
            if (rememberMeCheckbox.getValue()) {
                CookieOperations.saveAccount(account);
            }
            SessionManagement.setAccount(account);
            UI.getCurrent().getPage().reload();
        }
    }

    /**
     * Validates the input fields and creates a new account.
     *
     * @return a valid Account object if the input is valid, null otherwise.
     */
    private Account createAccount() {
        resetFieldValidity();

        String email = emailField.getValue();
        String password = passwordField.getValue();
        String firstName = firstNameField.getValue();
        String lastName = lastNameField.getValue();
        Date birthDate = AccountUtils.convertToDate(birthDateField.getValue());
        String phoneNumber = phoneNumberField.getValue();

        try {
            Account account = new Account(email, password, firstName, lastName, birthDate, phoneNumber);
            return AccountOperations.createAccount(account);
        } catch (AccountPolicyException ex) {
            validateInputFields(email, password, firstName, lastName, birthDate, phoneNumber);
            return null;
        }
    }

    /**
     * Resets the validity of all form fields before validation.
     */
    private void resetFieldValidity() {
        firstNameField.setInvalid(false);
        lastNameField.setInvalid(false);
        emailField.setInvalid(false);
        passwordField.setInvalid(false);
        birthDateField.setInvalid(false);
        phoneNumberField.setInvalid(false);
    }

    /**
     * Validates the input fields and marks fields as invalid with appropriate error messages if necessary.
     *
     * @param email       the email entered by the user.
     * @param password    the password entered by the user.
     * @param firstName   the first name entered by the user.
     * @param lastName    the last name entered by the user.
     * @param birthDate   the birth date entered by the user.
     * @param phoneNumber the phone number entered by the user.
     */
    private void validateInputFields(String email, String password, String firstName, String lastName, Date birthDate, String phoneNumber) {
        ArrayList<AccountPolicyException> exceptions = accountDataChecker(email, password, firstName, lastName, birthDate, phoneNumber);

        for (AccountPolicyException e : exceptions) {
            if (e instanceof InvalidEmailFormatException) {
                emailField.setInvalid(true);
                emailField.setErrorMessage(e.getMessage());
            } else if (e instanceof InvalidPasswordFormatException) {
                passwordField.setInvalid(true);
                passwordField.setErrorMessage(e.getMessage());
            } else if (e instanceof BadFirstNameException) {
                firstNameField.setInvalid(true);
                firstNameField.setErrorMessage(e.getMessage());
            } else if (e instanceof BadLastNameException) {
                lastNameField.setInvalid(true);
                lastNameField.setErrorMessage(e.getMessage());
            } else if (e instanceof IllegalBirthdateException) {
                birthDateField.setInvalid(true);
                birthDateField.setErrorMessage(e.getMessage());
            } else if (e instanceof InvalidPhoneNumberFormatException) {
                phoneNumberField.setInvalid(true);
                phoneNumberField.setErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * Checks the validity of account data fields such as email, password, first name, last name, birth date, and phone number.
     *
     * @param email       the email to validate.
     * @param password    the password to validate.
     * @param firstName   the first name to validate.
     * @param lastName    the last name to validate.
     * @param birthDate   the birth date to validate.
     * @param phoneNumber the phone number to validate.
     * @return a list of AccountPolicyException if any validation fails.
     */
    private ArrayList<AccountPolicyException> accountDataChecker(String email, String password, String firstName, String lastName, Date birthDate, String phoneNumber) {
        ArrayList<AccountPolicyException> exceptions = new ArrayList<>();

        try {
            AccountUtils.checkIfEmailIsInValidFormat(email);
            AccountUtils.isEmailAlreadyInUse(email);
        } catch (AccountPolicyException e) {
            exceptions.add(e);
        }

        try {
            AccountUtils.checkIfPasswordIsValid(password);
        } catch (InvalidPasswordFormatException e) {
            exceptions.add(e);
        }

        try {
            AccountUtils.checkIfFirstNameIsInValidFormat(firstName);
        } catch (BadFirstNameException e) {
            exceptions.add(e);
        }

        try {
            AccountUtils.checkIfLastNameIsInValidFormat(lastName);
        } catch (BadLastNameException e) {
            exceptions.add(e);
        }

        try {
            AccountUtils.checkIfBirthdateIsLegal(birthDate);
        } catch (IllegalBirthdateException e) {
            exceptions.add(e);
        }

        try {
            AccountUtils.check_if_phone_number_is_in_valid_format(phoneNumber);
        } catch (InvalidPhoneNumberFormatException e) {
            exceptions.add(e);
        }

        return exceptions;
    }
}

