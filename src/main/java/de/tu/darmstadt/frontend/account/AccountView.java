package de.tu.darmstadt.frontend.account;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
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

    private TextField accountBalanceField = new TextField("Account Balance");

    public AccountView() {
        currentAccount = SessionManagement.getAccount();
        
        if(currentAccount == null){
            showNotLoggedInJetMessage();
        } else {
            showAccount();
        }

        
    }

    private void showAccount() {
        showGeneralAccountInformation();
        showLastTransactions();

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        getStyle().set("text-align", "center");
    }

    private void showGeneralAccountInformation() {
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
        Details accountDetails = new Details(new H2("General Information"));
        accountDetails.setWidth("100%");
        accountDetails.getStyle().set("border", "1px solid #ccc");
        accountDetails.getStyle().set("border-radius", "6px");
        accountDetails.setOpened(true);
        accountDetails.add(formLayout);
        add(accountDetails);
    }

    private void showLastTransactions() {
        Details lastTransactions = new Details(new H2("Last Transactions"));
        lastTransactions.setOpened(true);
        lastTransactions.getStyle().set("border", "1px solid #ccc");
        lastTransactions.getStyle().set("border-radius", "6px");
        lastTransactions.setWidth("100%");

        Grid<Transaction> grid = new Grid<>(Transaction.class);

        // Configure the grid
        grid.setItems(AccountOperations.getTransactionsByAccount(currentAccount));
        grid.setColumns("sender", "receiver", "amount", "transactionDate");
        grid.setWidth("100%");
        grid.setAllRowsVisible(true);


        // Add the grid to the layout
        lastTransactions.add(grid);
        add(lastTransactions);
    }

    private void initializeFields() {
        firstNameField.setValue(currentAccount.getFirst_name());
        lastNameField.setValue(currentAccount.getLast_name());
        emailField.setValue(currentAccount.getEmail());
        passwordField.setValue(currentAccount.getPassword());
        birthDateField.setValue(LocalDate.of(0,2,1));
        phoneNumberField.setValue(currentAccount.getPhone_number());
        accountBalanceField.setValue("" + currentAccount.getBalance());
    }

    private void setReadOnlyForFields(boolean readOnly) {
        firstNameField.setReadOnly(readOnly);
        lastNameField.setReadOnly(readOnly);
        emailField.setReadOnly(readOnly);
        passwordField.setReadOnly(readOnly);
        birthDateField.setReadOnly(readOnly);
        phoneNumberField.setReadOnly(readOnly);
        accountBalanceField.setReadOnly(readOnly);


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
