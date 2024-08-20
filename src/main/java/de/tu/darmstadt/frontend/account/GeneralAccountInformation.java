package de.tu.darmstadt.frontend.account;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.dataModel.Account;

import java.time.LocalDate;

public class GeneralAccountInformation extends Details {
    Account currentAccount;

    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final EmailField emailField = new EmailField("Email");;
    private final PasswordField passwordField = new PasswordField("Password");
    private final DatePicker birthDateField = new DatePicker("Birth Date");
    private final TextField phoneNumberField = new TextField("Phone Number");

    private final TextField accountBalanceField = new TextField("Account Balance");
    public GeneralAccountInformation(Account currentAccount){
        super(new H2("General Information"));
        this.currentAccount = currentAccount;

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1));

        setReadOnlyForFields(true);
        initializeFields();

        formLayout.add(firstNameField, lastNameField);
        formLayout.add(emailField, accountBalanceField);
        formLayout.add(passwordField);
        formLayout.add(birthDateField, phoneNumberField);



        // Add the formWrapper to the layout
        setWidth("100%");
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");
        setOpened(true);
        add(formLayout);

    }

    private void initializeFields() {
        firstNameField.setValue(currentAccount.getFirstName());
        lastNameField.setValue(currentAccount.getLastName());
        emailField.setValue(currentAccount.getEmail());
        passwordField.setValue(currentAccount.getPassword());
        birthDateField.setValue(LocalDate.of(0,2,1));
        phoneNumberField.setValue(currentAccount.getPhoneNumber());
        accountBalanceField.setValue("" + currentAccount.getSaldo());
    }

    private void setReadOnlyForFields(boolean readOnly) {
        firstNameField.setReadOnly(readOnly);
        firstNameField.setThemeName("bordered");
        lastNameField.setReadOnly(readOnly);

        emailField.setReadOnly(readOnly);
        passwordField.setReadOnly(readOnly);
        birthDateField.setReadOnly(readOnly);
        phoneNumberField.setReadOnly(readOnly);
        accountBalanceField.setReadOnly(readOnly);
    }

    

}
