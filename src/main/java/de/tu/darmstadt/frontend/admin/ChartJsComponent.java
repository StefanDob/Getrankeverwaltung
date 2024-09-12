package de.tu.darmstadt.frontend.admin;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Div;

import java.util.Map;

public class ChartJsComponent extends Div {

    private Map<String, Integer> data; // Drink consumption data

    public ChartJsComponent(Map<String, Integer> data) {
        this.data = data;
        setId("chartContainer");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // Add the canvas element to the DOM
        getElement().setProperty("innerHTML", "<canvas id='drinkChart' width='600' height='400'></canvas>");

        // Ensure the JS runs after canvas is attached
        getElement().executeJs(generateChartJsCode(data));
    }

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


