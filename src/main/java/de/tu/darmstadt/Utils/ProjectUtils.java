package de.tu.darmstadt.Utils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.backendOperations.*;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.Transaction;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCartItem;
import de.tu.darmstadt.frontend.account.LoginDialog;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * The class {@link ProjectUtils} provides helpful functionalities for implementing the project.
 *
 * @author Toni Tan Phat Tran
 * @version 10.05.2024
 */
public final class ProjectUtils {


    /**
     * A {@link Random} object is used to create random primitive data.
     */
    public static final Random RANDOM = new Random();


    // :::::::::::::::::::::::::::::: CONSTRUCTORS ::::::::::::::::::::::::::::::::::::


    /**
     * The constructor of {@link ProjectUtils} is hidden with the qualifier {@code private} in order to prevent any
     * instantiation of any {@link ProjectUtils} instances
     */
    private ProjectUtils() {
        throw new RuntimeException("Utility class cannot be instantiated");
    }


    // :::::::::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::::::::


    /**
     * This static method is used to create a random {@link String} with a specified length. It has varargs of {@code
     * char} arrays, that are closed intervals of chars, to specify the characters used for the resulting {@link String}
     * according to the <a href="https://www.asciitable.com/">ASCII encoding standard</a>. Example:
     *
     * <p>
     *     Given is an array of {@code char} values {@code char[]... chars_interval = {{'a', 'z'}, {'A', 'Z'} } }.
     *     The resulting {@link String} value contains only characters from 'a' to 'z' (both inclusive) and characters
     *     from 'A' to 'Z' (both inclusive) according to the ASCII encoding standard.
     * </p>
     *
     * @param length the specified length of the resulting {@link String}.
     * @param chars_interval the closed intervals of {@code char}s used for the resulting {@link String}
     * @return a random {@link String} value.
     */
    @NotNull
    public static String randomString(int length, char[]... chars_interval) {

        // A StringBuilder is used to construct a String of chars with a loop.
        final StringBuilder sb = new StringBuilder(length);

        // builds the resulting String with a specified length by iterating 'length' times,
        // i.e., the String is appended with 'length' chars.
        for (int i = 0; i < length; i++) {

            // The randomly chosen char interval from the varargs
            char[] chars = chars_interval[ RANDOM.nextInt(chars_interval.length) ];

            char firstLetter = chars[0]; // the first letter of the interval 'chars'
            char LastLetter = chars[chars.length - 1]; // the last letter of the interval 'chars'

            // appends a random letter to the resulting String
            sb.append( (char) RANDOM.nextInt( firstLetter, LastLetter + 1 ) );

        } // end of for

        return sb.toString();
    }

    public static void buyItem(Item item) {
        if (SessionManagement.getAccount() == null) {
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.open();
        }else if(SessionManagement.getAccount().getStatus() == AccountStatus.RESTRICTED){
            Notification.show(LanguageManager.getLocalizedText("Your account is restricted. Contact an admin."), 3000, Notification.Position.MIDDLE);
        }else if(SessionManagement.getAccount().checkForCoverage(item.getPrice())){
            Notification.show(LanguageManager.getLocalizedText("Your account does not have enough coverage"), 3000, Notification.Position.MIDDLE);
        }else if(item.getStock() <= 0){
            Notification.show(LanguageManager.getLocalizedText("The product is currently not available"), 3000, Notification.Position.MIDDLE);
        }else {
            long receiverId = Constants.getMasterID();

            Transaction transaction = new Transaction(SessionManagement.getAccount().getId(), receiverId, item.getPrice(), LocalDateTime.now(), item.getName());
            TransactionOperations.addTransaction(transaction);

            //update the stock of the item
            item.setStock(item.getStock()-1);
            ItemOperations.saveItem(item);

            //Make sure acount is updated everywhere
            try {
                SessionManagement.setAccount(AccountOperations.getAccountByID(SessionManagement.getAccount().getId()));
            } catch (AccountPolicyException e) {
                throw new RuntimeException(e);
            }
            UI.getCurrent().getPage().reload();
            Notification.show(LanguageManager.getLocalizedText("You bought a ") + item.getName(), 3000, Notification.Position.MIDDLE);
        }
    }

