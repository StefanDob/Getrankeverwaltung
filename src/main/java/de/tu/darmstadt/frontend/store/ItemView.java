package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.Utils.ProjectUtils;
import de.tu.darmstadt.backend.backendService.ShoppingCartOperations;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.frontend.ItemManagment.ViewItemDialog;
import de.tu.darmstadt.frontend.account.LoginDialog;
import de.tu.darmstadt.Utils.SessionManagement;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The ItemView class represents a visual component for displaying an individual item.
 * It includes the item image, name, price, stock information, and provides buttons for
 * purchasing or adding the item to the shopping cart. The class also handles user interaction
 * through button clicks and item selection.
 */
public class ItemView extends VerticalLayout {

    // The item associated with this view
    private final Item item;

    /**
     * Constructor to initialize the item view with a specific item.
     *
     * @param item The item to be displayed in this view.
     */
    public ItemView(@NotNull Item item) {
        this.item = item;

        // Set layout properties
        setSpacing(false);
        setWidth("250px");  // Set a fixed width for the item view
        setHeight("350px"); // Set a fixed height for consistent display
        setPadding(true);
        setMargin(true);

        // Add a CSS class for custom styling
        addClassName("item-view");

        // Create and add image wrapper to display the item's image
        Div imageWrapper = new Div();
        imageWrapper.setWidth("100%");
        imageWrapper.setHeight("150px");  // Set a fixed height for the image container
        imageWrapper.getStyle().set("text-align", "center");  // Center the image
        add(imageWrapper);

        // Create and configure the image for the item
        Image img = new Image(item.getImageAsResource(), item.getName());
        img.setMaxHeight("100%");  // Maintain the aspect ratio
        img.setMaxWidth("100%");
        imageWrapper.add(img);

        // Create a layout for item details (name, price, and stock)
        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setWidth("100%");
        details.setPadding(false);
        details.setMargin(false);
        add(details);

        // Display the item name as a header
        H2 header = new H2(item.getName());
        header.addClassName("item-view-title");
        details.add(header);

        // Display the item price and stock information
        Div priceDiv = new Div(new Paragraph(
                LanguageManager.getLocalizedText("Price") + ": " +
                        item.getItemPriceAsString() + " (" +
                        LanguageManager.getLocalizedText("Stock") + ": " + item.getStock() + ")"
        ));
        priceDiv.setWidth("100%");
        priceDiv.getStyle().set("text-align", "left");  // Align price text to the left
        details.add(priceDiv);

        // Create a button layout for the action buttons
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();  // Ensure the button layout takes full width
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);  // Center buttons
        buttonLayout.setSpacing(true);

        // Create 'Buy Now' button with icon and custom theme
        Button buyNowButton = new Button(LanguageManager.getLocalizedText("Buy"), new Icon(VaadinIcon.CASH));
        buyNowButton.addThemeName("tertiary");
        buyNowButton.addClassName("styled-button");
        buttonLayout.add(buyNowButton);

        // Create 'Add to Cart' button with icon and custom theme
        Button addToCartButton = new Button(LanguageManager.getLocalizedText("Cart"), new Icon(VaadinIcon.CART));
        addToCartButton.addThemeName("tertiary");
        addToCartButton.addClassName("styled-button");
        buttonLayout.add(addToCartButton);

        // Add the button layout to the details section
        details.add(buttonLayout);

        // AtomicBoolean to track if a button was clicked
        AtomicBoolean buttonClicked = new AtomicBoolean(false);

        // Add click listener to the 'Buy Now' button
        buyNowButton.addClickListener(event -> {
            buttonClicked.set(true);  // Mark button click as true
            ProjectUtils.buyItem(item);  // Handle the item purchase
        });

        // Add click listener to the 'Add to Cart' button
        addToCartButton.addClickListener(event -> {
            buttonClicked.set(true);  // Mark button click as true
            if (SessionManagement.getAccount() == null) {
                // Show login dialog if the user is not logged in
                LoginDialog loginDialog = new LoginDialog();
                loginDialog.open();
            } else {
                // Add the item to the shopping cart
                ShoppingCartOperations.addItemToCart(SessionManagement.getAccount().getId(), item.getId());
                Notification.show(LanguageManager.getLocalizedText("Item added to cart"), 2000, Notification.Position.MIDDLE);
            }
        });

        // Add click listener to the entire item view to show item details on click
        addClickListener(e -> {
            if (buttonClicked.get()) {
                buttonClicked.set(false);  // Reset the button click flag
                return;  // Ignore clicks that occurred on buttons
            }
            showItemDetails();  // Show item details if the item itself is clicked
        });
    }

    /**
     * Displays a dialog showing the detailed view of the item when the item view is clicked.
     */
    private void showItemDetails() {
        // Create a dialog to display item details
        ViewItemDialog viewItemDialog = new ViewItemDialog(item);

        // Open the dialog
        viewItemDialog.open();
    }

    /**
     * Returns the item associated with this view.
     *
     * @return The item displayed in this view.
     */
    public Item getItem() {
        return item;
    }
}




