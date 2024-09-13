package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.dataModel.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrinkConsumptionService {

    // Regex pattern for items in the form: "Item Name x quantity"
    private static final Pattern ITEM_PATTERN = Pattern.compile("([A-Za-z ]+)\\s*x\\s*(\\d+)");

    public static Map<String, Integer> calculateDrinkConsumption(List<Transaction> transactions) {
        Map<String, Integer> drinkConsumption = new HashMap<>();

        for (Transaction transaction : transactions) {
            String description = transaction.getTransactionText();

            if (description.contains(";")) { // indicates shopping cart
                // Split the description by semicolons to get individual items
                String[] items = description.split(";");

                for (String item : items) {
                    // Use the regex matcher to extract the item name and quantity
                    Matcher matcher = ITEM_PATTERN.matcher(item);
                    if (matcher.find()) {
                        String drinkName = matcher.group(1).trim();
                        int quantity = Integer.parseInt(matcher.group(2).trim());

                        drinkConsumption.put(drinkName,
                                drinkConsumption.getOrDefault(drinkName, 0) + quantity);
                    }
                }
            } else { // direct sale
                // Use regex to handle direct sales as well
                Matcher matcher = ITEM_PATTERN.matcher(description);
                if (matcher.find()) {
                    String drinkName = matcher.group(1).trim();
                    int quantity = Integer.parseInt(matcher.group(2).trim());

                    drinkConsumption.put(drinkName,
                            drinkConsumption.getOrDefault(drinkName, 0) + quantity);
                } else {
                    // If no quantity is provided, assume the quantity is 1
                    if (description.trim().matches("[A-Za-z ]+")) {
                        String drinkName = description.trim();
                        drinkConsumption.put(drinkName, drinkConsumption.getOrDefault(drinkName, 0) + 1);
                    }
                }
            }
        }

        return drinkConsumption;
    }
}



