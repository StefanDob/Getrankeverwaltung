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

public abstract class ItemDialog extends Dialog {
    Item item;

    VerticalLayout overallLayout;

    HorizontalLayout contentLayout;

    public ItemDialog(){
        createLayout();
    }

    public ItemDialog(Item item) {
        this.item = item;
        createLayout();
    }

    private void createLayout() {
        setCloseOnOutsideClick(true);
        setWidth("90vw"); // Set dialog width to 90% of viewport width
        setHeight("90vh"); // Set dialog height to 90% of viewport height

        overallLayout = new VerticalLayout();

        contentLayout = new HorizontalLayout(createLeftPart(),createRightPart());

        // Adding components to the dialog
        overallLayout.add(createHeader(), contentLayout);

        add(overallLayout);
    }

    abstract Component createLeftPart();

    abstract Component createRightPart();

    abstract Component createHeader();


}
