package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.backend.exceptions.items.NegativePriceException;
import de.tu.darmstadt.dataModel.Item;

import javax.management.Notification;


public class ItemAdminDialog extends CreateItemDialog{

    public ItemAdminDialog(Item item) {
        super(item);
    }



    @Override
    Component createLeftPart() {
        Component comp = super.createLeftPart();
        imageDisplay.setSrc(item.getImageAsResource());
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
    @Override
    protected void save() {
        item.setName(nameField.getValue());
        if(cachedImage != null){
            item.setImage(cachedImage);
        }
        try {
            item.setPrice(priceField.getValue());
        } catch (NegativePriceException e) {
            throw new RuntimeException(e);
        }
        item.setDescription(descriptionField.getValue());
        ItemOperations.saveItem(item);
        close();
        UI.getCurrent().getPage().reload();
    }

}
