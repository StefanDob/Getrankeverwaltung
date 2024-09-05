package de.tu.darmstadt.frontend.account.AccountView;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.Utils.SessionManagement;

import java.util.ArrayList;
import java.util.List;

public class GeneralAccountInformation extends Details {
    Account currentAccount;

    private final Grid<Property> propertyGrid = new Grid<>(Property.class);

    public GeneralAccountInformation() {
        super(new H2(LanguageManager.getLocalizedText("General Information")));
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
        properties.add(new Property(LanguageManager.getLocalizedText("First Name"), currentAccount.getFirstName()));
        properties.add(new Property(LanguageManager.getLocalizedText("Last Name"), currentAccount.getLastName()));
        properties.add(new Property(LanguageManager.getLocalizedText("Email"), currentAccount.getEmail()));
        properties.add(new Property(LanguageManager.getLocalizedText("Password"), currentAccount.getPassword()));
        properties.add(new Property(LanguageManager.getLocalizedText("Birth Date"), currentAccount.getBirthDate().toString()));
        properties.add(new Property(LanguageManager.getLocalizedText("Phone Number"), currentAccount.getPhoneNumber()));
        properties.add(new Property(LanguageManager.getLocalizedText("Account Balance"), String.valueOf(currentAccount.getSaldo())));
        properties.add(new Property(LanguageManager.getLocalizedText("Debt Limit"), String.valueOf(currentAccount.getDebtLimit())));
        properties.add(new Property(LanguageManager.getLocalizedText("Status"), String.valueOf(currentAccount.getStatus())));

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
