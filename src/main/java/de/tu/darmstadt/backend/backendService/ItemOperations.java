package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.exceptions.items.ItemPropertiesException;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.ItemImage;
import de.tu.darmstadt.frontend.store.ItemView;

import java.util.ArrayList;

public class ItemOperations {
    /**
     * this method gets all the Items of the shop from the database
     * @return all shopitems from the database
     */
    public static ArrayList<ItemView> getAllShopItems(){
        //TODO implement a logic to get all shopitems from the database @Toni
        return initializeTestItemsList();
    }



    /**
     * helping method for creating 100 cola and Fanta elements for the Webshop
     * @return list of shopelements that can be used for testing porpuses
     */
    public static ArrayList<ItemView> initializeTestItemsList() {
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
