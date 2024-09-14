package de.tu.darmstadt.frontend.FrontendUtils.ItemManagment;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.backend.exceptions.items.NegativePriceException;
import de.tu.darmstadt.dataModel.Item;


/**
 * The ItemAdminDialog class extends the CreateItemDialog class and provides
 * additional functionality for admin users to edit, delete, or update an existing item.
 */
public class ItemAdminDialog extends CreateItemDialog {

    /**
     * Constructor that initializes the dialog with the given item’s data for editing.
     *
     * @param item The item to be edited in the dialog.
     */
    public ItemAdminDialog(Item item) {
        super(item);
    }

    /**
     * Creates the left part of the dialog containing the image display, image link field, and upload component.
     * It overrides the method from CreateItemDialog to display the image from the item being edited.
     *
     * @return A component containing the left part of the dialog.
     */
    @Override
    protected Component createLeftPart() {
        // Use the left part layout from the parent class
        Component leftPart = super.createLeftPart();

        // Set the image display to show the existing image of the item
        imageDisplay.setSrc(item.getImageAsResource());

        return leftPart;
    }

    /**
     * Creates the right part of the dialog containing the item details fields (name, description, price, stock)
     * and a delete button. It pre-fills the fields with the existing item data.
     *
     * @return A VerticalLayout containing the right part of the dialog.
     */
    @Override
    protected VerticalLayout createRightPart() {
        // Use the right part layout from the parent class
        VerticalLayout rightPartLayout = super.createRightPart();

        // Pre-fill the form fields with the current item data
        nameField.setValue(item.getName());
        descriptionField.setValue(item.getDescription());
        priceField.setValue(item.getPrice());
        stockField.setValue(String.valueOf(item.getStock()));  // Convert stock to string

        // Create a delete button for removing the item
        Button deleteButton = new Button(LanguageManager.getLocalizedText("Delete Item"));
        deleteButton.setWidthFull();
        rightPartLayout.add(deleteButton);

        // Add a click listener to the delete button to trigger the delete confirmation dialog
        deleteButton.addClickListener(event -> openDeleteConfirmationDialog());

        return rightPartLayout;
    }

    /**
     * Opens a confirmation dialog to ask the user whether they really want to delete the item.
     * If confirmed, the item is deleted, and the dialog is closed.
     */
    private void openDeleteConfirmationDialog() {
        // Create a new dialog for confirmation
        Dialog confirmationDialog = new Dialog();
        confirmationDialog.setCloseOnEsc(false);  // Prevent closing with Esc key
        confirmationDialog.setCloseOnOutsideClick(false);  // Prevent closing by clicking outside

        // Add a confirmation message
        Span message = new Span(LanguageManager.getLocalizedText("Are you sure you want to delete this item?"));
        confirmationDialog.add(message);

        // Create 'Confirm' and 'Cancel' buttons
        Button confirmButton = new Button(LanguageManager.getLocalizedText("Confirm"), event -> {
            // Perform the delete operation
            ItemOperations.deleteItem(item);
            confirmationDialog.close();  // Close confirmation dialog
            close();  // Close the admin dialog
            UI.getCurrent().getPage().reload();  // Reload the page to reflect changes
        });

        Button cancelButton = new Button(LanguageManager.getLocalizedText("Cancel"), event -> {
            confirmationDialog.close();  // Close the confirmation dialog without deleting
        });

        // Create a layout to hold the buttons and center them
        HorizontalLayout buttonsLayout = new HorizontalLayout(confirmButton, cancelButton);
        buttonsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);  // Center buttons
        buttonsLayout.setWidthFull();

        // Add buttons layout to the dialog
        confirmationDialog.add(buttonsLayout);

        // Open the confirmation dialog
        confirmationDialog.open();
    }

    /**
     * Saves the changes made to the item, including updated name, description, price, stock, and image.
     * Overrides the save method from CreateItemDialog to update the existing item.
     */
    @Override
    protected void save() {
        if (cachedImage != null) {
            item.setImage(cachedImage);  // Update image if a new one is provided
        }else{
            cachedImage = item.getImage();
        }

        if(validateInput()){
        // Update the item's name, image (if changed), price, description, and stock
        item.setName(nameField.getValue());



        try {
            item.setPrice(priceField.getValue());
        } catch (NegativePriceException e) {
            throw new RuntimeException(LanguageManager.getLocalizedText("Price cannot be negative: ") + e.getMessage());
        }

        item.setDescription(descriptionField.getValue());
        item.setStock(Integer.valueOf(stockField.getValue()));  // Update stock quantity

        // Save the updated item
        ItemOperations.saveItem(item);

        close();  // Close the dialog
        UI.getCurrent().getPage().reload();  // Reload the page to reflect changes
        }
    }
}
