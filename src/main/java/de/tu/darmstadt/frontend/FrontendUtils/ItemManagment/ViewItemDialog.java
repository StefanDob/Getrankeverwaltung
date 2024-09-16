package de.tu.darmstadt.frontend.FrontendUtils.ItemManagment;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.Utils.ProjectUtils;
import de.tu.darmstadt.backend.backendOperations.ShoppingCartOperations;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.frontend.account.LoginDialog;
import de.tu.darmstadt.Utils.SessionManagement;

/**
 * A dialog component for viewing an item’s details.
 * Extends ItemDialog to provide a read-only view with options to buy or add the item to the shopping cart.
 */
public class ViewItemDialog extends ItemDialog {

    private HorizontalLayout headerLayout;
    private Component description;
    private H1 title;
    private H3 price;
    private VerticalLayout rightPart;

    /**
     * Constructs a new ViewItemDialog for the given item.
     *
     * @param item The item to be displayed in the dialog.
     */
    public ViewItemDialog(Item item) {
        super(item);
    }

    /**
     * Creates the right-side layout of the dialog, containing the item’s title, price, description,
     * and action buttons for adding to the shopping cart or buying immediately.
     *
     * @return A VerticalLayout containing the item details and action buttons.
     */
    @Override
    protected VerticalLayout createRightPart() {
        rightPart = new VerticalLayout();
        rightPart.setWidthFull();

        // Item title and price
        title = new H1(item.getName());
        title.addClassName("bordered-title");
        price = new H3(item.getItemPriceAsString());
        price.addClassName("bordered-field");

        // Item description
        description = showDescription();

        // "Add to Shopping Cart" button
        Button addToCartButton = new Button(LanguageManager.getLocalizedText("Add to Shopping Cart"));
        addToCartButton.setClassName("shopping-cart-button");
        addToCartButton.addClickListener(e -> {
            if (SessionManagement.getAccount() == null) {
                // Show login dialog if user is not logged in
                LoginDialog loginDialog = new LoginDialog();
                loginDialog.open();
            } else {
                // Add item to shopping cart
                ShoppingCartOperations.addItemToCart(SessionManagement.getAccount().getId(), item.getId());
            }
        });

        // "Buy Now" button
        Button buyNowButton = new Button(LanguageManager.getLocalizedText("Buy Now"));
        buyNowButton.addClickListener(e -> {
            ProjectUtils.buyItem(item); // Buy item directly
        });
        buyNowButton.setClassName("shopping-cart-button");

        // Layout for price and action buttons
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(price, buyNowButton, addToCartButton);
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // Add components to the right-side layout
        rightPart.add(title, description, horizontalLayout);

        return rightPart;
    }

    /**
     * Displays the item’s description in a formatted HTML component.
     *
     * @return A Component containing the item's description.
     */
    protected Component showDescription() {
        Html html = new Html("<div>" + item.getDescription() + "</div>");
        Div div = new Div();
        div.add(html);
        div.setWidthFull();
        div.setHeightFull();
        description = div;
        description.setClassName("bordered-field");

        return description;
    }

    /**
     * Creates the left-side layout of the dialog, displaying the item’s image.
     *
     * @return A Component containing the item's image.
     */
    @Override
    protected Component createLeftPart() {
        Image itemImage = new Image(item.getImageAsResource(), item.getName());

        itemImage.setWidth("30vw");
        itemImage.setHeight("70vh");

        // Ensure the entire image fits within the container without cropping
        itemImage.getStyle().set("object-fit", "contain");

        // Center the image within the container
        itemImage.getStyle().set("display", "block");
        itemImage.getStyle().set("margin", "auto");

        return itemImage;
    }

    /**
     * Creates the dialog header, including a close button.
     *
     * @return A HorizontalLayout containing the close button.
     */
    @Override
    protected HorizontalLayout createHeader() {
        // Close button in the dialog header
        Button closeButton = new Button(LanguageManager.getLocalizedText("Close"));
        closeButton.addClickListener(event -> close());
        closeButton.getStyle().set("margin-left", "auto"); // Align close button to the right

        // Create the header layout
        headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        headerLayout.add(closeButton);
        return headerLayout;
    }

    /**
     * Updates the item description displayed in the dialog.
     *
     * @param description The new description component.
     */
    public void setDescription(Component description) {
        rightPart.remove(this.description);
        this.description = description;
        rightPart.add(this.description);
    }
}
