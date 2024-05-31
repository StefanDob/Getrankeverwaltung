package de.tu.darmstadt.frontend.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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
import de.tu.darmstadt.backend.exceptions.accountOperation.AccountOperationException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.frontend.MainLayout;

public class LoginDialog extends Dialog {

    private TextField usernameField;
    private PasswordField passwordField;

    public LoginDialog() {
        Header header = new Header(new H2("Login"));

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);


        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
        closeButton.addClickListener(e -> {
            close(); // Close the dialog without performing any action
        });

        // Add title to the left of the header layout
        headerLayout.add(header, closeButton);


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

            Account currentAccount = null;
            try {
                currentAccount = AccountOperations.getAccountByUserName(usernameField.getValue(), passwordField.getValue());
            } catch (AccountOperationException ex) {
                throw new RuntimeException(ex);
            }
            if (currentAccount != null) {
                SessionManagement.setAccount(currentAccount);
                UI.getCurrent().getPage().reload();
                //UI.getCurrent().navigate(AccountView.class);
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




        formLayout.add(usernameField, passwordField, loginButton, registerButton);
        add(headerLayout, formLayout);
    }

    private void loginUser() {

    }


}