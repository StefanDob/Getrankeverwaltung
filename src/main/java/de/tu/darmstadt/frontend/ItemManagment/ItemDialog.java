package de.tu.darmstadt.frontend.ItemManagment;


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

/**
 * Abstract class ItemDialog represents a base dialog structure for handling item creation,
 * editing, or viewing. This class provides a layout with customizable left, right, and
 * header components, which are implemented by subclasses.
 */
public abstract class ItemDialog extends Dialog {

    // The item being managed in the dialog (could be null for new items)
    protected Item item;

    // Main vertical layout for the entire dialog
    protected VerticalLayout overallLayout;

    // Horizontal layout to hold the left and right parts of the dialog
    protected HorizontalLayout contentLayout;

    /**
     * Default constructor. Initializes the dialog without any item data,
     * typically used for creating a new item.
     */
    public ItemDialog() {
        createLayout();  // Initialize the layout structure
    }

    /**
     * Constructor that initializes the dialog with an existing item's data,
     * typically used for editing or viewing the item.
     *
     * @param item The item whose data will be managed in the dialog.
     */
    public ItemDialog(Item item) {
        this.item = item;
        createLayout();  // Initialize the layout structure with item data
    }

    /**
     * Initializes the layout of the dialog, setting up the overall structure
     * with header, left, and right parts. Subclasses will define the content
     * of these sections.
     */
    private void createLayout() {
        // Set dialog size and behavior
        setWidthFull();  // Set dialog to take the full width of the parent container
        setCloseOnOutsideClick(true);  // Allow closing dialog by clicking outside
        setWidth("90vw");  // Set dialog width to 90% of the viewport width
        setHeight("90vh");  // Set dialog height to 90% of the viewport height

        // Create the main vertical layout that holds all components
        overallLayout = new VerticalLayout();
        overallLayout.setWidthFull();  // Ensure layout takes full width

        // Create a horizontal layout for the main content (left and right parts)
        contentLayout = new HorizontalLayout(createLeftPart(), createRightPart());
        contentLayout.setWidthFull();  // Ensure content layout takes full width

        // Add header and content layouts to the overall layout
        overallLayout.add(createHeader(), contentLayout);

        // Add the overall layout to the dialog
        add(overallLayout);
    }

    /**
     * Abstract method to create the left part of the dialog. This method must be
     * implemented by subclasses to define the content of the left section.
     *
     * @return A Component representing the left part of the dialog.
     */
    protected abstract Component createLeftPart();

    /**
     * Abstract method to create the right part of the dialog. This method must be
     * implemented by subclasses to define the content of the right section.
     *
     * @return A Component representing the right part of the dialog.
     */
    protected abstract Component createRightPart();

    /**
     * Abstract method to create the header of the dialog. This method must be
     * implemented by subclasses to define the header content, such as buttons
     * or titles.
     *
     * @return A Component representing the header of the dialog.
     */
    protected abstract Component createHeader();
}
