package de.tu.darmstadt.frontend.account;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.backend.exceptions.items.ItemPropertiesException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.AdminAccount;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.ItemImage;
import de.tu.darmstadt.frontend.MainLayout;
import de.tu.darmstadt.frontend.store.CreateItemDialog;
import de.tu.darmstadt.frontend.store.ItemAdminDialog;
import de.tu.darmstadt.frontend.store.ItemDialog;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
@PageTitle("Admin")
@Route(value = "admin", layout = MainLayout.class)
public class AdminView extends Details {

    public AdminView(){
        super(new H2("Admin Options"));
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
        H3 title = new H3("Items List");

        // Add title to the VerticalLayout
        verticalLayout.add(title);

        // Create a Grid
        Grid<Item> grid = new Grid<>(Item.class, false);
        grid.addClassName("item-grid");

        // Add columns
        grid.addComponentColumn(item -> {
            Image image = new Image(item.getImage(), "Item image");
            image.setWidth("100px");
            image.setHeight("100px");
            return image;
        }).setHeader("Image");

        grid.addColumn(Item::getName).setHeader("Name");
        grid.addColumn(Item::getDescription).setHeader("Description");
        grid.addColumn(Item::getPrice).setHeader("Price");

        // Set items to the grid
        ArrayList<Item> items = ItemOperations.getAllShopItems();
        grid.setItems(items);

        // Add item click listener
        grid.addItemClickListener(event -> {
            Item clickedItem = event.getItem();
            ItemAdminDialog itemDialog = new ItemAdminDialog(clickedItem);
            itemDialog.open();
        });

        // Create and add the "Create Item" button
        Button createItemButton = new Button("Create Item");
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
        H3 title = new H3("Accounts List");

        // Add title to the VerticalLayout
        verticalLayout.add(title);

        // Create a Grid
        Grid<Account> grid = new Grid<>(Account.class, false);
        grid.addClassName("account-grid");

        // Add columns
        grid.addColumn(Account::getFirst_name).setHeader("First Name");
        grid.addColumn(Account::getLast_name).setHeader("Last Name");
        grid.addColumn(Account::getBalance).setHeader("Balance");
        grid.addColumn(Account::getPhone_number).setHeader("Phone Number");
        grid.addColumn(Account::getDebt_limit).setHeader("Debt Limit");

        // Set items to the grid
        ArrayList<Account> accounts = AccountOperations.getAllAccounts();
        grid.setItems(accounts);

        // Add the grid to the VerticalLayout
        verticalLayout.add(grid);
        verticalLayout.setHeight("400px");
        return verticalLayout;
    }
}
