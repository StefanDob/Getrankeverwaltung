package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.tu.darmstadt.dataModel.Item;
import org.jetbrains.annotations.NotNull;

public class ItemView extends VerticalLayout {

    private final Item item;

    public ItemView(@NotNull Item item) {
        this.item = item;
        setSpacing(false);
        setWidth("250px"); // Adjust width as needed

        // Enforce a fixed height for consistent sizing
        setHeight("350px");

        setPadding(true);
        setMargin(true);

        // Add a CSS class for styling
        addClassName("item-view");

        // Image container with fixed height
        Div imageWrapper = new Div();
        imageWrapper.setWidth("100%");
        imageWrapper.setHeight("150px"); // Set a fixed height for the image container
        imageWrapper.getStyle().set("text-align", "center");
        add(imageWrapper);

        // Responsive image
        Image img = new Image(item.getImageAsResource(), item.getName());
        img.setMaxHeight("100%"); // Maintain aspect ratio
        img.setMaxWidth("100%");
        imageWrapper.add(img);

        // Item details
        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setWidth("100%");
        // No need for explicit height as it will fill remaining space
        add(details);

        H2 header = new H2(item.getName());
        header.addClassNames(LumoUtility.Margin.Top.SMALL, LumoUtility.Margin.Bottom.MEDIUM);
        details.add(header);

        Div priceDiv = new Div(new Paragraph("Price: " + item.getPrice() + " € (Stock: " + item.getStock() + ")"));
        priceDiv.setWidth("100%");
        priceDiv.getStyle().set("text-align", "left");
        details.add(priceDiv);

        // Button layout
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setSpacing(true);
        details.add(buttonLayout);

        // Buy now button with icon and text
        Button buyNowButton = new Button("Buy", new Icon(VaadinIcon.CASH));
        buyNowButton.addThemeName("tertiary");
        buyNowButton.addClassName("styled-button");
        buttonLayout.add(buyNowButton);

        // Add to cart button with icon and text
        Button addToCartButton = new Button("Cart", new Icon(VaadinIcon.CART));
        addToCartButton.addThemeName("tertiary");
        addToCartButton.addClassName("styled-button");
        buttonLayout.add(addToCartButton);

        // Event handlers for buttons
        buyNowButton.addClickListener(event -> {
            // Implement buy now logic
        });

        addToCartButton.addClickListener(event -> {
            // Implement add to cart logic
        });

        addClickListener(e -> showItemDetails());
    }

    /**
     * this method displays the item details once the item is clicked
     */
    private void showItemDetails() {
        // Creating a dialog to display item details
        ViewItemDialog viewItemDialog = new ViewItemDialog(item);

        // Opening the dialog
        viewItemDialog.open();
    }
}



