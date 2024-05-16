package de.tu.darmstadt.frontend.store;

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
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;

public class RegistrationDialog extends Dialog {

    private TextField firstNameField = new TextField("First Name");
    private TextField lastNameField = new TextField("Last Name");
    private EmailField emailField = new EmailField("Email");;
    private PasswordField passwordField = new PasswordField("Password");
    private DatePicker birthDateField = new DatePicker("Birth Date");
    private TextField phoneNumberField = new TextField("Phone Number");

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

    private void createAccount() {

        // Implement your logic to create the account here
        Account account = null;
        try {
            account = new Account(emailField.getValue(), passwordField.getValue(), firstNameField.getValue(), lastNameField.getValue(),
                    birthDateField.getValue(), phoneNumberField.getValue());
                    AccountOperations.createAccount(account);
        } catch (AccountPolicyException e) {
            Notification.show("Problem occured " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            return;
        }


        Notification.show("Account created successfully!", 3000, Notification.Position.MIDDLE);
        close();
    }


}
