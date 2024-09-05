package de.tu.darmstadt.frontend.admin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.frontend.MainLayout;
import de.tu.darmstadt.frontend.store.CreateItemDialog;
import de.tu.darmstadt.frontend.store.ItemAdminDialog;

import java.util.List;

@PageTitle("Admin")
@Route(value = "admin", layout = MainLayout.class)
public class AdminView extends Details {

    public AdminView(){
        super(new H2(LanguageManager.getLocalizedText("Admin Options")));
        setOpened(true);
        setWidth("100%");
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");

        VerticalLayout wrapper = new VerticalLayout();
        wrapper.add(getAccountListView());
        wrapper.add(getItemListView());
        add(wrapper);

    }

    private Component getItemListView() {
        // Create a VerticalLayout to contain the title and grid
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.getStyle().set("overflow", "auto");

        // Create a title
        H3 title = new H3(LanguageManager.getLocalizedText("Items List"));

        // Add title to the VerticalLayout
        verticalLayout.add(title);

        // Create a Grid
        Grid<Item> grid = new Grid<>(Item.class, false);
        grid.addClassName("item-grid");

        // Add columns
        grid.addComponentColumn(item -> {
            Image image = new Image(item.getImageAsResource(), LanguageManager.getLocalizedText("Item image"));
            image.setWidth("100px");
            image.setHeight("100px");
            return image;
        }).setHeader(LanguageManager.getLocalizedText("Image"));

        grid.addColumn(Item::getName).setHeader(LanguageManager.getLocalizedText("Name"));
        grid.addColumn(Item::getDescription).setHeader(LanguageManager.getLocalizedText("Description"));
        grid.addColumn(Item::getItemPriceAsString).setHeader(LanguageManager.getLocalizedText("Price"));
        grid.addColumn(Item::getStock).setHeader(LanguageManager.getLocalizedText("Stock"));

        // Set items to the grid
        List<Item> items = ItemOperations.getAllShopItems();
        grid.setItems(items);

        // Add item click listener
        grid.addItemClickListener(event -> {
            Item clickedItem = event.getItem();
            ItemAdminDialog itemDialog = new ItemAdminDialog(clickedItem);
            itemDialog.open();
        });

        // Create and add the "Create Item" button
        Button createItemButton = new Button(LanguageManager.getLocalizedText("Create Item"));
        verticalLayout.add(createItemButton);

        // Add click listener for "Create Item" button (**implement your logic here**)
        createItemButton.addClickListener(clickEvent -> {
            CreateItemDialog createItemDialog = new CreateItemDialog();
            createItemDialog.open();

        });

        // Add the grid to the VerticalLayout
        verticalLayout.add(grid);
        verticalLayout.setHeight("900px");
        return verticalLayout;
    }

    private Component getAccountListView() {
        // Create a VerticalLayout to contain the title and grid
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.getStyle().set("overflow", "auto");

        // Create a title
        H3 title = new H3(LanguageManager.getLocalizedText("Accounts List"));

        // Add title to the VerticalLayout
        verticalLayout.add(title);

        // Create a Grid
        Grid<Account> grid = new Grid<>(Account.class, false);
        grid.addClassName("account-grid");

        // Add columns
        grid.addColumn(Account::getFirstName).setHeader(LanguageManager.getLocalizedText("First Name"));
        grid.addColumn(Account::getLastName).setHeader(LanguageManager.getLocalizedText("Last Name"));
        grid.addColumn(Account::getSaldo).setHeader(LanguageManager.getLocalizedText("Balance"));
        grid.addColumn(Account::getPhoneNumber).setHeader(LanguageManager.getLocalizedText("Phone Number"));
        grid.addColumn(Account::getDebtLimit).setHeader(LanguageManager.getLocalizedText("Debt Limit"));
        grid.addColumn(Account::getStatus).setHeader(LanguageManager.getLocalizedText("State"));

        // Set items to the grid
        List<Account> accounts = AccountOperations.getAllAccounts();
        if(accounts != null)
            grid.setItems(accounts);

        grid.addItemClickListener(event -> {
            Account clickedAccount = event.getItem();
            AdminAccountsDetailsView adminAccountsDetailsView = new AdminAccountsDetailsView(clickedAccount);
            adminAccountsDetailsView.open();
        });

        // Add the grid to the VerticalLayout
        verticalLayout.add(grid);
        verticalLayout.setHeight("400px");
        return verticalLayout;
    }
}
