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

/**
 * A view component for displaying a chart of drink consumption data.
 * Allows users to filter data based on a date range and updates the chart accordingly.
 */
public class DrinkConsumptionChartView extends Details {

    private ChartJsComponent chartJsComponent;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;

    /**
     * Constructs a new DrinkConsumptionChartView instance.
     * Initializes the UI components, sets default date range, and configures layout.
     */
    public DrinkConsumptionChartView() {
        // Set the title for the Details component
        super(new H2(LanguageManager.getLocalizedText("Consumption Chart")));
        setOpened(true);

        // Create a VerticalLayout to hold the content
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        contentLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Initialize date pickers for selecting the date range
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();

        // Set default values to show transactions from the past year to today
        startDatePicker.setValue(LocalDate.now().minusYears(1));
        endDatePicker.setValue(LocalDate.now());

        // Create and configure the button to apply the date range filter
        Button applyFilterButton = new Button("Apply Filter", e -> updateChart());

        // Arrange date pickers and filter button in a horizontal layout
        HorizontalLayout datePickersLayout = new HorizontalLayout(startDatePicker, endDatePicker, applyFilterButton);

        // Initialize the chart with default data based on the default date range
        Map<String, Integer> drinkConsumption = DrinkConsumptionService.calculateDrinkConsumption(getTransactions(startDatePicker.getValue(), endDatePicker.getValue()));
        chartJsComponent = new ChartJsComponent(drinkConsumption);

        // Add date pickers, filter button, and chart to the content layout
        contentLayout.add(datePickersLayout, chartJsComponent);

        // Set the content of the Details component to the content layout
        setContent(contentLayout);

        // Add theme variants for styling
        addThemeVariants(DetailsVariant.REVERSE);

        // Initialize chart with default data
        updateChart();

        // Set width to 100% to fill the available space
        setWidth("100%");
    }

    /**
     * Updates the chart with data filtered by the selected date range.
     * Validates the date range and fetches filtered transactions before updating the chart.
     */
    private void updateChart() {
        // Get the selected date range from the date pickers
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        // Validate that both dates are selected and the start date is not after the end date
        if (startDate == null || endDate == null) {
            Notification.show("Please select both start and end dates.", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (startDate.isAfter(endDate)) {
            Notification.show("Start date cannot be after the end date.", 3000, Notification.Position.MIDDLE);
            return;
        }

        // Fetch and filter transactions based on the selected date range, then update the chart
        Map<String, Integer> filteredDrinkConsumption = DrinkConsumptionService.calculateDrinkConsumption(getTransactions(startDate, endDate));
        chartJsComponent.updateData(filteredDrinkConsumption);
    }

    /**
     * Retrieves a list of transactions within the specified date range.
     *
     * @param startDate The start date of the date range.
     * @param endDate The end date of the date range.
     * @return A list of transactions within the specified date range.
     */
    protected List<Transaction> getTransactions(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = new ArrayList<>();
        // Fetch all transactions and filter them by the selected date range
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







