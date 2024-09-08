package de.tu.darmstadt.frontend.ItemManagment;

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
import de.tu.darmstadt.backend.backendService.ShoppingCartOperations;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.frontend.ItemManagment.ItemDialog;
import de.tu.darmstadt.frontend.account.LoginDialog;
import de.tu.darmstadt.Utils.SessionManagement;

public class ViewItemDialog extends ItemDialog {
    HorizontalLayout headerLayout;

    Component description;

    H1 title;

    H3 price;

    VerticalLayout rightPart;

    public ViewItemDialog(Item item){
        super(item);
    }
    @Override
    protected VerticalLayout createRightPart() {
        rightPart = new VerticalLayout();
        rightPart.setWidthFull();

        title = new H1(item.getName());
        title.addClassName("bordered-title");
        price = new H3(item.getItemPriceAsString());
        price.addClassName("bordered-field");
        description = showDescription();

        Button addToCartButton = new Button(LanguageManager.getLocalizedText("Add to Shopping Cart"));
        addToCartButton.setClassName("shopping-cart-button");
        addToCartButton.addClickListener(e -> {
            if(SessionManagement.getAccount() == null){
                LoginDialog loginDialog = new LoginDialog();
                loginDialog.open();
            }else{
                ShoppingCartOperations.addItemToCart(SessionManagement.getAccount().getId(), item.getId());
            }
        });

        Button buyNowButton = new Button(LanguageManager.getLocalizedText("Buy Now"));
        buyNowButton.addClickListener(e -> {
            ProjectUtils.buyItem(item);
        });
        buyNowButton.setClassName("shopping-cart-button");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(price, buyNowButton ,addToCartButton);
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);


        rightPart.add(title, description, horizontalLayout);

        return rightPart;
    }

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

    @Override
    protected Component createLeftPart() {
        Image itemImage = new Image(item.getImageAsResource(), item.getName());

        itemImage.setWidth("30vw");
        itemImage.setHeight("70vh");

        // Ensure the entire image fits within the container without cropping
        itemImage.getStyle().set("object-fit", "contain"); // 'contain' ensures the whole image is visible

        // Optional: Ensure the image is centered within the container
        itemImage.getStyle().set("display", "block");
        itemImage.getStyle().set("margin", "auto");

        return itemImage;
    }

    @Override
    protected HorizontalLayout createHeader() {
        // Close button in the dialog header
        Button closeButton = new Button(LanguageManager.getLocalizedText("Close"));
        closeButton.addClickListener(event -> close());
        closeButton.getStyle().set("margin-left", "auto"); // Move the button to the right

        // Creating a header layout
        headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        headerLayout.add(closeButton);
        return headerLayout;
    }

    public void setDescription(Component description){
        rightPart.remove(this.description);
        this.description = description;
        rightPart.add(this.description);
    }
}