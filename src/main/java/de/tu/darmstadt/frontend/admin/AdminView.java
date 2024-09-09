package de.tu.darmstadt.frontend.admin;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.frontend.MainLayout;
import com.vaadin.flow.component.html.H2;
import de.tu.darmstadt.frontend.admin.AdminLists.AccountListView;
import de.tu.darmstadt.frontend.admin.AdminLists.ItemListView;
import de.tu.darmstadt.frontend.admin.AdminLists.TransactionListView;


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

        if(SessionManagement.getAccount() != null && SessionManagement.getAccount().getStatus() == AccountStatus.ADMIN){
            // Create the wrapper layout for admin views
            VerticalLayout wrapper = createWrapperLayout();
            wrapper.add(new AccountListView(), new ItemListView(), new TransactionListView());

            // Add wrapper to the Details component
            add(wrapper);
        }else{
            displayNoAdminView();
        }

    }

    /**
     * this method displays a field if a user tries to access admin functions even tough he is no admin
     */
    private void displayNoAdminView() {
        setSizeFull();
        VerticalLayout verticalLayout = new VerticalLayout();

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        verticalLayout.add(img);

        H2 header = new H2("You are not an admin");
        header.addClassNames(LumoUtility.Margin.Top.XLARGE, LumoUtility.Margin.Bottom.MEDIUM);
        verticalLayout.add(header);

        //set Style of Layout
        verticalLayout.setSizeFull();
        verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");

        add(verticalLayout);

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
