package de.tu.darmstadt.frontend.FrontendUtils.Charts;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Div;

import java.util.Map;

/**
 * A Vaadin component that renders a Chart.js bar chart based on the provided data.
 * Extends Div to integrate a Chart.js chart into the Vaadin application.
 */
public class ChartJsComponent extends Div {

    private Map<String, Integer> data; // Drink consumption data

    /**
     * Constructs a new ChartJsComponent with the specified data.
     * Initializes the component with the provided data and sets the component's ID.
     *
     * @param data The initial data to be displayed in the chart.
     */
    public ChartJsComponent(Map<String, Integer> data) {
        this.data = data;
        setId("chartContainer");
    }

    /**
     * Called when the component is attached to the DOM.
     * Adds the canvas element for the chart and executes JavaScript to render the chart.
     *
     * @param attachEvent The attach event triggered when the component is added to the DOM.
     */
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // Add the canvas element to the DOM
        getElement().setProperty("innerHTML", "<canvas id='drinkChart' width='800' height='500'></canvas>");

        // Ensure the JS runs after the canvas is attached
        getElement().executeJs(generateChartJsCode(data));
    }

    /**
     * Updates the chart with new data.
     * Clears the existing chart and re-renders it with the updated data.
     *
     * @param newData The new data to be displayed in the chart.
     */
    public void updateData(Map<String, Integer> newData) {
        this.data = newData;

        // Re-generate the Chart.js code with the updated data
        String chartJsCode = generateChartJsCode(data);

        // Clear the existing chart and update it with new data
        getElement().executeJs(
                "document.getElementById('drinkChart').remove();" +
                        "document.getElementById('chartContainer').innerHTML = '<canvas id=\"drinkChart\" width=\"800\" height=\"500\"></canvas>';" +
                        chartJsCode
        );
    }

    /**
     * Generates the JavaScript code to initialize and render the Chart.js chart.
     * The chart configuration includes labels, data, and styling options.
     *
     * @param data The data to be displayed in the chart.
     * @return A string containing the JavaScript code to render the chart.
     */
    private String generateChartJsCode(Map<String, Integer> data) {
        // Create JavaScript code to initialize the chart
        StringBuilder labels = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            labels.append("'").append(entry.getKey()).append("',");
            values.append(entry.getValue()).append(",");
        }

        // Chart.js initialization script
        return "var ctx = document.getElementById('drinkChart').getContext('2d');" +
                "new Chart(ctx, {" +
                "    type: 'bar'," +
                "    data: {" +
                "        labels: [" + labels.toString() + "]," +
                "        datasets: [{" +
                "            label: 'Item Consumption'," +
                "            data: [" + values.toString() + "]," +
                "            backgroundColor: 'rgba(75, 192, 192, 0.2)'," +
                "            borderColor: 'rgba(75, 192, 192, 1)'," +
                "            borderWidth: 1" +
                "        }]" +
                "    }," +
                "    options: {" +
                "        responsive: true," +
                "        scales: {" +
                "            y: {" +
                "                beginAtZero: true" +
                "            }" +
                "        }" +
                "    }" +
                "});";
    }
}


