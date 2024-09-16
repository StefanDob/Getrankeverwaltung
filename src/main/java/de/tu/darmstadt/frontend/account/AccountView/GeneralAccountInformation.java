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

/**
 * The GeneralAccountInformation class displays a collapsible section containing
 * general information about the user's account in a grid.
 */
public class GeneralAccountInformation extends Details {

    private final Account currentAccount;
    private final Grid<Property> propertyGrid = new Grid<>(Property.class);

    /**
     * Constructor for GeneralAccountInformation.
     * It initializes the account information and sets up the grid and layout.
     */
    public GeneralAccountInformation() {
        super(new H2(LanguageManager.getLocalizedText("General Information")));
        this.currentAccount = SessionManagement.getAccount();

        setupLayout();
        populateGrid();
        alignColumnsToLeft();
    }

    /**
     * Aligns all columns in the grid to the left.
     */
    private void alignColumnsToLeft() {
        for (Grid.Column<Property> column : propertyGrid.getColumns()) {
            column.setTextAlign(ColumnTextAlign.START);
        }
    }

    /**
     * Populates the grid with account properties such as name, email, and balance.
     */
    private void populateGrid() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(LanguageManager.getLocalizedText("First Name"), currentAccount.getFirstName()));
        properties.add(new Property(LanguageManager.getLocalizedText("Last Name"), currentAccount.getLastName()));
        properties.add(new Property(LanguageManager.getLocalizedText("Email"), currentAccount.getEmail()));
        properties.add(new Property(LanguageManager.getLocalizedText("Password"), currentAccount.getPassword()));
        properties.add(new Property(LanguageManager.getLocalizedText("Birth Date"), currentAccount.getBirthDate().toString()));
        properties.add(new Property(LanguageManager.getLocalizedText("Phone Number"), currentAccount.getPhoneNumber()));
        properties.add(new Property(
                LanguageManager.getLocalizedText("Account Balance"),
                String.format("%.2f €", currentAccount.getSaldo())
        ));

        properties.add(new Property(
                LanguageManager.getLocalizedText("Debt Limit"),
                String.format("%.2f €", currentAccount.getDebtLimit())
        ));
        properties.add(new Property(LanguageManager.getLocalizedText("Status"), String.valueOf(currentAccount.getStatus())));

        propertyGrid.setItems(properties);
    }

    /**
     * Sets up the layout and styles of the account information grid and surrounding container.
     */
    private void setupLayout() {
        setWidth("100%");
        getStyle().set("border", "1px solid #ccc");
        getStyle().set("border-radius", "6px");
        setOpened(true);


        add(propertyGrid);
    }

    /**
     * A class representing an account property with a name and value, used in the grid.
     */
    public static class Property {
        private final String name;
        private final String value;

        /**
         * Constructor for Property.
         *
         * @param name  the property name (e.g., "First Name")
         * @param value the property value (e.g., "John")
         */
        public Property(String name, String value) {
            this.name = name;
            this.value = value;
        }

        /**
         * getter for name
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * getter for value
         * @return value
         */
        public String getValue() {
            return value;
        }
    }
}
