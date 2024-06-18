package de.tu.darmstadt.frontend.store;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Title;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.dataModel.Item;

public class ItemDialog extends Dialog {
    Item item;

    HorizontalLayout headerLayout;

    Component description;

    H1 title;

    H3 price;

    HorizontalLayout contentLayout;

    VerticalLayout rightPart;

    public ItemDialog(Item item){
        this.item = item;

        setCloseOnOutsideClick(true);
        setWidth("90vw"); // Set dialog width to 90% of viewport width
        setHeight("90vh"); // Set dialog height to 90% of viewport height

        contentLayout = new HorizontalLayout(createImageAndTitle(),createRightPart());


        // Adding components to the dialog
        add(createHeader(), contentLayout);
    }

    private Component createRightPart() {
        rightPart = new VerticalLayout();

        title = new H1(item.getName());
        price = new H3("" + item.getPrice());
        description = showDescription();

        rightPart.add(title,price, description);

        return rightPart;
    }

    protected Component showDescription() {
        Html description = new Html("<div>" + item.getDescription() + "</div>");
        return description;
    }

    private Component createImageAndTitle() {
        Image itemImage = new Image(item.getImage(), item.getName());
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

    public void setDescription(Component description){
        rightPart.remove(this.description);
        this.description = description;
        contentLayout.add(this.description);
    }
}
