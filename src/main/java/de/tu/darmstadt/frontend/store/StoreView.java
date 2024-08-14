package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
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

    private FlexLayout itemContainer;
    private List<ItemView> shopItemsList;

    public StoreView() {
        setWidthFull();
        setHeightFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);

        // Fetch all shop items
        List<Item> itemsList = ItemOperations.getAllShopItems();

        // Create a list of ItemView components
        shopItemsList = itemsList.stream()
                .map(ItemView::new)
                .collect(Collectors.toList());

        // Create the TextField for the search bar
        TextField searchBar = new TextField();
        searchBar.setPlaceholder("Search items...");
        searchBar.setWidth("80%"); // Set a width that aligns with the itemContainer
        searchBar.setValueChangeMode(ValueChangeMode.EAGER); // Set to trigger on each keystroke

        // Add a listener for each key press
        searchBar.addValueChangeListener(event -> filterItems(event.getValue()));

        // Create a layout to center the search bar
        HorizontalLayout searchBarLayout = new HorizontalLayout(searchBar);
        searchBarLayout.setWidthFull();
        searchBarLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        // Create the FlexLayout to hold the item views
        itemContainer = new FlexLayout();
        itemContainer.setHeightFull();
        itemContainer.setWidthFull();
        itemContainer.setFlexDirection(FlexLayout.FlexDirection.ROW);
        itemContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        itemContainer.setAlignItems(Alignment.STRETCH);
        itemContainer.setJustifyContentMode(JustifyContentMode.CENTER);

        // Add each ItemView to the FlexLayout
        shopItemsList.forEach(itemView -> itemContainer.add(itemView));

        // Create a container to hold the search bar and item container
        Div container = new Div();
        container.add(searchBarLayout, itemContainer);
        container.setHeightFull();
        container.setWidthFull();
        container.getStyle().set("overflow", "auto");

        // Add the container to the main layout
        add(container);
    }

    private void filterItems(String filterText) {
        itemContainer.removeAll();
        shopItemsList.stream()
                .filter(itemView -> itemView.getItem().getName().toLowerCase().contains(filterText.toLowerCase()))
                .forEach(itemView -> itemContainer.add(itemView));
    }
}



