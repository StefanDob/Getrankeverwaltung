package de.tu.darmstadt.frontend.account.AccountView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.CookieOperations;
import de.tu.darmstadt.backend.backendService.TransactionOperations;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.frontend.FrontendUtils.ViewTransactionDialog;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The TransactionInformation class displays the current account's transaction history
 * in a grid format. It provides options to view transactions, transfer money, and log out.
 */
public class TransactionInformation extends Details {

    private final Account currentAccount;
    private final Grid<Transaction> transactionGrid = new Grid<>(Transaction.class);

    /**
     * Constructor for TransactionInformation.
     * Initializes the transaction information view with grid data and action buttons.
     */
    public TransactionInformation() {
        super(new H2(LanguageManager.getLocalizedText("Transactions")));
        this.currentAccount = SessionManagement.getAccount();

        setupLayout();
        populateGrid();
        alignColumnsToLeft();
    }

    /**
     * Sets up the layout for the transaction information, including buttons for adding a transaction and logging out.
     */
    private void setupLayout() {
        setWidth("100%");
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");
        setOpened(true);
        transactionGrid.setHeight("55vh");

        // Create and configure the "Add Transaction" button
        Button addTransactionButton = new Button(LanguageManager.getLocalizedText("Transfer Money"));
        addTransactionButton.addClickListener(e -> openNewTransactionDialog());

        // Create and configure the "Logout" button
        Button logoutButton = new Button(LanguageManager.getLocalizedText("Logout"));
        logoutButton.addClickListener(e -> logout());

        // Layout to contain the transaction grid and buttons
        VerticalLayout layout = new VerticalLayout(addTransactionButton, transactionGrid);
        add(layout, logoutButton);
    }

    /**
     * Opens the NewTransactionDialog for the user to create a new transaction.
     */
    private void openNewTransactionDialog() {
        NewTransactionDialog newTransactionDialog = new NewTransactionDialog(currentAccount);
        newTransactionDialog.open();
    }

    /**
     * Logs out the current user by clearing session data and refreshing the page.
     */
    private void logout() {
        CookieOperations.deleteCurrentAccount();
        SessionManagement.setAccount(null);
        UI.getCurrent().getPage().reload();
    }

    /**
     * Aligns all transaction grid columns to the left for consistency.
     */
    private void alignColumnsToLeft() {
        for (Grid.Column<Transaction> column : transactionGrid.getColumns()) {
            column.setTextAlign(ColumnTextAlign.START);
        }
    }

    /**
     * Populates the grid with the account's transaction history and sets up column headers and alignment.
     */
    private void populateGrid() {
        List<Transaction> transactions = TransactionOperations.getTransactionsById(currentAccount.getId());

        if (transactions != null) {
            transactionGrid.setItems(transactions);
        }

        // Clear any auto-generated columns
        transactionGrid.removeAllColumns();

        // Manually define the columns with appropriate headers and alignment
        transactionGrid.addColumn(transaction -> String.format("%.2f €", transaction.getAmount()))
                .setHeader(LanguageManager.getLocalizedText("Amount"))
                .setTextAlign(ColumnTextAlign.END);

        transactionGrid.addColumn(Transaction::getSenderName)
                .setHeader(LanguageManager.getLocalizedText("Sender"))
                .setTextAlign(ColumnTextAlign.START)
                .setSortable(true);

        transactionGrid.addColumn(Transaction::getReceiverName)
                .setHeader(LanguageManager.getLocalizedText("Receiver"))
                .setTextAlign(ColumnTextAlign.START)
                .setSortable(true);

        transactionGrid.addColumn(Transaction::getTransactionText)
                .setHeader(LanguageManager.getLocalizedText("Text"))
                .setTextAlign(ColumnTextAlign.START)
                .setSortable(true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        transactionGrid.addColumn(transaction -> transaction.getTransactionDate().format(formatter))
                .setHeader(LanguageManager.getLocalizedText("Date"))
                .setTextAlign(ColumnTextAlign.START);

        // Add item click listener to open transaction details when a row is clicked
        transactionGrid.addItemClickListener(event -> {
            Transaction clickedTransaction = event.getItem();
            ViewTransactionDialog viewTransactionDialog = new ViewTransactionDialog(clickedTransaction);
            viewTransactionDialog.open();

        });
    }
}
