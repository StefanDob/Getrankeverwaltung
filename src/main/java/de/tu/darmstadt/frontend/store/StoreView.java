package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
        setSpacing(false); // Adjust spacing as needed
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        add(horizontalLayout);

        ItemView colaItemView = new ItemView("images/cola.jpg", "Coca Cola",  "$10");
        horizontalLayout.add(colaItemView);

        ItemView fantaItemView = new ItemView("images/fanta.jpg", "Fanta", "$15");
        horizontalLayout.add(fantaItemView);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
