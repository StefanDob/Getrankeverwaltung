package de.tu.darmstadt.frontend.admin.AdminLists;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.backendService.TransactionOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * The TransactionListView class represents a view for displaying a list of transactions
 * in a grid format. It allows users to view the sender, receiver, transaction text, amount,
 * and date of each transaction. The class also provides functionality to click on a transaction
 * row for potential editing (TODO).
 */
public class TransactionListView extends VerticalLayout {

    private Grid<Transaction> transactionGrid;
    private ListDataProvider<Transaction> dataProvider; // To manage data for the grid
    private TextField searchField; // Search field

    /**
     * Constructor that initializes the transaction list view.
     */
    public TransactionListView() {
        // Set layout properties
        setHeight("600px");  // Set fixed height for the view
        getStyle().set("overflow", "auto");  // Allow scrolling if content overflows

        // Initialize the layout and components
        initializeTransactionListView();
    }

    /**
     * Initializes the transaction list view by adding a title and setting up the transaction grid.
     */
    private void initializeTransactionListView() {
        // Add a title for the transaction list
        H3 title = new H3(LanguageManager.getLocalizedText("Transactions List"));
        add(title);

        // Create and add the search field
        searchField = new TextField(LanguageManager.getLocalizedText("Search"));
        searchField.setWidthFull();
        searchField.setPlaceholder(LanguageManager.getLocalizedText("Search by sender, receiver, or text"));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(event -> filterTransactionGrid(event.getValue()));
        add(searchField);

        // Create and configure the transaction grid
        transactionGrid = createTransactionGrid();
        add(transactionGrid);  // Add the grid to the layout

        // Populate the grid with transaction data
        populateTransactionGrid();
    }

    /**
     * Creates and configures the transaction grid with columns for sender, receiver, transaction text,
     * amount, and date. It also sets up click event listeners for each row in the grid.
     *
     * @return A Grid component configured for displaying transactions.
     */
    private Grid<Transaction> createTransactionGrid() {
        Grid<Transaction> grid = new Grid<>(Transaction.class, false);
        grid.addClassName("transaction-grid");  // Add a CSS class for custom styling

        // Add column for sender's full name
        grid.addColumn(transaction -> {
            Account sender = null;
            try {
                sender = AccountOperations.getAccountByID(transaction.getSender());
            } catch (AccountPolicyException e) {
                throw new RuntimeException(e);  // Handle the exception if account retrieval fails
            }
            return sender.getFirstName() + " " + sender.getLastName();  // Return the full name of the sender
        }).setHeader(LanguageManager.getLocalizedText("Sender"));

        // Add column for receiver's full name
        grid.addColumn(transaction -> {
            Account receiver = null;
            try {
                receiver = AccountOperations.getAccountByID(transaction.getReceiver());
            } catch (AccountPolicyException e) {
                throw new RuntimeException(e);  // Handle the exception if account retrieval fails
            }
            return receiver.getFirstName() + " " + receiver.getLastName();  // Return the full name of the receiver
        }).setHeader(LanguageManager.getLocalizedText("Receiver"));

        // Add column for transaction description (text)
        grid.addColumn(Transaction::getTransactionText)
                .setHeader(LanguageManager.getLocalizedText("Transaction Text"));

        // Add column for transaction amount
        grid.addColumn(transaction -> String.format("%.2f €", transaction.getAmount()))
                .setHeader(LanguageManager.getLocalizedText("Amount"));


        // Add column for transaction date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        grid.addColumn(transaction -> transaction.getTransactionDate().format(formatter))
                .setHeader(LanguageManager.getLocalizedText("Date"));


        // Add item click listener to open transaction details when a row is clicked
        grid.addItemClickListener(event -> {
            Transaction clickedTransaction = event.getItem();
            // TODO: Open a dialog to allow editing transactions
        });

        return grid;
    }

    /**
     * Populates the transaction grid with data by retrieving all transactions from
     * the TransactionOperations class.
     */
    private void populateTransactionGrid() {
        // Retrieve all transactions
        List<Transaction> transactions = TransactionOperations.getAllTransactions();

        // If transactions are available, set them to the grid; otherwise, clear the grid
        if (transactions != null) {
            dataProvider = new ListDataProvider<>(transactions); // Set up the data provider
            transactionGrid.setDataProvider(dataProvider);
        } else {
            transactionGrid.setItems();  // Clear the grid if no transactions are found
        }
    }

    /**
     * Filters the transaction grid based on the search query.
     *
     * @param filterText the search text to filter by.
     */
    private void filterTransactionGrid(String filterText) {
        if (dataProvider != null) {
            String lowerCaseFilter = filterText.toLowerCase();

            dataProvider.setFilter(transaction -> {
                Account sender = null;
                Account receiver = null;
                try {
                    sender = AccountOperations.getAccountByID(transaction.getSender());
                    receiver = AccountOperations.getAccountByID(transaction.getReceiver());
                } catch (AccountPolicyException e) {
                    throw new RuntimeException(e);  // Handle the exception if account retrieval fails
                }

                boolean matchesSender = sender != null && (sender.getFirstName() + " " + sender.getLastName()).toLowerCase().contains(lowerCaseFilter);
                boolean matchesReceiver = receiver != null && (receiver.getFirstName() + " " + receiver.getLastName()).toLowerCase().contains(lowerCaseFilter);
                boolean matchesText = transaction.getTransactionText() != null && transaction.getTransactionText().toLowerCase().contains(lowerCaseFilter);

                return matchesSender || matchesReceiver || matchesText;
            });
        }
    }
}

