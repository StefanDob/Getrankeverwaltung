package de.tu.darmstadt.frontend.account;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.frontend.MainLayout;

import java.time.LocalDate;

@PageTitle("Account")
@Route(value = "account", layout = MainLayout.class)
public class AccountView extends VerticalLayout {

    Account currentAccount;

    private TextField firstNameField = new TextField("First Name");
    private TextField lastNameField = new TextField("Last Name");
    private EmailField emailField = new EmailField("Email");;
    private PasswordField passwordField = new PasswordField("Password");
    private DatePicker birthDateField = new DatePicker("Birth Date");
    private TextField phoneNumberField = new TextField("Phone Number");

    public AccountView() {
        currentAccount = SessionManagement.getAccount();
        
        if(currentAccount == null){
            showNotLoggedInJetMessage();
        }else{
            showAccount();
        }

        
    }

    private void showAccount() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1));

        setReadOnlyForFields(true);
        initializeFields();

        formLayout.add(firstNameField, lastNameField);
        formLayout.add(emailField);
        formLayout.add(passwordField);
        formLayout.add(birthDateField, phoneNumberField);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(new H2("General Information"), formLayout);

        // Add the formWrapper to the layout
        add(verticalLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void initializeFields() {
        firstNameField.setValue(currentAccount.getFirst_name());
        lastNameField.setValue(currentAccount.getLast_name());
        emailField.setValue(currentAccount.getEmail());
        passwordField.setValue(currentAccount.getPassword());
        birthDateField.setValue(LocalDate.of(0,2,1));
        phoneNumberField.setValue("not jet implemented");
    }

    private void setReadOnlyForFields(boolean readOnly) {
        firstNameField.setReadOnly(readOnly);
        lastNameField.setReadOnly(readOnly);
        emailField.setReadOnly(readOnly);
        passwordField.setReadOnly(readOnly);
        birthDateField.setReadOnly(readOnly);
        phoneNumberField.setReadOnly(readOnly);



    }

    private void showNotLoggedInJetMessage() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("Please login");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);



        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }



}
