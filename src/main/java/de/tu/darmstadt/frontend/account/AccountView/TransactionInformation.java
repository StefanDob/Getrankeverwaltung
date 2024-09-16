package de.tu.darmstadt.frontend.account.AccountView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendOperations.CookieOperations;
import de.tu.darmstadt.backend.backendOperations.TransactionOperations;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.frontend.FrontendUtils.Charts.AccountDrinkConsumptionChartView;
import de.tu.darmstadt.frontend.FrontendUtils.ViewTransactionDialog;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code TransactionInformation} class extends the Vaadin {@link Details} component to display a list of
 * transactions for the currently logged-in account. It includes a search field to filter transactions,
 * a button to add new transactions, and a logout option.
 */
public class TransactionInformation extends Details {

    private final Account currentAccount;
    private final Grid<Transaction> transactionGrid = new Grid<>(Transaction.class);
    private final TextField searchField = new TextField(LanguageManager.getLocalizedText("Search"));

    /**
     * Constructor for {@code TransactionInformation}. Initializes the layout and populates the grid
     * with transactions for the current account.
     */
    public TransactionInformation() {
        super(new H2(LanguageManager.getLocalizedText("Transactions")));
        this.currentAccount = SessionManagement.getAccount();

        setupLayout();
        populateGrid();
        alignColumnsToLeft();
    }

    /**
     * Sets up the layout of the component, including the search field, add transaction button,
     * and a logout button. Also adds a chart to the layout.
     */
    private void setupLayout() {
        setWidth("100%");
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");
        setOpened(true);
        transactionGrid.setHeight("55vh");

        // Configure the search field
        searchField.setPlaceholder(LanguageManager.getLocalizedText("Search by transaction Text"));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> filterTransactions(e.getValue()));
        searchField.setWidthFull();

        Button addTransactionButton = new Button(LanguageManager.getLocalizedText("Transfer Money"));
        addTransactionButton.addClickListener(e -> openNewTransactionDialog());

        Button logoutButton = new Button(LanguageManager.getLocalizedText("Logout"));
        logoutButton.addClickListener(e -> logout());

        VerticalLayout layout = new VerticalLayout(searchField, addTransactionButton, transactionGrid, new AccountDrinkConsumptionChartView());
        add(layout, logoutButton);
    }

    /**
     * Filters the transactions based on the search term entered in the search field.
     *
     * @param searchTerm the search term to filter the transactions.
     */
    private void filterTransactions(String searchTerm) {
        List<Transaction> transactions = TransactionOperations.getTransactionsById(currentAccount.getId());

        if (transactions != null) {
            transactions = transactions.reversed();
            // Filter transactions based on the search term
            transactions = transactions.stream()
                    .filter(t -> t.getSenderName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            t.getReceiverName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            t.getTransactionText().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
            transactionGrid.setItems(transactions);
        }
    }

    /**
     * Opens a dialog for creating a new transaction.
     */
    private void openNewTransactionDialog() {
        NewTransactionDialog newTransactionDialog = new NewTransactionDialog(currentAccount);
        newTransactionDialog.open();
    }

    /**
     * Logs out the current user, deletes the account session, and reloads the page.
     */
    private void logout() {
        CookieOperations.deleteCurrentAccount();
        SessionManagement.setAccount(null);
        UI.getCurrent().getPage().reload();
    }

    /**
     * Aligns all the columns of the transaction grid to the left.
     */
    private void alignColumnsToLeft() {
        for (Grid.Column<Transaction> column : transactionGrid.getColumns()) {
            column.setTextAlign(ColumnTextAlign.START);
        }
    }

    /**
     * Populates the transaction grid with data for the current account and customizes the columns
     * to display transaction details such as amount, sender, receiver, text, and date.
     */
    private void populateGrid() {
        List<Transaction> transactions = TransactionOperations.getTransactionsById(currentAccount.getId());
        transactions = transactions.reversed();

        if (transactions != null) {
            transactionGrid.setItems(transactions);
        }

        transactionGrid.removeAllColumns();

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

        transactionGrid.addItemClickListener(event -> {
            Transaction clickedTransaction = event.getItem();
            ViewTransactionDialog viewTransactionDialog = new ViewTransactionDialog(clickedTransaction);
            viewTransactionDialog.open();
        });
    }
}

