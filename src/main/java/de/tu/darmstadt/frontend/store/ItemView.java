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
import org.jetbrains.annotations.NotNull;

public class ItemView extends VerticalLayout {

    private final Item item;

    public ItemView(@NotNull Item item) {
        this.item = item;

        setSpacing(false);

        // Wrap image and header in a div for centering
        Div contentWrapper = new Div();
        contentWrapper.setWidth("100%");
        contentWrapper.getStyle().set("text-align", "center");
        add(contentWrapper);

        Image img = new Image(  item.getImage(), item.getName());
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
        ItemDialog itemDialog = new ItemDialog(item);


        // Opening the dialog
        itemDialog.open();
    }
}

