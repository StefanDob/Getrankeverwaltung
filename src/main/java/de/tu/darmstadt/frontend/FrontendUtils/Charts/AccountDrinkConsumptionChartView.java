package de.tu.darmstadt.frontend.FrontendUtils.Charts;

import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.backend.backendOperations.TransactionOperations;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountDrinkConsumptionChartView extends DrinkConsumptionChartView{
    public AccountDrinkConsumptionChartView() {
        super();
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");
    }

    protected List<Transaction> getTransactions(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = new ArrayList<>();
        Account currentAccount = SessionManagement.getAccount();
        // Fetch all transactions and filter by date range
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
