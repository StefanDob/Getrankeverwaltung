package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.backend.backendService.AccountOperations;

public class LoginDialog extends Dialog {

    private TextField usernameField;
    private PasswordField passwordField;

    public LoginDialog() {
        // Create header with title
        H2 title = new H2("Login");

        // Create form layout for fields
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        // Username field
        usernameField = new TextField("Username");
        formLayout.add(usernameField);

        // Password field
        passwordField = new PasswordField("Password");
        formLayout.add(passwordField);

        Button loginButton = new Button("Login");
        loginButton.setWidthFull();
        loginButton.addClickListener(e -> {
            // Handle login logic here

            if (AccountOperations.getAccountByUserName(usernameField.getValue(), passwordField.getValue().toCharArray())!= null) {
                Notification.show("Login successful", 3000, Notification.Position.MIDDLE);
                close(); // Close the dialog after successful login
            } else {
                Notification.show("Invalid username or password", 3000, Notification.Position.MIDDLE);
            }


        });

        Button registerButton = new Button("Create Account");
        registerButton.setWidthFull();
        registerButton.addClickListener(e -> {

            close(); // Close the login dialog before navigating
            //open registration dialog
            RegistrationDialog registrationDialog = new RegistrationDialog();
            registrationDialog.open();

        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setWidthFull();
        cancelButton.addClickListener(e -> {
            close(); // Close the dialog without performing any action
        });

        formLayout.add(usernameField, passwordField, loginButton, registerButton, cancelButton);
        add(formLayout);
    }

    private void loginUser() {

    }


}