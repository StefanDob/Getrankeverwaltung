package de.tu.darmstadt.frontend.admin;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.Constants;
import de.tu.darmstadt.backend.backendService.DrinkConsumptionService;
import de.tu.darmstadt.backend.backendService.TransactionOperations;
import de.tu.darmstadt.dataModel.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrinkConsumptionChartView extends VerticalLayout {

    public DrinkConsumptionChartView() {

        // Fetch and pass the drink consumption data
        Map<String, Integer> drinkConsumption = DrinkConsumptionService.calculateDrinkConsumption(getTransactions());
        System.out.println(drinkConsumption.toString());
        ChartJsComponent chartJsComponent = new ChartJsComponent(drinkConsumption);
        add(chartJsComponent);
    }

    private List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        // Fetch transactions for the last month from your database
        for(Transaction transaction : TransactionOperations.getAllTransactions()){
            if(transaction.getReceiver() == Constants.getMasterID()){
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}


