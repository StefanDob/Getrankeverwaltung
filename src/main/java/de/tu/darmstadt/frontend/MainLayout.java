package de.tu.darmstadt.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.tu.darmstadt.frontend.account.AccountView;
import de.tu.darmstadt.frontend.dashboard.DashboardView;
import de.tu.darmstadt.frontend.store.LoginDialog;
import de.tu.darmstadt.frontend.store.StoreView;
import de.tu.darmstadt.frontend.warenkorb.WarenkorbView;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private Button loginButton;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        // Create a layout for the login button
        Button loginButton = new Button("Login");
        loginButton.addClickListener(e -> {
            createAccount();
        });
        loginButton.getStyle().set("margin-right", "16px"); // Adjust margin as needed

        HorizontalLayout headerRightContent = new HorizontalLayout();
        headerRightContent.add(loginButton);
        headerRightContent.setWidthFull();
        headerRightContent.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        addToNavbar(true, toggle, viewTitle, headerRightContent);
    }

    private void createAccount() {
        LoginDialog accountDialog = new LoginDialog();
        accountDialog.open();
    }

    private void addDrawerContent() {
        H1 appName = new H1("My App");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Store", StoreView.class, LineAwesomeIcon.FILE.create()));
        nav.addItem(new SideNavItem("Dashboard", DashboardView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        nav.addItem(new SideNavItem("Account", AccountView.class, LineAwesomeIcon.FILE.create()));
        nav.addItem(new SideNavItem("Warenkorb", WarenkorbView.class, LineAwesomeIcon.FILE.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
