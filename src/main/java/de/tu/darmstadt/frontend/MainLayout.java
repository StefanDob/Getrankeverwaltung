package de.tu.darmstadt.frontend;

import com.vaadin.flow.component.UI;
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
import de.tu.darmstadt.ProjectUtils;
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.frontend.account.AccountView.AccountView;
import de.tu.darmstadt.frontend.admin.AdminView;
import de.tu.darmstadt.frontend.account.SessionManagement;
import de.tu.darmstadt.frontend.account.LoginDialog;
import de.tu.darmstadt.frontend.store.StoreView;
import de.tu.darmstadt.frontend.ShoppingCart.ShoppingCartView;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private Button loginButton;

    public MainLayout() {
        ProjectUtils.checkForCookies();
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
        if(SessionManagement.getAccount() == null){
            loginButton = new Button("Login");
            loginButton.addClickListener(e -> {
                loginToAccount();
            });
        }else{
            loginButton = new Button(SessionManagement.getAccount().getFirstName());
            loginButton.addClickListener(e -> {
                UI.getCurrent().navigate(AccountView.class);
            });

        }


        loginButton.getStyle().set("margin-right", "16px"); // Adjust margin as needed

        HorizontalLayout headerRightContent = new HorizontalLayout();
        headerRightContent.add(loginButton);
        headerRightContent.setWidthFull();
        headerRightContent.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        addToNavbar(true, toggle, viewTitle, headerRightContent);
    }

    private void loginToAccount() {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.open();
    }

    private void addDrawerContent() {
        H1 appName = new H1("Shopping");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Store", StoreView.class, LineAwesomeIcon.STORE_SOLID.create()));
        nav.addItem(new SideNavItem("Account", AccountView.class, LineAwesomeIcon.USER_CIRCLE.create()));
        nav.addItem(new SideNavItem("Warenkorb", ShoppingCartView.class, LineAwesomeIcon.CART_ARROW_DOWN_SOLID.create()));
        if(SessionManagement.getAccount() != null && SessionManagement.getAccount().getStatus() == AccountStatus.ADMIN){
            nav.addItem(new SideNavItem("Admin", AdminView.class, LineAwesomeIcon.TOOLBOX_SOLID.create()));
        }


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
