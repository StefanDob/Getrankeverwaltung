package de.tu.darmstadt.frontend.store;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import de.tu.darmstadt.dataModel.Item;

public class ItemDialog extends Dialog {
    Item item;

    HorizontalLayout headerLayout;

    Component desciption;

    HorizontalLayout contentLayout;

    public ItemDialog(Item item){
        this.item = item;

        setCloseOnOutsideClick(true);
        setWidth("90vw"); // Set dialog width to 90% of viewport width
        setHeight("90vh"); // Set dialog height to 90% of viewport height

        contentLayout = new HorizontalLayout(createImageAndTitle(), createDescription());


        // Adding components to the dialog
        add(createHeader(), contentLayout);
    }

    private Component createDescription() {
        desciption = new Html("<div>" + item.getDescription() + "</div>");
        return desciption;
    }

    private Component createImageAndTitle() {
        Image itemImage = new Image(item.getImage().getPath(), item.getName());
        itemImage.setWidth("30vw"); // Set image width
        itemImage.setHeight("30vw"); // Set image height
        return itemImage;
    }

    protected HorizontalLayout createHeader() {
        // Close button in the dialog header
        Button closeButton = new Button("Close");
        closeButton.addClickListener(event -> close());
        closeButton.getStyle().set("margin-left", "auto"); // Move the button to the right

        // Creating a header layout
        headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        headerLayout.add(closeButton);
        return headerLayout;
    }
}
