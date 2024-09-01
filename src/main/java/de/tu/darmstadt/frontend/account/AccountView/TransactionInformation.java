package de.tu.darmstadt.frontend.account.AccountView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.backend.backendService.TransactionOperations;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
import de.tu.darmstadt.frontend.account.SessionManagement;

import java.util.List;

public class TransactionInformation extends Details {
    Account currentAccount;

    private final Grid<Transaction> transactionGrid = new Grid<>(Transaction.class);

    public TransactionInformation() {
        super(new H2("Transactions"));
        this.currentAccount = SessionManagement.getAccount();

        // Populate the grid with account information
        populateGrid();

        // Align all auto-generated columns to the left
        alignColumnsToLeft();

        // Set up the layout
        setWidth("100%");
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");
        setOpened(true);
        transactionGrid.setHeight("55vh");

        VerticalLayout verticalLayout = new VerticalLayout();
        Button addTransactionButton = new Button("Transfer Money");
        addTransactionButton.addClickListener(e -> {
            NewTransactionDialog newTransactionDialog = new NewTransactionDialog(currentAccount);
            newTransactionDialog.open();
        });
        verticalLayout.add(addTransactionButton);
        verticalLayout.add(transactionGrid);
        add(verticalLayout);

        Button logoutButton = new Button("Logout");
        logoutButton.addClickListener(e -> {
            SessionManagement.setAccount(null);
            UI.getCurrent().getPage().reload();
        });

        add(logoutButton);
    }

    private void alignColumnsToLeft() {
        // Iterate over all columns and set their text alignment to start (left)
        for (Grid.Column<Transaction> column : transactionGrid.getColumns()) {
            column.setTextAlign(ColumnTextAlign.START);
        }
    }

    private void populateGrid() {
        List<Transaction> transactions = TransactionOperations.getTransactionsById(currentAccount.getId());

        if(transactions != null){
            transactionGrid.setItems(transactions);
        }

        transactionGrid.removeAllColumns();

        // Manually define columns
        transactionGrid.addColumn(Transaction::getAmount).setHeader("Amount").setTextAlign(ColumnTextAlign.END);
        transactionGrid.addColumn(Transaction::getSenderName).setHeader("Sender").setTextAlign(ColumnTextAlign.START);
        transactionGrid.addColumn(Transaction::getReceiverName).setHeader("Receiver").setTextAlign(ColumnTextAlign.START);
        transactionGrid.addColumn(Transaction::getTransactionText).setHeader("Text").setTextAlign(ColumnTextAlign.START);
        transactionGrid.addColumn(Transaction::getTransactionDate).setHeader("Date").setTextAlign(ColumnTextAlign.START);


    }

}