    /**
     * Handles the process of purchasing items in the shopping cart.
     * <p>
     * This method performs the following actions:
     * <ul>
     *     <li>If the user is not logged in, opens the login dialog.</li>
     *     <li>If the user's account status is restricted, shows a notification indicating that the account is restricted.</li>
     *     <li>If the user is logged in and their account is not restricted, processes the transaction by creating a transaction record and updating the account information.</li>
     *     <li>Reloads the current page and displays a notification indicating that the shopping cart has been purchased.</li>
     * </ul>
     *
     * @param shoppingCartItems a list of {@link ShoppingCartItem} objects representing the items in the shopping cart
     * @param totalPrice the total price of the items in the shopping cart
     */
    public static void buyShoppingCart(List<ShoppingCartItem> shoppingCartItems, double totalPrice) {
        if (SessionManagement.getAccount() == null) {
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.open();
        } else if (SessionManagement.getAccount().getStatus() == AccountStatus.RESTRICTED) {
            Notification.show(LanguageManager.getLocalizedText("Your account is restricted. Contact an admin."), 3000, Notification.Position.MIDDLE);
        } else if(SessionManagement.getAccount().checkForCoverage(totalPrice)){
            Notification.show(LanguageManager.getLocalizedText("Your account does not have enough coverage"), 3000, Notification.Position.MIDDLE);
        }else if(checkShoppingCartItems(shoppingCartItems)){
            Notification.show(LanguageManager.getLocalizedText("One of the products exceeds the stock, please make it fit"), 3000, Notification.Position.MIDDLE);
        }
        else {
            StringBuilder description = new StringBuilder();
            for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
                //create description for transaction
                description.append(shoppingCartItem.getItem().getName())
                        .append(" x ")
                        .append(shoppingCartItem.getQuantity())
                        .append("; ");

                //update the item stock too
                Item item = shoppingCartItem.getItem();
                item.setStock(item.getStock()-shoppingCartItem.getQuantity());
                ItemOperations.saveItem(item);

            }

            long receiverId = Constants.getMasterID();

            Transaction transaction = new Transaction(SessionManagement.getAccount().getId(), receiverId, totalPrice, LocalDateTime.now(), description.toString());
            TransactionOperations.addTransaction(transaction);
            //shopping cart allows checking out event tough we buy more items than there are in the stock
            // Make sure account is updated everywhere
            try {
                SessionManagement.setAccount(AccountOperations.getAccountByID(SessionManagement.getAccount().getId()));
            } catch (AccountPolicyException e) {
                throw new RuntimeException(e);
            }
            ShoppingCartOperations.deleteAllShoppingCartItems();
            UI.getCurrent().getPage().reload();
            Notification.show(LanguageManager.getLocalizedText("You bought the Shopping Cart "), 3000, Notification.Position.MIDDLE);
        }
    }

    /**
     *
     * @param shoppingCartItems that need to be checked
     * @return false if shoppingcartItems are ok and true if there is a fault
     */
    private static boolean checkShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        for(ShoppingCartItem shoppingCartItem : shoppingCartItems){
            if(shoppingCartItem.getQuantity() > shoppingCartItem.getItem().getStock()){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for the presence of cookies and sets the account information if available.
     * <p>
     * This method retrieves the account information from cookies (if present) and updates the session with the retrieved account.
     */
    public static void checkForCookies() {
        Account account = CookieOperations.getAccount();
        if (account != null) {
            SessionManagement.setAccount(account);
        }
    }

    /**
     * method that checks wether a string is parsable to int
     * @param value string to be parsed
     * @return false if string is parsable, true if string parsing throws error
     */
    public static boolean checkStringToInt(String value) {
        try{
            Integer.parseInt(value);
        }catch(NumberFormatException ex){
            return true;
        }
        return false;
    }
}
