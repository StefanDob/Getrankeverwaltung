package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
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
import java.util.stream.Collectors;

@PageTitle("Store")
@Route(value = "store", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class StoreView extends VerticalLayout {

    public StoreView() {
        setWidthFull();
        // Fetch all shop items
        List<Item> itemsList = ItemOperations.getAllShopItems();

        // Create a list of ItemView components
        List<ItemView> shopItemsList = itemsList.stream()
                .map(ItemView::new)
                .collect(Collectors.toList());

        // Create the FlexLayout to hold the item views
        FlexLayout itemContainer = new FlexLayout();
        itemContainer.setWidthFull();
        itemContainer.setFlexDirection(FlexLayout.FlexDirection.ROW);
        itemContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        itemContainer.setAlignItems(FlexComponent.Alignment.START);
        itemContainer.setJustifyContentMode(JustifyContentMode.CENTER);

        // Add each ItemView to the FlexLayout
        shopItemsList.forEach(itemView -> itemContainer.add(itemView));

        // Add the FlexLayout to the main layout
        add(itemContainer);
    }

}



