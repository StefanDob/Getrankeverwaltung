package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.tu.darmstadt.dataModel.Item;

public class ItemView extends VerticalLayout {
    private Item item;

    public ItemView(Item item) {
        this.item = item;

        setSpacing(false);

        // Wrap image and header in a div for centering
        Div contentWrapper = new Div();
        contentWrapper.setWidth("100%");
        contentWrapper.getStyle().set("text-align", "center");
        add(contentWrapper);

        Image img = new Image(  item.getImage().getPath(), item.getName());
        img.setWidth("200px"); // Adjust image size as needed
        contentWrapper.add(img);

        H2 header = new H2(item.getName());
        header.addClassNames(LumoUtility.Margin.Top.XLARGE, LumoUtility.Margin.Bottom.MEDIUM);
        contentWrapper.add(header);

        Div priceDiv = new Div(new Paragraph("Price: " + item.getPrice()));
        priceDiv.setWidth("100%");
        priceDiv.getStyle().set("text-align", "center");
        add(priceDiv);

        // Optionally, add a button to add the item to the cart or perform other actions

        setWidth("250px"); // Adjust width as needed
        setPadding(true);
        setMargin(true);

        addClickListener(e -> showItemDetails());


    }

    /**
     * this method displays the item details once the item is clicked
     */
    private void showItemDetails() {
        // Creating a dialog to display item details
        Dialog itemDialog = new Dialog();
        itemDialog.setCloseOnOutsideClick(true);
        itemDialog.setWidth("90vw"); // Set dialog width to 90% of viewport width
        itemDialog.setHeight("90vh"); // Set dialog height to 90% of viewport height

        // Close button in the dialog header
        Button closeButton = new Button("Close");
        closeButton.addClickListener(event -> itemDialog.close());
        closeButton.getStyle().set("margin-left", "auto"); // Move the button to the right

        // Creating a header layout
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        headerLayout.add(closeButton);

        // Sample item details
        Div itemDetails = new Div();
        itemDetails.setText(item.getDescription());

        // Sample item image
        Image itemImage = new Image(item.getImage().getPath(), item.getName());
        itemImage.setWidth("40vw"); // Set image width
        itemImage.setHeight("40vw"); // Set image height

        // Create a layout for the dialog content
        HorizontalLayout contentLayout = new HorizontalLayout();

        // Add the image to the left side
        contentLayout.add(itemImage);

        // Add a spacer to create some space between the image and text
        contentLayout.add(new Div());

        // Add the item details to the right side
        contentLayout.add(itemDetails);

        // Adding components to the dialog
        itemDialog.add(headerLayout, contentLayout);

        // Opening the dialog
        itemDialog.open();
    }
}

