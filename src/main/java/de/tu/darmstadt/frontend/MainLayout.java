package de.tu.darmstadt.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.Utils.ProjectUtils;
import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.frontend.account.AccountView.AccountView;
import de.tu.darmstadt.frontend.admin.AdminView;
import de.tu.darmstadt.frontend.account.LoginDialog;
import de.tu.darmstadt.frontend.store.StoreView;
import de.tu.darmstadt.frontend.ShoppingCart.ShoppingCartView;
import org.apache.commons.codec.language.bm.Lang;
import org.intellij.lang.annotations.Language;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.Locale;

/**
 * MainLayout is the primary layout class for the application. It sets up the main structure of the application
 * including the top navbar, side navigation drawer, and footer.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;
    private Button loginButton;

    /**
     * Constructs the MainLayout instance and initializes the application layout.
     */
    public MainLayout() {
        ProjectUtils.checkForCookies();
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    /**
     * Adds header content including the menu toggle, view title, login button, and language selection.
     */
    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        // Create a layout for the login button
        if (SessionManagement.getAccount() == null) {
            loginButton = new Button("Login");
            loginButton.addClickListener(e -> loginToAccount());
        } else {
            loginButton = new Button(SessionManagement.getAccount().getFirstName());
            loginButton.addClickListener(e -> UI.getCurrent().navigate(AccountView.class));
        }

        loginButton.getStyle().set("margin-right", "16px");

        // Create a ComboBox for language selection
        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.setItems("EN", "DE");
        languageComboBox.setValue(LanguageManager.getCurrentLanguage() == Locale.ENGLISH ? "EN" : "DE");

        languageComboBox.addValueChangeListener(event -> {
            String selectedLanguage = event.getValue();
            if (selectedLanguage.equals("DE")) {
                LanguageManager.setLanguage(Locale.GERMAN);
            } else {
                LanguageManager.setLanguage(Locale.ENGLISH);
            }
            UI.getCurrent().getPage().reload();
        });

        languageComboBox.setWidth("80px");
        languageComboBox.getElement().executeJs("this.inputElement.setAttribute('readonly', 'readonly')");

        // Layout for login button and language switch
        HorizontalLayout headerRightContent = new HorizontalLayout();
        headerRightContent.add(languageComboBox, loginButton);
        headerRightContent.setWidthFull();
        headerRightContent.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        // Add components to the navigation bar
        addToNavbar(true, toggle, viewTitle, headerRightContent);
    }

    /**
     * Opens the login dialog when the login button is clicked.
     */
    private void loginToAccount() {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.open();
    }

    /**
     * Adds drawer content including the application name, navigation items, and footer.
     */
    private void addDrawerContent() {
        H1 appName = new H1(LanguageManager.getLocalizedText("Shopping"));
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    /**
     * Creates the side navigation with links to different views.
     * Adds an "Admin" link if the current user has admin status.
     *
     * @return the SideNav component with navigation items.
     */
    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem(LanguageManager.getLocalizedText("Store"), StoreView.class, LineAwesomeIcon.STORE_SOLID.create()));
        nav.addItem(new SideNavItem(LanguageManager.getLocalizedText("Account"), AccountView.class, LineAwesomeIcon.USER_CIRCLE.create()));
        nav.addItem(new SideNavItem(LanguageManager.getLocalizedText("Shopping Cart"), ShoppingCartView.class, LineAwesomeIcon.CART_ARROW_DOWN_SOLID.create()));

        if (SessionManagement.getAccount() != null && SessionManagement.getAccount().getStatus() == AccountStatus.ADMIN) {
            nav.addItem(new SideNavItem(LanguageManager.getLocalizedText("Admin"), AdminView.class, LineAwesomeIcon.TOOLBOX_SOLID.create()));
        }

        return nav;
    }

    /**
     * Creates the footer for the layout.
     *
     * @return the Footer component.
     */
    private Footer createFooter() {
        return new Footer();
    }

    /**
     * Updates the view title after navigation based on the current page's title.
     */
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    /**
     * Retrieves the current page title from the PageTitle annotation.
     *
     * @return the localized page title.
     */
    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : LanguageManager.getLocalizedText(title.value());
    }
}