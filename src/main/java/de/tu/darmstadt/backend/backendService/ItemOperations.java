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
    public static @NotNull ArrayList<ItemView> getAllShopItems(){
        // TODO implement a logic to get all shopitems from the database @Toni
        return initializeTestItemsList();
    }

    /**
     * This method returns an {@link Item} wrapped in an {@link ItemView} by getting a specified ID.
     * If the ID with the corresponding {@link Item} does not exist, this method simply returns {@code null}.
     * @param ID the specified ID
     * @return the {@link ItemView} with the {@link Item} within. If the ID does not exist, return {@code null}.
     */
    private static @Nullable ItemView getItemById(String ID) {
        ItemService itemService = SpringContext.getBean(ItemService.class);
        Item itemOptional =  itemService.getItemByID(ID).orElse(null);

        return itemOptional != null ? new ItemView(itemOptional) : null;
    }

    /**
     * helping method for creating 100 cola and Fanta elements for the Webshop
     * @return list of shopelements that can be used for testing porpuses
     */
    @Contract(" -> new")
    public static @NotNull ArrayList<ItemView> initializeTestItemsList() {
        ArrayList<ItemView> shopItemsList = new ArrayList<>();
        for(int i = 0; i <= 50; i++){

            String description = "Zutaten: Wasser, Zucker, Kohlensäure, Farbstoff E 150d, Säuerungsmittel Phosphorsäure, Aroma, Aroma Koffein.";

            try {
                ItemView colaItemView = new ItemView(new Item(10, "Coca Cola", new ItemImage("images/cola.jpg"), description));
                shopItemsList.add(colaItemView);

                ItemView fantaItemView = new ItemView(new Item(15, "Fanta", new ItemImage("images/fanta.jpg"), description));

                shopItemsList.add(fantaItemView);
            } catch (ItemPropertiesException e) {
                e.printStackTrace();
            } // end of try-catch

        }
        return shopItemsList;
    }
}
