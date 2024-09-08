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
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.frontend.MainLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The StoreView class represents the main view of the store, displaying a list of items available
 * for purchase. It includes a search bar to filter items by name and a dynamic layout to show
 * each item as a clickable component.
 */
@PageTitle("Store")
@Route(value = "store", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class StoreView extends VerticalLayout {

    // Container for displaying item views
    private FlexLayout itemContainer;

    // List of ItemView components representing each item in the store
    private List<ItemView> shopItemsList;

    /**
     * Constructor that initializes the store view layout and components.
     */
    public StoreView() {
        // Set layout properties for full screen usage
        setWidthFull();
        setHeightFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);  // Align items to the center horizontally

        // Fetch all shop items from the database or service
        List<Item> itemsList = ItemOperations.getAllShopItems();

        // Convert each Item object to an ItemView component and store in a list
        shopItemsList = itemsList.stream()
                .map(ItemView::new)
                .collect(Collectors.toList());

        // Initialize and configure the search bar
        TextField searchBar = new TextField();
        searchBar.setPlaceholder(LanguageManager.getLocalizedText("Search items..."));  // Placeholder text for the search bar
        searchBar.setWidth("80%");  // Set width to match the item container layout
        searchBar.setValueChangeMode(ValueChangeMode.EAGER);  // Trigger value changes on each keystroke

        // Add listener to filter items when the user types in the search bar
        searchBar.addValueChangeListener(event -> filterItems(event.getValue()));

        // Create a layout for centering the search bar at the top of the view
        HorizontalLayout searchBarLayout = new HorizontalLayout(searchBar);
        searchBarLayout.setWidthFull();  // Occupy full width of the parent layout
        searchBarLayout.setJustifyContentMode(JustifyContentMode.CENTER);  // Center the search bar horizontally

        // Initialize the FlexLayout to display the items
        itemContainer = new FlexLayout();
        itemContainer.setHeightFull();
        itemContainer.setWidthFull();
        itemContainer.setFlexDirection(FlexLayout.FlexDirection.ROW);  // Arrange items in a row
        itemContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);  // Allow items to wrap to the next row
        itemContainer.setAlignItems(Alignment.STRETCH);  // Stretch items to fill available space
        itemContainer.setJustifyContentMode(JustifyContentMode.CENTER);  // Center the items horizontally

        // Add each ItemView component to the item container layout
        shopItemsList.forEach(itemView -> itemContainer.add(itemView));

        // Create a container to hold the search bar and item views, and ensure scrolling is allowed
        Div container = new Div();
        container.add(searchBarLayout, itemContainer);  // Add search bar and items to the container
        container.setHeightFull();
        container.setWidthFull();
        container.getStyle().set("overflow", "auto");  // Enable scrolling if content exceeds view size

        // Add the main container to the StoreView layout
        add(container);
    }

    /**
     * Filters the displayed items based on the input text in the search bar.
     * It compares the item names to the search text and updates the display accordingly.
     *
     * @param filterText The text used to filter items by name.
     */
    private void filterItems(String filterText) {
        // Clear all items from the container before re-adding filtered ones
        itemContainer.removeAll();

        // Filter the list of item views based on the input text, case-insensitive
        shopItemsList.stream()
                .filter(itemView -> itemView.getItem().getName().toLowerCase().contains(filterText.toLowerCase()))
                .forEach(itemView -> itemContainer.add(itemView));  // Add the matching items back to the container
    }
}



