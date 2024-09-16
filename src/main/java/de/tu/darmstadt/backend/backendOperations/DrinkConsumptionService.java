package de.tu.darmstadt.backend.backendOperations;

import de.tu.darmstadt.dataModel.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@link DrinkConsumptionService} class provides functionality to calculate drink consumption
 * based on a list of transactions. It parses item descriptions using regex patterns and aggregates
 * the quantities of drinks sold.
 */
public class DrinkConsumptionService {

    // Regex pattern to match items in the format: "Item Name x quantity"
    private static final Pattern ITEM_PATTERN = Pattern.compile("([A-Za-z ]+)\\s*x\\s*(\\d+)");

    /**
     * Calculates the total drink consumption from a list of transactions.
     * Each transaction may involve a single drink or multiple drinks in a shopping cart format.
     *
     * @param transactions the list of {@link Transaction} objects containing transaction descriptions.
     * @return a map where the keys are drink names and the values are the total quantities sold.
     */
    public static Map<String, Integer> calculateDrinkConsumption(List<Transaction> transactions) {
        Map<String, Integer> drinkConsumption = new HashMap<>();

        for (Transaction transaction : transactions) {
            String description = transaction.getTransactionText();

            if (description.contains(";")) { // Shopping cart with multiple items
                // Split the description by semicolons to get individual items
                String[] items = description.split(";");

                for (String item : items) {
                    // Use regex to extract the drink name and quantity
                    processItem(item, drinkConsumption);
                }
            } else { // Direct sale
                // Handle direct sales (single item) using regex
                processItem(description, drinkConsumption);
            }
        }

        return drinkConsumption;
    }

    /**
     * Helper method to process a single item description, extract the drink name and quantity,
     * and update the drink consumption map.
     *
     * @param item the item description to process.
     * @param drinkConsumption the map tracking drink names and their respective quantities.
     */
    private static void processItem(String item, Map<String, Integer> drinkConsumption) {
        Matcher matcher = ITEM_PATTERN.matcher(item);

        if (matcher.find()) {
            // Extract drink name and quantity using regex
            String drinkName = matcher.group(1).trim();
            int quantity = Integer.parseInt(matcher.group(2).trim());

            // Update the map with the drink name and its corresponding quantity
            drinkConsumption.put(drinkName,
                    drinkConsumption.getOrDefault(drinkName, 0) + quantity);
        } else {
            // Handle the case where no quantity is specified, assuming a default quantity of 1
            if (item.trim().matches("[A-Za-z ]+")) {
                String drinkName = item.trim();
                drinkConsumption.put(drinkName, drinkConsumption.getOrDefault(drinkName, 0) + 1);
            }
        }
    }
}




