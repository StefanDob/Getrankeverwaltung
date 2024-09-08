package de.tu.darmstadt.frontend.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.frontend.store.CreateItemDialog;
import de.tu.darmstadt.frontend.store.ItemAdminDialog;

import java.util.List;

/**
 * ItemListView displays a list of items in a grid format.
 * It includes functionality to view item details and create new items.
 */
public class ItemListView extends VerticalLayout {

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
        Grid<Item> grid = new Grid<>(Item.class, false);
        grid.addClassName("item-grid");

        // Add columns to the grid
        grid.addComponentColumn(item -> createItemImage(item))
                .setHeader(LanguageManager.getLocalizedText("Image"));
        grid.addColumn(Item::getName)
                .setHeader(LanguageManager.getLocalizedText("Name"));
        grid.addColumn(Item::getDescription)
                .setHeader(LanguageManager.getLocalizedText("Description"));
        grid.addColumn(Item::getItemPriceAsString)
                .setHeader(LanguageManager.getLocalizedText("Price"));
        grid.addColumn(Item::getStock)
                .setHeader(LanguageManager.getLocalizedText("Stock"));

        // Populate the grid with items
        List<Item> items = ItemOperations.getAllShopItems();
        grid.setItems(items);

        // Add item click listener
        grid.addItemClickListener(event -> openItemDialog(event.getItem()));

        return grid;
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
}