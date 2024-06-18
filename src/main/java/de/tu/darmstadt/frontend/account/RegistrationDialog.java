package de.tu.darmstadt.frontend.account;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.*;
import de.tu.darmstadt.dataModel.Account;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class RegistrationDialog extends Dialog {

    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final EmailField emailField = new EmailField("Email");;
    private final PasswordField passwordField = new PasswordField("Password");
    private final DatePicker birthDateField = new DatePicker("Birth Date");
    private final TextField phoneNumberField = new TextField("Phone Number (optional)");

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

        Button createAccountButton = new Button("Create Account", event -> createAccount());
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

    /*
    different problems until now:
        name throws exception even though it is written right
        I do not think that we can hold the concept with the exceptions at this stage, they can stay, but I need public access to the methods that validate those inputs
            What happens if multiple fields are wrong at the same time? Program cannot throw multiple exceptions at once
     */
    private void createAccount() {
        createAccountV1();
    }


    private @NotNull ArrayList<? extends AccountPolicyException> account_data_checker(String email, String password,
                                                                            String first_name, String last_name,
                                                                            LocalDate birth_date, String phone_number)
    {
        final ArrayList<AccountPolicyException> exceptions = new ArrayList<>(6);


        try {
            Account.check_if_email_is_in_valid_format(email); // Checks if email is in a valid format
            Account.is_email_already_in_use(email); // Checks if email is already in use
        } catch (AccountPolicyException e) {
            exceptions.add(e);
        }

        try {
            Account.check_if_password_is_valid(password);
        } catch (InvalidPasswordFormatException e) {
            exceptions.add(e);
        }

        try {
            Account.check_if_first_name_is_in_valid_format(first_name);
        } catch (BadFirstNameException e) {
            exceptions.add(e);
        }

        try {
            Account.check_if_last_name_is_in_valid_format(last_name);
        } catch (BadLastNameException e) {
            exceptions.add(e);
        }

        try {
            Account.check_if_birthdate_is_legal(birth_date);
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


    /**
     * @deprecated This method should not be used since it is faulty. Use {@link #createAccountV1()} instead.
     */
    private void createAccountV2() {

        firstNameField.setInvalid(false);
        lastNameField.setInvalid(false);
        emailField.setInvalid(false);
        passwordField.setInvalid(false);
        birthDateField.setInvalid(false);
        phoneNumberField.setInvalid(false);

        // These are the data for creating a new account
        String email = emailField.getValue();
        String password = passwordField.getValue();
        String firstName = firstNameField.getValue();
        String lastName = lastNameField.getValue();
        LocalDate birthDate = birthDateField.getValue();
        String phoneNumber = phoneNumberField.getValue();

        ArrayList<? extends AccountPolicyException> accountPolicyExceptions =
                account_data_checker(email, password, firstName, lastName, birthDate, phoneNumber);

        Account account;

        if(accountPolicyExceptions.isEmpty()) {

            try {
                account = new Account(email, password, firstName, lastName, birthDate, phoneNumber);
                AccountOperations.createAccount(account);
            } catch (AccountPolicyException e) {
                return; // terminate
            }

            Notification.show("Account created successfully!", 3000, Notification.Position.MIDDLE);
            close();
        }

        for(AccountPolicyException ex : accountPolicyExceptions) {
            if(ex instanceof InvalidEmailFormatException) {
                emailField.setInvalid(true);
                emailField.setErrorMessage(ex.getMessage());
            } else if(ex instanceof EmailAlreadyInUseException) {
                emailField.setInvalid(true);
                emailField.setErrorMessage(ex.getMessage());
            } else if (ex instanceof InvalidPasswordFormatException) {
                passwordField.setInvalid(true);
                passwordField.setErrorMessage(ex.getMessage());
            } else if (ex instanceof BadFirstNameException) {
                firstNameField.setInvalid(true);
                firstNameField.setErrorMessage(ex.getMessage());
            } else if (ex instanceof BadLastNameException) {
                lastNameField.setInvalid(true);
                lastNameField.setErrorMessage(ex.getMessage());
            } else if (ex instanceof IllegalBirthdateException) {
                birthDateField.setInvalid(true);
                birthDateField.setErrorMessage(ex.getMessage());
            } else if (ex instanceof InvalidPhoneNumberFormatException) {
                phoneNumberField.setInvalid(true);
                phoneNumberField.setErrorMessage(ex.getMessage());
            }
        }

    }


    private void createAccountV1() {

        firstNameField.setInvalid(false);
        lastNameField.setInvalid(false);
        emailField.setInvalid(false);
        passwordField.setInvalid(false);
        birthDateField.setInvalid(false);
        phoneNumberField.setInvalid(false);
        // Implement your logic to create the account here

        // The entered values are stored as local variables for better readability.
        final String email = emailField.getValue();
        final String password = passwordField.getValue();
        final String firstName = firstNameField.getValue();
        final String lastName = lastNameField.getValue();
        final LocalDate birthDate = birthDateField.getValue();
        final String phoneNumber = phoneNumberField.getValue();

        Account account;
        try {
            account = new Account(email, password, firstName,
                    lastName, birthDate, phoneNumber);

            AccountOperations.createAccount(account);

        } catch (AccountPolicyException ex) {

            try {
                Account.check_if_email_is_in_valid_format(email); // Checks the email format
                Account.is_email_already_in_use(email); // Checks if an email is already in use
            } catch (AccountPolicyException e) {
                emailField.setInvalid(true);
                emailField.setErrorMessage(e.getMessage());
            }

            try {
                Account.check_if_password_is_valid(password);
            } catch (InvalidPasswordFormatException e) {
                passwordField.setInvalid(true);
                passwordField.setErrorMessage(e.getMessage());
            }

            try {
                Account.check_if_first_name_is_in_valid_format(firstName);
            } catch (BadFirstNameException e) {
                firstNameField.setInvalid(true);
                firstNameField.setErrorMessage(e.getMessage());
            }

            try {
                Account.check_if_last_name_is_in_valid_format(lastName);
            } catch (BadLastNameException e) {
                lastNameField.setInvalid(true);
                lastNameField.setErrorMessage(e.getMessage());
            }

            try {
                Account.check_if_birthdate_is_legal(birthDate);
            } catch (IllegalBirthdateException e) {
                birthDateField.setInvalid(true);
                birthDateField.setErrorMessage(e.getMessage());
            }

            try {
                Account.check_if_phone_number_is_in_valid_format(phoneNumber);
            } catch (InvalidPhoneNumberFormatException e) {
                phoneNumberField.setInvalid(true);
                phoneNumberField.setErrorMessage(e.getMessage());
            }

            return; // Terminate the method immediately when any AccountPolicyException occurs.

        } // end of try-catch


        Notification.show("Account created successfully!", 3000, Notification.Position.MIDDLE);
        close();
    }


}
