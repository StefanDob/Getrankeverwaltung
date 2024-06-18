package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.database.ItemService;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.backend.exceptions.items.ItemPropertiesException;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.ItemImage;
import de.tu.darmstadt.frontend.store.ItemView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;

public class ItemOperations {
    /**
     * this method gets all the Items of the shop from the database
     * @return all shopitems from the database
     */
    @Contract(" -> new")
    public static @NotNull ArrayList<Item> getAllShopItems(){
        // TODO implement a logic to get all shopitems from the database @Toni
        return initializeTestItemsList();
    }

    /**
     * This method returns an {@link Item} wrapped in an {@link ItemView} by getting a specified ID.
     * If the ID with the corresponding {@link Item} does not exist, this method simply returns {@code null}.
     * @param ID the specified ID
     * @return the {@link ItemView} with the {@link Item} within. If the ID does not exist, return {@code null}.
     */
    public static @Nullable Item getItemById(String ID) {
        /*
        ItemService itemService = SpringContext.getBean(ItemService.class);
        Item itemOptional = itemService.getItemByID(ID).orElse(null);

        return itemOptional != null ? new ItemView(itemOptional) : null;
        */
        ItemService itemService = SpringContext.getBean(ItemService.class);
        return itemService.getItemByID(ID).orElse(null);
    }

    /**
     * helping method for creating 100 cola and Fanta elements for the Webshop
     * @return list of shopelements that can be used for testing porpuses
     */
    @Contract(" -> new")
    public static @NotNull ArrayList<Item> initializeTestItemsList() {
        ArrayList<Item> shopItemsList = new ArrayList<>();
        for(int i = 0; i <= 50; i++){

            String description = "Zutaten: Wasser, Zucker, Kohlensäure, Farbstoff E 150d, Säuerungsmittel Phosphorsäure, Aroma, Aroma Koffein.";

            try {
                Item colaItem = new Item(10, "Coca Cola", "images/cola.jpg", description);
                shopItemsList.add(colaItem);

                Item fantaItem = new Item(15, "Fanta", "images/fanta.jpg", description);

                shopItemsList.add(fantaItem);
            } catch (ItemPropertiesException e) {
                e.printStackTrace();
            } // end of try-catch

        }
        return shopItemsList;
    }

    public static void createItem(Item item) {
        ItemService itemService = SpringContext.getBean(ItemService.class);
        itemService.saveItem(item);
    }
}
