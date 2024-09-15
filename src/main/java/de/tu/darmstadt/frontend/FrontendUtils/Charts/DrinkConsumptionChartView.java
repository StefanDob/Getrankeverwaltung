package de.tu.darmstadt.frontend.FrontendUtils.Charts;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.Constants;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendOperations.DrinkConsumptionService;
import de.tu.darmstadt.backend.backendOperations.TransactionOperations;
import de.tu.darmstadt.dataModel.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;

public class DrinkConsumptionChartView extends Details {

    private ChartJsComponent chartJsComponent;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;

    public DrinkConsumptionChartView() {
        // Set the title for the Details component
        super(new H2(LanguageManager.getLocalizedText("Consumption Chart")));
        setOpened(true);

        // Create a VerticalLayout to hold the content
        VerticalLayout contentLayout = new VerticalLayout();

        contentLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        contentLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);


        // Create date pickers for selecting the date range
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();

        // Set default values to show all transactions
        startDatePicker.setValue(LocalDate.now().minusYears(1));
        endDatePicker.setValue(LocalDate.now());



        // Add a button to apply the date range filter
        Button applyFilterButton = new Button("Apply Filter", e -> updateChart());

        // Add components to the date pickers layout
        HorizontalLayout datePickersLayout = new HorizontalLayout(startDatePicker, endDatePicker, applyFilterButton);

        // Initialize the chart with default data
        Map<String, Integer> drinkConsumption = DrinkConsumptionService.calculateDrinkConsumption(getTransactions(startDatePicker.getValue(), endDatePicker.getValue()));
        chartJsComponent = new ChartJsComponent(drinkConsumption);

        // Add components to the content layout
        contentLayout.add(datePickersLayout, chartJsComponent);

        // Set the content of the Details component
        setContent(contentLayout);

        // Add theme variants
        addThemeVariants(DetailsVariant.REVERSE);

        //first initialization of chart
        updateChart();

        setWidth("100%");
    }

    private void updateChart() {
        // Fetch transactions based on the selected date range
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        // Validate that the dates are valid and that the start date is before or the same as the end date
        if (startDate == null || endDate == null) {
            Notification.show("Please select both start and end dates.", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (startDate.isAfter(endDate)) {
            Notification.show("Start date cannot be after the end date.", 3000, Notification.Position.MIDDLE);
            return;
        }

        // Update the chart with the filtered data
        Map<String, Integer> filteredDrinkConsumption = DrinkConsumptionService.calculateDrinkConsumption(getTransactions(startDate, endDate));
        chartJsComponent.updateData(filteredDrinkConsumption);
    }

    protected List<Transaction> getTransactions(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = new ArrayList<>();
        // Fetch all transactions and filter by date range
        for (Transaction transaction : TransactionOperations.getAllTransactions()) {
            if (transaction.getReceiver() == Constants.getMasterID() &&
                    !transaction.getTransactionDate().toLocalDate().isBefore(startDate) &&
                    !transaction.getTransactionDate().toLocalDate().isAfter(endDate)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}






