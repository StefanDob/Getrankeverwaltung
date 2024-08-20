package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    VerticalLayout createRightPart() {
        VerticalLayout verticalLayout = super.createRightPart();
        nameField.setValue(item.getName());
        descriptionField.setValue(item.getDescription());
        priceField.setValue(item.getPrice());
        stockField.setValue("" + item.getStock());

        Button deleteButton = new Button("Delete Item");
        deleteButton.setWidthFull();
        verticalLayout.add(deleteButton);

        deleteButton.addClickListener(event -> {
            // Create a confirmation dialog
            Dialog confirmationDialog = new Dialog();
            confirmationDialog.setCloseOnEsc(false);
            confirmationDialog.setCloseOnOutsideClick(false);

            // Add a message to the confirmation dialog
            Span message = new Span("Are you sure you want to delete this item?");
            confirmationDialog.add(message);

            // Create buttons for 'Confirm' and 'Cancel'
            Button confirmButton = new Button("Confirm", e -> {
                // Perform the delete operation if confirmed
                ItemOperations.deleteItem(item);
                confirmationDialog.close(); // Close the confirmation dialog
                close(); // Close the original dialog
                UI.getCurrent().getPage().reload(); // Reload the page
            });

            Button cancelButton = new Button("Cancel", e -> {
                confirmationDialog.close(); // Close the confirmation dialog
            });

            // Add buttons to the dialog
            HorizontalLayout buttons = new HorizontalLayout(confirmButton, cancelButton);
            confirmationDialog.add(buttons);
            buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // Center horizontally
            buttons.setWidthFull(); // Ensure the layout takes full width

            // Open the confirmation dialog
            confirmationDialog.open();
        });

        return verticalLayout;
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
        item.setStock(Integer.valueOf(stockField.getValue()));
        ItemOperations.saveItem(item);
        close();
        UI.getCurrent().getPage().reload();
    }

}
