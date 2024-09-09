package de.tu.darmstadt.frontend.ShoppingCart;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.component.notification.Notification;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.Utils.ProjectUtils;
import de.tu.darmstadt.backend.backendService.ShoppingCartOperations;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCartItem;
import de.tu.darmstadt.frontend.MainLayout;
import de.tu.darmstadt.Utils.SessionManagement;

import java.util.List;

/**
 * View class for displaying the shopping cart.
 * Shows the list of items in the cart with their details and provides options to update quantities and proceed to purchase.
 */
@PageTitle("Warenkorb")
@Route(value = "warenkorb", layout = MainLayout.class)
public class ShoppingCartView extends VerticalLayout {

    private final Div totalPriceLabel = new Div();

    /**
     * Constructor that initializes the view based on the user's session state.
     */
    public ShoppingCartView() {
        if (SessionManagement.getAccount() == null) {
            displayNoShoppingCart();
        } else {
            displayShoppingCart();
        }
    }

    /**
     * Displays the shopping cart view with items and actions if the user is logged in.
     */
    private void displayShoppingCart() {
        List<ShoppingCartItem> items = ShoppingCartOperations.getShoppingCartItems(SessionManagement.getAccount().getId());

        Grid<ShoppingCartItem> grid = createItemGrid(items);
        Button buyNowButton = createBuyNowButton();

        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("shopping-cart-layout");
        layout.add(grid, totalPriceLabel, buyNowButton);
        layout.setAlignItems(Alignment.CENTER);
        layout.setPadding(true);
        layout.setSpacing(true);

        add(layout);
        setTotalPriceLabel();
    }

    /**
     * Creates a grid for displaying shopping cart items.
     *
     * @param items the list of items to be displayed in the grid.
     * @return the configured Grid component.
     */
    private Grid<ShoppingCartItem> createItemGrid(List<ShoppingCartItem> items) {
        Grid<ShoppingCartItem> grid = new Grid<>(ShoppingCartItem.class, false);
        grid.addClassName("item-grid");

        grid.addComponentColumn(this::createItemImage).setHeader(LanguageManager.getLocalizedText("Image"));
        grid.addColumn(ShoppingCartItem::getName).setHeader(LanguageManager.getLocalizedText("Name"));
        grid.addColumn(ShoppingCartItem::getPriceAsString).setHeader(LanguageManager.getLocalizedText("Price"));
        grid.addColumn(ShoppingCartItem::getStockAsString).setHeader(LanguageManager.getLocalizedText("Stock"));
        grid.addComponentColumn(this::createQuantityField).setHeader(LanguageManager.getLocalizedText("Quantity"));

        grid.setItems(items);
        grid.setHeight(Math.min(165 * items.size(), 600) + "px");  // Prevent overly tall grids

        return grid;
    }

    /**
     * Creates an image component for a shopping cart item.
     *
     * @param item the shopping cart item.
     * @return the Image component displaying the item's image.
     */
    private Image createItemImage(ShoppingCartItem item) {
        Image image = new Image(item.getItem().getImageAsResource(), "Item image");
        image.setWidth("120px");
        image.setHeight("120px");
        image.addClassName("item-image");
        return image;
    }

    /**
     * Creates a NumberField component for editing the quantity of a shopping cart item.
     *
     * @param item the shopping cart item.
     * @return the NumberField component for adjusting the item's quantity.
     */
    private NumberField createQuantityField(ShoppingCartItem item) {
        NumberField quantityField = new NumberField();
        quantityField.setValue((double) item.getQuantity());
        quantityField.setMin(1);
        quantityField.setMax(item.getStock());
        quantityField.setStep(1);
        quantityField.setWidth("80px");
        if(item.getQuantity() > item.getItem().getStock()){
            quantityField.setInvalid(true);
        }else{
            quantityField.setInvalid(false);
        }
        quantityField.addValueChangeListener(event -> handleQuantityChange(item, event.getValue()));

        return quantityField;
    }

    /**
     * Handles changes to the quantity field.
     * Updates the item quantity or removes the item from the cart if the quantity is zero.
     *
     * @param shoppingCartItem the shopping cart item being updated.
     * @param newValue the new quantity value.
     */
    private void handleQuantityChange(ShoppingCartItem shoppingCartItem, Double newValue) {
        if(newValue < 0){
            UI.getCurrent().getPage().reload();
            Notification.show(LanguageManager.getLocalizedText("New quantity cannot be negative"), 3000, Notification.Position.MIDDLE);
        }else if (newValue == 0) {
            ShoppingCartOperations.delete(shoppingCartItem);
            UI.getCurrent().getPage().reload();
            Notification.show(LanguageManager.getLocalizedText("Item removed"), 2000, Notification.Position.MIDDLE);
        } else {
            shoppingCartItem.setQuantity(newValue.intValue());
            ShoppingCartOperations.save(shoppingCartItem);
            setTotalPriceLabel();
            Notification.show(LanguageManager.getLocalizedText("Quantity updated"), 2000, Notification.Position.MIDDLE);
        }
    }

    /**
     * Creates a "Buy Now" button that initiates the purchase process for the items in the cart.
     *
     * @return the configured Button component.
     */
    private Button createBuyNowButton() {
        Button buyNowButton = new Button(LanguageManager.getLocalizedText("Buy Now"));
        buyNowButton.addClassName("buy-now-button");
        buyNowButton.addClickListener(event -> {
            List<ShoppingCartItem> items = ShoppingCartOperations.getShoppingCartItems(SessionManagement.getAccount().getId());
            ProjectUtils.buyShoppingCart(items, getTotalPrice());
        });

        return buyNowButton;
    }

    /**
     * Updates the total price label with the current total price of the items in the cart.
     */
    private void setTotalPriceLabel() {
        totalPriceLabel.setText(String.format("Total: %.2f€", getTotalPrice()));
        totalPriceLabel.addClassName("total-price");
    }

    /**
     * Calculates the total price of the items in the shopping cart.
     *
     * @return the total price.
     */
    private double getTotalPrice() {
        List<ShoppingCartItem> items = ShoppingCartOperations.getShoppingCartItems(SessionManagement.getAccount().getId());
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    /**
     * Displays a message indicating that the user is not logged in and cannot access the shopping cart.
     */
    private void displayNoShoppingCart() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "Placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2(LanguageManager.getLocalizedText("You are not logged in yet"));
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph(LanguageManager.getLocalizedText("Please login if you want to use the Shopping Cart 🤗")));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}