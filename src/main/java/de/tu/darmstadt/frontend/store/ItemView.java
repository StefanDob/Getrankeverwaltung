package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ItemView extends VerticalLayout {
    public ItemView(String imageUrl, String itemName, String price) {
        setSpacing(false);

        // Wrap image and header in a div for centering
        Div contentWrapper = new Div();
        contentWrapper.setWidth("100%");
        contentWrapper.getStyle().set("text-align", "center");
        add(contentWrapper);

        Image img = new Image(imageUrl, itemName);
        img.setWidth("200px"); // Adjust image size as needed
        contentWrapper.add(img);

        H2 header = new H2(itemName);
        header.addClassNames(LumoUtility.Margin.Top.XLARGE, LumoUtility.Margin.Bottom.MEDIUM);
        contentWrapper.add(header);

        Div priceDiv = new Div(new Paragraph("Price: " + price));
        priceDiv.setWidth("100%");
        priceDiv.getStyle().set("text-align", "center");
        add(priceDiv);

        // Optionally, add a button to add the item to the cart or perform other actions

        setWidth("250px"); // Adjust width as needed
        setPadding(true);
        setMargin(true);
    }
}
