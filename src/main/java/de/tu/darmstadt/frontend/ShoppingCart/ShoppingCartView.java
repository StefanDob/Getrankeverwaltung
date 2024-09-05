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

@PageTitle("Warenkorb")
@Route(value = "warenkorb", layout = MainLayout.class)
public class ShoppingCartView extends VerticalLayout {

    Div totalPriceLabel = new Div();

    public ShoppingCartView() {
        if(SessionManagement.getAccount() == null){
            displayNoShoppingCart();
        }else{
            displayShoppingCart();
        }
    }

    private void displayShoppingCart() {
        List<ShoppingCartItem> items = ShoppingCartOperations.getShoppingCartItems(SessionManagement.getAccount().getId());


        Grid<ShoppingCartItem> grid = new Grid<>(ShoppingCartItem.class, false);
        grid.addClassName("item-grid");

        // Add columns
        grid.addComponentColumn(shoppingCartItem -> {
            Image image = new Image(shoppingCartItem.getItem().getImageAsResource(), "Item image");
            image.setWidth("120px");
            image.setHeight("120px");
            image.addClassName("item-image");
            return image;
        }).setHeader(LanguageManager.getLocalizedText("Image"));

        grid.addColumn(ShoppingCartItem::getName).setHeader(LanguageManager.getLocalizedText("Name"));
        grid.addColumn(ShoppingCartItem::getPriceAsString)
                .setHeader(LanguageManager.getLocalizedText("Price"));
        grid.addColumn(ShoppingCartItem::getStockAsString).setHeader(LanguageManager.getLocalizedText("Stock"));

        // Add editable quantity column
        grid.addComponentColumn(shoppingCartItem -> {
            NumberField quantityField = new NumberField();
            quantityField.setValue((double) shoppingCartItem.getQuantity());
            quantityField.setMin(1);
            quantityField.setMax(shoppingCartItem.getStock()); // Adjust as needed
            quantityField.setStep(1);
            quantityField.setWidth("80px");
            quantityField.addValueChangeListener(event -> {
                if(event.getValue() == 0){
                    ShoppingCartOperations.delete(shoppingCartItem);
                    UI.getCurrent().getPage().reload();
                    Notification.show(LanguageManager.getLocalizedText("Item removed"), 2000, Notification.Position.MIDDLE);
                }else{
                    shoppingCartItem.setQuantity(event.getValue().intValue());
                    ShoppingCartOperations.save(shoppingCartItem);
                    setTotalPriceLabel();
                    Notification.show(LanguageManager.getLocalizedText("Quantity updated"), 2000, Notification.Position.MIDDLE);
                }

            });
            return quantityField;
        }).setHeader(LanguageManager.getLocalizedText("Quantity"));

        // Set items to the grid
        grid.setItems(items);
        grid.setHeight(165 * items.size() + "px");

        setTotalPriceLabel();

        // Add "Buy Now" button
        Button buyNowButton = new Button(LanguageManager.getLocalizedText("Buy Now"));
        buyNowButton.addClassName("buy-now-button");
        buyNowButton.addClickListener(event -> {
            ProjectUtils.buyShoppingCart(ShoppingCartOperations.getShoppingCartItems(SessionManagement.getAccount().getId()), getTotalPrice());
            ShoppingCartOperations.deleteAllShoppingCartItems();
            Notification.show(LanguageManager.getLocalizedText("Buying completed..."), 3000, Notification.Position.MIDDLE);
        });


        // Add components to the layout
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("shopping-cart-layout");
        layout.add(grid, totalPriceLabel, buyNowButton);
        layout.setAlignItems(Alignment.CENTER);
        layout.setPadding(true);
        layout.setSpacing(true);

        add(layout);
    }

    private void setTotalPriceLabel() {
        totalPriceLabel.setText(String.format("Total: %.2f€", getTotalPrice()));
        totalPriceLabel.addClassName("total-price");
    }

    private double getTotalPrice() {
        List<ShoppingCartItem> items = ShoppingCartOperations.getShoppingCartItems(SessionManagement.getAccount().getId());

        // Calculate the total price
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }


    private void displayNoShoppingCart() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
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