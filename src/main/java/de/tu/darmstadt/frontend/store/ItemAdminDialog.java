package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import de.tu.darmstadt.dataModel.Item;


public class ItemAdminDialog extends CreateItemDialog{

    public ItemAdminDialog(Item item) {
        super(item);
    }



    @Override
    Component createLeftPart() {
        Component comp = super.createLeftPart();
        return comp;
    }

    @Override
    Component createRightPart() {
        Component comp = super.createRightPart();
        nameField.setValue(item.getName());
        descriptionField.setValue(item.getDescription());
        priceField.setValue(item.getPrice());
        return comp;
    }

}
