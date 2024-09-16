package de.tu.darmstadt.frontend.FrontendUtils.Charts;

import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.backend.backendOperations.TransactionOperations;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A view component for displaying a chart of drink consumption data specific to the current account.
 * Extends DrinkConsumptionChartView to provide account-specific transaction filtering.
 */
public class AccountDrinkConsumptionChartView extends DrinkConsumptionChartView {

    /**
     * Constructs a new AccountDrinkConsumptionChartView instance.
     * Initializes the UI with custom styling for the border and border radius.
     */
    public AccountDrinkConsumptionChartView() {
        super();
        // Apply custom styling to the component
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");
    }

    /**
     * Retrieves a list of transactions within the specified date range, filtered by the current account.
     * Only transactions where the current account is either the sender or receiver are included.
     *
     * @param startDate The start date of the date range.
     * @param endDate The end date of the date range.
     * @return A list of transactions specific to the current account within the specified date range.
     */
    @Override
    protected List<Transaction> getTransactions(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = new ArrayList<>();
        Account currentAccount = SessionManagement.getAccount();
        // Fetch all transactions and filter them by date range and account involvement
        for (Transaction transaction : TransactionOperations.getAllTransactions()) {
            if ((transaction.getReceiver() == currentAccount.getId() || transaction.getSender() == currentAccount.getId()) &&
                    !transaction.getTransactionDate().toLocalDate().isBefore(startDate) &&
                    !transaction.getTransactionDate().toLocalDate().isAfter(endDate)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}

