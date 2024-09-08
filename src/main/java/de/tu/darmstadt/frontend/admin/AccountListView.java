package de.tu.darmstadt.frontend.admin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.dataModel.Account;

import java.util.List;

/**
 * AccountListView is responsible for displaying a list of accounts in a grid format.
 * Each account's details can be viewed by clicking on a row, which opens the AdminAccountsDetailsView.
 */
public class AccountListView extends VerticalLayout {

    private Grid<Account> accountGrid;

    /**
     * Constructor for AccountListView.
     * Initializes the account list view and sets up the grid with account data.
     */
    public AccountListView() {
        setHeight("400px");
        getStyle().set("overflow", "auto");
        initializeAccountListView();
    }

    /**
     * Initializes the account list view by creating the grid and populating it with account data.
     */
    private void initializeAccountListView() {
        // Add title for the account list
        H3 title = new H3(LanguageManager.getLocalizedText("Accounts List"));
        add(title);

        // Initialize and configure the account grid
        accountGrid = createAccountGrid();
        add(accountGrid);

        // Load and set the account data into the grid
        populateAccountGrid();
    }

    /**
     * Creates and configures the account grid with columns for each account.
     *
     * @return the configured Grid<Account>.
     */
    private Grid<Account> createAccountGrid() {
        Grid<Account> grid = new Grid<>(Account.class, false);
        grid.addClassName("account-grid");

        grid.addColumn(Account::getFirstName).setHeader(LanguageManager.getLocalizedText("First Name"));
        grid.addColumn(Account::getLastName).setHeader(LanguageManager.getLocalizedText("Last Name"));
        grid.addColumn(Account::getSaldo).setHeader(LanguageManager.getLocalizedText("Balance"));
        grid.addColumn(Account::getPhoneNumber).setHeader(LanguageManager.getLocalizedText("Phone Number"));
        grid.addColumn(Account::getDebtLimit).setHeader(LanguageManager.getLocalizedText("Debt Limit"));
        grid.addColumn(Account::getStatus).setHeader(LanguageManager.getLocalizedText("State"));

        // Add item click listener to open account details view when a row is clicked
        grid.addItemClickListener(event -> {
            Account clickedAccount = event.getItem();
            openAccountDetails(clickedAccount);
        });

        return grid;
    }

    /**
     * Populates the account grid with data retrieved from the backend service.
     */
    private void populateAccountGrid() {
        List<Account> accounts = AccountOperations.getAllAccounts();

        // Check if accounts are available, then set the grid items
        if (accounts != null) {
            accountGrid.setItems(accounts);
        } else {
            accountGrid.setItems(); // Clear grid if no accounts are available
        }
    }

    /**
     * Opens the AdminAccountsDetailsView for the clicked account.
     *
     * @param account the account to be viewed in detail.
     */
    private void openAccountDetails(Account account) {
        AdminAccountsDetailsView adminAccountsDetailsView = new AdminAccountsDetailsView(account);
        adminAccountsDetailsView.open();
    }
}