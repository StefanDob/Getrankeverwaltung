package de.tu.darmstadt.frontend.account.AccountView;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
import de.tu.darmstadt.frontend.MainLayout;
import de.tu.darmstadt.Utils.SessionManagement;


/**
 * AccountView displays account-related information if the user is logged in.
 * If the user is not logged in, a message prompting them to log in is shown.
 */
@PageTitle("Account")
@Route(value = "account", layout = MainLayout.class)
public class AccountView extends VerticalLayout {

    private final Account currentAccount;

    /**
     * Constructor for AccountView. It checks if the user is logged in and either
     * shows the account information or a login prompt.
     */
    public AccountView() {
        currentAccount = SessionManagement.getAccount();

        if (currentAccount == null) {
            showNotLoggedInMessage();
        } else {
            showAccountDetails();
        }
    }

    /**
     * Displays the account information components on the page.
     */
    private void showAccountDetails() {
        add(new GeneralAccountInformation());
        add(new TransactionInformation());
        configureLayoutForAccount();
    }

    /**
     * Configures the layout for when the account information is displayed.
     */
    private void configureLayoutForAccount() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        getStyle().set("text-align", "center");
    }

    /**
     * Displays a message to the user that they need to log in, along with a placeholder image.
     */
    private void showNotLoggedInMessage() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("Please login");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);

        configureLayoutForNotLoggedIn();
    }

    /**
     * Configures the layout for when the user is not logged in.
     */
    private void configureLayoutForNotLoggedIn() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}

