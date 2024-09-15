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
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.backend.backendOperations.AccountOperations;
import de.tu.darmstadt.backend.backendOperations.CookieOperations;
import de.tu.darmstadt.backend.exceptions.accountOperation.AccountOperationException;
import de.tu.darmstadt.dataModel.Account;

/**
 * LoginDialog handles user login by displaying a form with email, password, and a "Remember Me" option.
 * It performs account validation and manages session or cookie storage based on the user's preferences.
 */
public class LoginDialog extends Dialog {

    private final TextField emailField;
    private final PasswordField passwordField;
    private final Checkbox rememberMeCheckbox;

    /**
     * Constructor for LoginDialog.
     * Initializes the dialog with form fields for email, password, and a login button.
     */
    public LoginDialog() {
        // Initialize form fields
        emailField = new TextField(LanguageManager.getLocalizedText("Email"));
        passwordField = new PasswordField(LanguageManager.getLocalizedText("Password"));
        rememberMeCheckbox = new Checkbox(LanguageManager.getLocalizedText("Remember Me"));

        // Create and set up the layout
        HorizontalLayout headerLayout = createHeaderLayout();
        FormLayout formLayout = createFormLayout();

        // Add header and form layout to the dialog
        add(headerLayout, formLayout);
    }

    /**
     * Creates the header layout with a title and a close button.
     *
     * @return a HorizontalLayout containing the header components.
     */
    private HorizontalLayout createHeaderLayout() {
        Header header = new Header(new H2(LanguageManager.getLocalizedText("Login")));
        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
        closeButton.addClickListener(e -> close());

        HorizontalLayout headerLayout = new HorizontalLayout(header, closeButton);
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);

        return headerLayout;
    }

    /**
     * Creates the form layout with input fields and buttons for login and registration.
     *
     * @return a FormLayout containing the input fields and buttons.
     */
    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        Button loginButton = new Button(LanguageManager.getLocalizedText("Login"), e -> handleLogin());
        loginButton.setWidthFull();

        Button registerButton = new Button(LanguageManager.getLocalizedText("Create Account"), e -> openRegistrationDialog());
        registerButton.setWidthFull();

        formLayout.add(emailField, passwordField, rememberMeCheckbox, loginButton, registerButton);
        return formLayout;
    }

    /**
     * Handles the login process by validating the email and password fields.
     * If valid, it logs in the user and manages session and cookies.
     */
    private void handleLogin() {
        String enteredEmail = emailField.getValue().trim();
        String enteredPassword = passwordField.getValue();

        try {
            Account currentAccount = AccountOperations.getAccountByEmail(enteredEmail, enteredPassword);

            if (currentAccount != null) {
                SessionManagement.setAccount(currentAccount);

                if (rememberMeCheckbox.getValue()) {
                    CookieOperations.saveAccount(currentAccount);
                }

                UI.getCurrent().getPage().reload();
                close();
            }
        } catch (AccountOperationException ex) {
            showLoginError();
        }
    }

    /**
     * Displays an error notification if login fails due to invalid credentials.
     */
    private void showLoginError() {
        emailField.setInvalid(true);
        passwordField.setInvalid(true);
        Notification.show(LanguageManager.getLocalizedText("Email/Password combination does not exist"), 3000, Notification.Position.MIDDLE);
    }

    /**
     * Opens the registration dialog and closes the current login dialog.
     */
    private void openRegistrationDialog() {
        close();
        new RegistrationDialog().open();
    }
}
