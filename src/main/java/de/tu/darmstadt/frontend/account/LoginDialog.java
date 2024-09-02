package de.tu.darmstadt.frontend.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.backendService.CookieOperations;
import de.tu.darmstadt.backend.exceptions.accountOperation.AccountOperationException;
import de.tu.darmstadt.backend.exceptions.accountOperation.IncorrectEmailException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.frontend.MainLayout;

public class LoginDialog extends Dialog {

    private TextField emailField;
    private PasswordField passwordField;
    private Checkbox rememberMeCheckbox;

    public LoginDialog() {
        // Header and close button
        Header header = new Header(new H2("Login"));
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
        closeButton.addClickListener(e -> close());
        headerLayout.add(header, closeButton);

        // Form layout for fields
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        // Email field
        emailField = new TextField("Email");
        formLayout.add(emailField);

        // Password field
        passwordField = new PasswordField("Password");
        formLayout.add(passwordField);

        // Remember Me checkbox
        rememberMeCheckbox = new Checkbox("Remember Me");
        formLayout.add(rememberMeCheckbox);

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setWidthFull();
        loginButton.addClickListener(e -> handleLogin());

        // Register button
        Button registerButton = new Button("Create Account");
        registerButton.setWidthFull();
        registerButton.addClickListener(e -> openRegistrationDialog());

        // Add components to the form layout
        formLayout.add(emailField, passwordField, rememberMeCheckbox, loginButton, registerButton);

        // Add header and form layout to the dialog
        add(headerLayout, formLayout);
    }

    private void handleLogin() {
        // Get entered email and password
        final String enteredEmail = emailField.getValue().trim();
        final String enteredPassword = passwordField.getValue();

        try {
            Account currentAccount = AccountOperations.getAccountByEmail(enteredEmail, enteredPassword);

            if (currentAccount != null) {
                SessionManagement.setAccount(currentAccount);

                // Save the account in a cookie if the "Remember Me" checkbox is checked
                if (rememberMeCheckbox.getValue()) {
                    CookieOperations.saveAccount(currentAccount);
                }

                UI.getCurrent().getPage().reload();
                close(); // Close the dialog after successful login
            }
        } catch (AccountOperationException ex) {
            emailField.setInvalid(true);
            passwordField.setInvalid(true);
            Notification.show(ex.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    private void openRegistrationDialog() {
        close(); // Close the login dialog before navigating
        RegistrationDialog registrationDialog = new RegistrationDialog();
        registrationDialog.open();
    }
}