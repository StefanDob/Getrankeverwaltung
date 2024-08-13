package de.tu.darmstadt.frontend.store;

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
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.Utils.ItemUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateItemDialog extends ItemDialog {

    NumberField priceField;
    TextField nameField;
    TextArea imageLinkField;
    TextArea descriptionField;
    Image imageDisplay;

    byte[] cachedImage;

    public CreateItemDialog(){
        super();
    }

    public CreateItemDialog(Item item) {
        super(item);
    }

    @Override
    Component createLeftPart() {
        // Create the layout for the left part of the view
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setWidth("30vw");

        // Create an Image component to display the image
        imageDisplay = new Image();
        imageDisplay.getStyle().set("width", "30vw")
                .set("height", "50vh")
                .set("object-fit", "contain");

        // Create a TextField for inputting the image link
        imageLinkField = new TextArea("Image URL");
        imageLinkField.setWidth("30vw");
        imageLinkField.setHeight("8vh");
        imageLinkField.getStyle().set("font-size", "12px");

        // Create a button to confirm the image URL
        Button confirmButton = new Button("Confirm");
        confirmButton.addClickListener(event -> {
            String imageUrl = imageLinkField.getValue();
            imageDisplay.setSrc(imageUrl);
            try {
                cachedImage = ItemUtils.downloadImageAsByteArray(imageUrl);
            } catch (IOException e) {
                Notification.show("Failed to load image from URL: " + e.getMessage());
            }
        });

        // Create a MemoryBuffer to hold the uploaded file
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setWidth("30vw");

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream();

            try {
                cachedImage = inputStream.readAllBytes();
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Display the uploaded image
            StreamResource resource = new StreamResource(fileName, () -> new ByteArrayInputStream(cachedImage));
            imageDisplay.setSrc(resource);
        });

        upload.addFailedListener(event ->
                Notification.show("Image upload failed: " + event.getReason().getMessage())
        );

        // Add the components to the layout
        leftLayout.add(imageDisplay, imageLinkField, confirmButton, upload);

        return leftLayout;
    }

    @Override
    Component createRightPart() {
        priceField = new NumberField("Price");
        priceField.setWidth("40vw");

        nameField = new TextField("Name");
        nameField.setWidth("40vw");
        nameField.getStyle().set("font-size", "30px");

        descriptionField = new TextArea("Description");
        descriptionField.setWidth("40vw");
        descriptionField.setHeight("30vh");

        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.setWidth("45vw");

        verticalLayout1.add(nameField);
        verticalLayout1.add(descriptionField);
        verticalLayout1.add(priceField);

        return verticalLayout1;
    }

    @Override
    Component createHeader() {
        // Save button in the dialog header
        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> save());
        saveButton.getStyle().set("margin-right", "0.5em");

        // Close button in the dialog header with a symbol
        Button closeButton = new Button(VaadinIcon.CLOSE.create());
        closeButton.addClickListener(event -> close());

        // Creating a header layout
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        headerLayout.add(saveButton, closeButton);
        return headerLayout;
    }

    protected void save() {
        ItemOperations.saveItem(new Item(nameField.getValue(),priceField.getValue(), cachedImage, descriptionField.getValue()));
        close();
        UI.getCurrent().getPage().reload();
    }




}
