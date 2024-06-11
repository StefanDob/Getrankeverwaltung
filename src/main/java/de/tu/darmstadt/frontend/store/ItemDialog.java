package de.tu.darmstadt.frontend.store;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import de.tu.darmstadt.dataModel.Item;

public class ItemDialog extends Dialog {
    public ItemDialog(Item item){
        setCloseOnOutsideClick(true);
        setWidth("90vw"); // Set dialog width to 90% of viewport width
        setHeight("90vh"); // Set dialog height to 90% of viewport height

        // Close button in the dialog header
        Button closeButton = new Button("Close");
        closeButton.addClickListener(event -> close());
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
        add(headerLayout, contentLayout);
    }
}
