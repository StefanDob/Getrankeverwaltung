package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import de.tu.darmstadt.frontend.MainLayout;

import java.awt.*;

@PageTitle("Store")
@Route(value = "store", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class StoreView extends VerticalLayout {

    int itemsPerLine = 4;

    public StoreView() {
        setSpacing(true); // Adjust spacing as needed

        // Item 1
        ItemView itemView1 = new ItemView("images/cola.jpg", "Item 1", "Large", "$10");
        add(itemView1);

        // Item 2
        ItemView itemView2 = new ItemView("images/fanta.jpg", "Item 2", "Medium", "$15");
        add(itemView2);

        // Item 3
        ItemView itemView3 = new ItemView("images/sprite.png", "Item 3", "Small", "$20");
        add(itemView3);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
