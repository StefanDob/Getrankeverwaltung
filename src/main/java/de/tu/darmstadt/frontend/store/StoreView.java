package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.ItemImage;
import de.tu.darmstadt.frontend.MainLayout;

import java.util.ArrayList;

@PageTitle("Store")
@Route(value = "store", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class StoreView extends VerticalLayout {





    public StoreView() {
        setSpacing(false); // Adjust spacing as needed

        ArrayList<ItemView> shopItemsList = initializeItemsList();

        displayShopItems(4, shopItemsList);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        getStyle().set("text-align", "center");
    }

    private void displayShopItems(int itemsPerLine, ArrayList<ItemView> shopItemsList) {
        VerticalLayout lines = new VerticalLayout();
        lines.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(lines);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        lines.add(horizontalLayout);
        int counter = 0;
        for(ItemView element : shopItemsList){
            if(counter < itemsPerLine){
                horizontalLayout.add(element);
                counter++;
            }else{
                counter = 1;
                horizontalLayout = new HorizontalLayout();
                horizontalLayout.add(element);
                lines.add(horizontalLayout);
            }
        }


    }

    /**
     * helping method for creating 100 cola and Fanta elements for the Webshop
     * @return list of shopelements that can be used for testing porpuses
     */
    private ArrayList<ItemView> initializeItemsList() {
        ArrayList<ItemView> shopItemsList = new ArrayList<>();
        for(int i = 0; i <= 50; i++){

            String description = "Zutaten: Wasser, Zucker, Kohlensäure, Farbstoff E 150d, Säuerungsmittel Phosphorsäure, Aroma, Aroma Koffein.";

            ItemView colaItemView = new ItemView(new Item(10,"Coca Cola",new ItemImage("images/cola.jpg"),description));
            shopItemsList.add(colaItemView);

            ItemView fantaItemView = new ItemView(new Item(15,"Fanta",new ItemImage("images/fanta.jpg"),description));

            shopItemsList.add(fantaItemView);
        }
        return shopItemsList;
    }

}
