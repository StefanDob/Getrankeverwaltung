package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ItemView extends VerticalLayout {
    public ItemView(String imageUrl, String itemName, String size, String price) {
        setSpacing(false);

        Image img = new Image(imageUrl, itemName);
        img.setWidth("200px"); // Adjust image size as needed
        add(img);

        H2 header = new H2(itemName);
        header.addClassNames(LumoUtility.Margin.Top.XLARGE, LumoUtility.Margin.Bottom.MEDIUM);
        add(header);

        add(new Paragraph("Size: " + size));
        add(new Paragraph("Price: " + price));

        // Optionally, add a button to add the item to the cart or perform other actions

        setWidth("250px"); // Adjust width as needed
        setPadding(true);
        setMargin(true);
    }
}
