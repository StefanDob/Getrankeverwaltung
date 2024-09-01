package de.tu.darmstadt;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.backendService.TransactionOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.Transaction;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCartItem;
import de.tu.darmstadt.frontend.account.LoginDialog;
import de.tu.darmstadt.frontend.account.SessionManagement;
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

    public static void main(String[] args) {

        System.out.println(
                randomString(
                        24,
                        new char[]{'a', 'z'},
                        new char[]{'0', '9'},
                        new char[]{'A', 'Z'},
                        new char[]{'-'}
                )
        );
    }


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
    //TODO check wether enough money is on the account
    public static void buyItem(Item item) {
        if (SessionManagement.getAccount() == null) {
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.open();
        }else if(SessionManagement.getAccount().getStatus() == AccountStatus.RESTRICTED){
            Notification.show("Your account is restricted. Contact an admin.", 3000, Notification.Position.MIDDLE);
        }else {
            long receiverId = Constants.getMasterID();

            Transaction transaction = new Transaction(SessionManagement.getAccount().getId(), receiverId, item.getPrice(), LocalDateTime.now(), item.getName());
            TransactionOperations.addTransaction(transaction);

            //Make sure acount is updated everywhere
            try {
                SessionManagement.setAccount(AccountOperations.getAccountByID(SessionManagement.getAccount().getId()));
            } catch (AccountPolicyException e) {
                throw new RuntimeException(e);
            }
            UI.getCurrent().getPage().reload();
            Notification.show("You bought a " + item.getName(), 3000, Notification.Position.MIDDLE);
        }
    }

    public static void buyShoppingCart(List<ShoppingCartItem> shoppingCartItems, double totalPrice) {
        if (SessionManagement.getAccount() == null) {
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.open();
        }else if(SessionManagement.getAccount().getStatus() == AccountStatus.RESTRICTED){
            Notification.show("Your account is restricted. Contact an admin.", 3000, Notification.Position.MIDDLE);
        }else {
            StringBuilder description = new StringBuilder();
            for(ShoppingCartItem shoppingCartItem : shoppingCartItems){
                description.append(shoppingCartItem.getItem().getName()).append(" * ").append(shoppingCartItem.getQuantity()).append("; ");
            }

            long receiverId = Constants.getMasterID();

            Transaction transaction = new Transaction(SessionManagement.getAccount().getId(), receiverId, totalPrice, LocalDateTime.now(), description.toString());
            TransactionOperations.addTransaction(transaction);

            //Make sure acount is updated everywhere
            try {
                SessionManagement.setAccount(AccountOperations.getAccountByID(SessionManagement.getAccount().getId()));
            } catch (AccountPolicyException e) {
                throw new RuntimeException(e);
            }
            UI.getCurrent().getPage().reload();
            Notification.show("You bought the Shopping Cart ", 3000, Notification.Position.MIDDLE);
        }
    }
}
