package de.tu.darmstadt.frontend.admin.AdminLists;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.frontend.FrontendUtils.ItemManagment.CreateItemDialog;
import de.tu.darmstadt.frontend.FrontendUtils.ItemManagment.ItemAdminDialog;

import java.util.List;

/**
 * ItemListView displays a list of items in a grid format.
 * It includes functionality to view item details and create new items.
 */
public class ItemListView extends VerticalLayout {

    private Grid<Item> itemGrid;
    private ListDataProvider<Item> dataProvider; // To manage data for the grid
    private TextField searchField; // Search field

    /**
     * Constructs the ItemListView and initializes its components.
     */
    public ItemListView() {
        // Configure the layout and initialize the view components
        configureLayout();
        initializeComponents();
    }

    /**
     * Configures the layout of the ItemListView.
     */
    private void configureLayout() {
        setHeight("900px");
        getStyle().set("overflow", "auto");
    }

    /**
     * Initializes and adds the components to the ItemListView.
     */
    private void initializeComponents() {
        // Create and add the search field
        searchField = new TextField(LanguageManager.getLocalizedText("Search"));
        searchField.setWidthFull();
        searchField.setPlaceholder(LanguageManager.getLocalizedText("Search by name or description"));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(event -> filterItemGrid(event.getValue()));
        add(searchField);

        // Add other components
        add(createTitle(), createItemGrid(), createCreateItemButton());
    }

    /**
     * Creates the title for the item list view.
     *
     * @return H3 title with localized text for "Items List".
     */
    private H3 createTitle() {
        return new H3(LanguageManager.getLocalizedText("Items List"));
    }

    /**
     * Creates and configures the grid to display items.
     *
     * @return Configured Grid component for items.
     */
    private Grid<Item> createItemGrid() {
        itemGrid = new Grid<>(Item.class, false);
        itemGrid.addClassName("item-grid");

        // Add columns to the grid
        itemGrid.addComponentColumn(item -> createItemImage(item))
                .setHeader(LanguageManager.getLocalizedText("Image"));
        itemGrid.addColumn(Item::getName)
                .setHeader(LanguageManager.getLocalizedText("Name"));
        itemGrid.addColumn(Item::getDescription)
                .setHeader(LanguageManager.getLocalizedText("Description"));
        itemGrid.addColumn(Item::getItemPriceAsString)
                .setHeader(LanguageManager.getLocalizedText("Price"));
        itemGrid.addColumn(Item::getStock)
                .setHeader(LanguageManager.getLocalizedText("Stock"));

        // Populate the grid with items
        List<Item> items = ItemOperations.getAllShopItems();
        dataProvider = new ListDataProvider<>(items); // Set up the data provider
        itemGrid.setDataProvider(dataProvider);

        // Add item click listener
        itemGrid.addItemClickListener(event -> openItemDialog(event.getItem()));

        return itemGrid;
    }

    /**
     * Creates an image component for displaying item images.
     *
     * @param item The item for which to display the image.
     * @return Image component for the item.
     */
    private Image createItemImage(Item item) {
        Image image = new Image(item.getImageAsResource(), LanguageManager.getLocalizedText("Item image"));
        image.setWidth("100px");
        image.setHeight("100px");
        return image;
    }

    /**
     * Opens a dialog to view or edit the selected item.
     *
     * @param item The item to view or edit.
     */
    private void openItemDialog(Item item) {
        ItemAdminDialog itemDialog = new ItemAdminDialog(item);
        itemDialog.open();
    }

    /**
     * Creates a button to open a dialog for creating new items.
     *
     * @return Button to create a new item.
     */
    private Button createCreateItemButton() {
        Button createItemButton = new Button(LanguageManager.getLocalizedText("Create Item"));
        createItemButton.addClickListener(clickEvent -> openCreateItemDialog());
        return createItemButton;
    }

    /**
     * Opens a dialog for creating new items.
     */
    private void openCreateItemDialog() {
        CreateItemDialog createItemDialog = new CreateItemDialog();
        createItemDialog.open();
    }

    /**
     * Filters the item grid based on the search query.
     *
     * @param filterText the search text to filter by.
     */
    private void filterItemGrid(String filterText) {
        if (dataProvider != null) {
            String lowerCaseFilter = filterText.toLowerCase();

            dataProvider.setFilter(item -> {
                boolean matchesName = item.getName() != null && item.getName().toLowerCase().contains(lowerCaseFilter);
                boolean matchesDescription = item.getDescription() != null && item.getDescription().toLowerCase().contains(lowerCaseFilter);

                return matchesName || matchesDescription;
            });
        }
    }
}