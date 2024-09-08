package de.tu.darmstadt.frontend.admin;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.frontend.MainLayout;
import com.vaadin.flow.component.html.H2;


/**
 * AdminView is the main admin page for managing accounts and items.
 * It contains a set of admin options including account and item management.
 */
@PageTitle("Admin")
@Route(value = "admin", layout = MainLayout.class)
public class AdminView extends Details {

    /**
     * Constructs the AdminView page with a layout containing account and item lists.
     */
    public AdminView() {
        // Set the title and configure the Details component
        super(createTitle());
        setOpened(true);
        configureDetailsStyle();

        // Create the wrapper layout for admin views
        VerticalLayout wrapper = createWrapperLayout();
        wrapper.add(new AccountListView(), new ItemListView());

        // Add wrapper to the Details component
        add(wrapper);
    }

    /**
     * Creates the title of the admin options section.
     *
     * @return H2 title with localized text for "Admin Options".
     */
    private static H2 createTitle() {
        return new H2(LanguageManager.getLocalizedText("Admin Options"));
    }

    /**
     * Configures the style of the Details component.
     */
    private void configureDetailsStyle() {
        setWidth("100%");
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");
    }

    /**
     * Creates a wrapper layout that will hold the admin components.
     *
     * @return A new VerticalLayout to hold the admin views.
     */
    private VerticalLayout createWrapperLayout() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setPadding(true);
        wrapper.setSpacing(true);
        wrapper.setWidthFull();
        return wrapper;
    }
}
