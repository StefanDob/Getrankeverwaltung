package de.tu.darmstadt.frontend.account.AccountView;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.frontend.account.SessionManagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GeneralAccountInformation extends Details {
    Account currentAccount;

    private final Grid<Property> propertyGrid = new Grid<>(Property.class);

    public GeneralAccountInformation() {
        super(new H2("General Information"));
        this.currentAccount = SessionManagement.getAccount();

        // Populate the grid with account information
        populateGrid();

        // Align all auto-generated columns to the left
        alignColumnsToLeft();

        // Set up the layout
        setWidth("100%");
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");
        setOpened(true);
        propertyGrid.setHeight("55vh");
        add(propertyGrid);
    }

    private void alignColumnsToLeft() {
        // Iterate over all columns and set their text alignment to start (left)
        for (Grid.Column<Property> column : propertyGrid.getColumns()) {
            column.setTextAlign(ColumnTextAlign.START);
        }
    }


    private void populateGrid() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("First Name", currentAccount.getFirstName()));
        properties.add(new Property("Last Name", currentAccount.getLastName()));
        properties.add(new Property("Email", currentAccount.getEmail()));
        properties.add(new Property("Password", currentAccount.getPassword()));
        properties.add(new Property("Birth Date", currentAccount.getBirthDate().toString()));
        properties.add(new Property("Phone Number", currentAccount.getPhoneNumber()));
        properties.add(new Property("Account Balance", String.valueOf(currentAccount.getSaldo())));
        properties.add(new Property("Debt Limit", String.valueOf(currentAccount.getDebtLimit())));
        properties.add(new Property("Status", String.valueOf(currentAccount.getStatus())));

        propertyGrid.setItems(properties);
    }

    // Inner class to represent property name and value
    public static class Property {
        private final String name;
        private final String value;

        public Property(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
}
