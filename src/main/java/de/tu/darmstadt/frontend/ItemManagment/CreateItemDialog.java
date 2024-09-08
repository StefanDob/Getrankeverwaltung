package de.tu.darmstadt.frontend.ItemManagment;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamResource;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.Utils.ItemUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The CreateItemDialog class is responsible for creating a dialog that allows
 * users to input and save details about an item. It extends the ItemDialog
 * class and provides a form with fields for name, price, stock, description,
 * and image (from URL or upload).
 */
public class CreateItemDialog extends ItemDialog {

    // Form fields
    NumberField priceField;
    TextField nameField;
    TextArea imageLinkField;
    TextArea descriptionField;
    TextField stockField;

    // UI components for displaying the image
    Image imageDisplay;

    // Cached image byte array for uploaded or URL images
    byte[] cachedImage;

    /**
     * Default constructor that initializes the dialog without any pre-filled data.
     */
    public CreateItemDialog() {
        super();
    }

    /**
     * Constructor that initializes the dialog with the given item’s data.
     *
     * @param item The item whose data will be pre-filled in the form.
     */
    public CreateItemDialog(Item item) {
        super(item);
    }

    /**
     * Creates the left part of the dialog containing the image display,
     * an image link field, and an upload component.
     *
     * @return A layout containing the components for image input and display.
     */
    @Override
    protected Component createLeftPart() {
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setWidth("40vw");  // Set width for the layout

        // Create an Image component to display the selected or uploaded image
        imageDisplay = new Image();
        imageDisplay.getStyle().set("width", "40vw")
                .set("height", "50vh")
                .set("object-fit", "contain");

        // Create a text area for inputting the image URL
        imageLinkField = new TextArea(LanguageManager.getLocalizedText("Image URL"));
        imageLinkField.setWidth("40vw");
        imageLinkField.setHeight("8vh");
        imageLinkField.getStyle().set("font-size", "12px");

        imageLinkField.setValueChangeMode(ValueChangeMode.EAGER);  // Trigger on each keystroke

        // Add a listener to update the image display and cache the image when the URL changes
        imageLinkField.addValueChangeListener(event -> {
            String imageUrl = imageLinkField.getValue();
            imageDisplay.setSrc(imageUrl);  // Display the image from the URL
            try {
                cachedImage = ItemUtils.downloadImageAsByteArray(imageUrl);  // Cache the image as a byte array
            } catch (IOException e) {
                Notification.show(LanguageManager.getLocalizedText("Failed to load image from URL: ") + e.getMessage());
            }
        });

        // Set up the upload component for image file uploads
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setWidth("40vw");

        // Set custom texts for the upload buttons and status
        UploadI18N i18n = new UploadI18N();
        i18n.setAddFiles(new UploadI18N.AddFiles().setOne(LanguageManager.getLocalizedText("Select file...")));
        i18n.setDropFiles(new UploadI18N.DropFiles().setOne(LanguageManager.getLocalizedText("Drop file here...")));
        i18n.setUploading(new UploadI18N.Uploading()
                .setStatus(new UploadI18N.Uploading.Status()
                        .setProcessing(LanguageManager.getLocalizedText("Uploading file..."))));

        upload.setI18n(i18n);

        // Handle the file upload success
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream();

            try {
                cachedImage = inputStream.readAllBytes();  // Cache the uploaded image
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Display the uploaded image
            StreamResource resource = new StreamResource(fileName, () -> new ByteArrayInputStream(cachedImage));
            imageDisplay.setSrc(resource);
        });

        // Handle failed uploads
        upload.addFailedListener(event ->
                Notification.show(LanguageManager.getLocalizedText("Image upload failed: ") + event.getReason().getMessage())
        );

        // Add components to the layout
        leftLayout.add(imageDisplay, imageLinkField, upload);

        return leftLayout;
    }

    /**
     * Creates the right part of the dialog with fields for item name, description,
     * price, and stock quantity.
     *
     * @return A vertical layout containing the item details input fields.
     */
    @Override
    protected VerticalLayout createRightPart() {
        // Initialize the form fields
        priceField = new NumberField(LanguageManager.getLocalizedText("Price"));
        priceField.setWidthFull();

        stockField = new TextField(LanguageManager.getLocalizedText("Stock"));
        stockField.setWidthFull();

        nameField = new TextField(LanguageManager.getLocalizedText("Name"));
        nameField.setWidthFull();
        nameField.getStyle().set("font-size", "30px");

        descriptionField = new TextArea(LanguageManager.getLocalizedText("Description"));
        descriptionField.setWidthFull();
        descriptionField.setHeight("30vh");

        // Create and add the fields to a layout
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setWidthFull();
        formLayout.add(nameField, descriptionField, priceField, stockField);

        return formLayout;
    }

    /**
     * Creates the header part of the dialog, containing a Save button and a Close button.
     *
     * @return A layout with the Save and Close buttons.
     */
    @Override
    protected Component createHeader() {
        // Create the Save button
        Button saveButton = new Button(LanguageManager.getLocalizedText("Save"));
        saveButton.addClickListener(event -> save());
        saveButton.getStyle().set("margin-right", "0.5em");

        // Create the Close button
        Button closeButton = new Button(VaadinIcon.CLOSE.create());
        closeButton.addClickListener(event -> close());

        // Create and configure the header layout
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        headerLayout.add(saveButton, closeButton);

        return headerLayout;
    }

    /**
     * Saves the current item by gathering data from the form fields and sending
     * it to the ItemOperations class for persistence.
     */
    protected void save() {
        // Create a new item with the form data and save it
        Item newItem = new Item(
                nameField.getValue(),
                priceField.getValue(),
                Integer.valueOf(stockField.getValue()),
                cachedImage,
                descriptionField.getValue()
        );

        ItemOperations.saveItem(newItem);
        close();
        UI.getCurrent().getPage().reload();  // Reload the page to reflect changes
    }
}

