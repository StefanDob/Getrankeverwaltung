package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.frontend.MainLayout;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Store")
@Route(value = "store", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class StoreView extends VerticalLayout {





    public StoreView() {
        setSpacing(false); // Adjust spacing as needed

        List<Item> itemsList = ItemOperations.getAllShopItems();

        ArrayList<ItemView> shopItemsList = new ArrayList<>();
        for(Item item : itemsList){
            ItemView view  = new ItemView(item);
            shopItemsList.add(view);
        }

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



}