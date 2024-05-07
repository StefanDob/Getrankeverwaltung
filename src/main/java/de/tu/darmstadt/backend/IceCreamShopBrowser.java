package de.tu.darmstadt.backend;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import static de.tu.darmstadt.backend.IceCreamShopProperties.*;

/**
 * An {@link IceCreamShopBrowser} grants the user a terminal for accessing to an {@link Account} or
 * to create a new {@link Account}.
 */
public class IceCreamShopBrowser {

    /**
     * The account that the user is currently logged in. This attribute stores the email which is the primary
     * key of {@link Account}.
     */
    private static String currentlyLoggedInAccount = null;

    /**
     * A {@link Scanner} is used for {@link System} input.
     */
    private static final Scanner SCANNER = new Scanner(System.in);


    // ::::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::

    /**
     * The main entry point of the program. It is also the main menu of the program.
     */
    public static void launch() {

        boolean valid_entry = true;


        int entry = 0;

        // If an invalid number is entered, the loop keeps repeating until a valid number is entered.
        do {

            try {
                System.out.println("Welcome to Ice Cream World!");
                System.out.println("Please enter [1] for login, [2] for creating a new account.");
                System.out.println("Please only enter numbers, otherwise an error occurs!");
                entry = SCANNER.nextInt();

                // If [1] is entered, the menu for login is activated.
                if (entry == 1) {
                    login();
                }

                // If [2] is entered, a menu for creating a new account is opened.
                if (entry == 2) {
                    create_new_account();
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter only numbers as input.");
            }


        } while (entry != 1 && entry != 2); // end of do-while

    }


    public static void login() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your email. Type \"exit\" to cancel login.");
        String email = scanner.nextLine(); // the entered email

        if(email.toLowerCase().equals("exit")) {
            launch();
            return;
        }

        System.out.println("Enter your password: ");
        String password = scanner.nextLine(); // the entered password

        // In this block, a connection to the data source is established via URL.
        try {

            // connection via URL (relative path)
            Connection connection = DriverManager.getConnection(DATA_SOURCE_URL);

            // Gets the account by email which is the primary key of accounts
            String sql_query = "SELECT * FROM account WHERE email = '" + email + "'";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql_query); // executes the SQL query

            // The queried email is compared with the entered email if they exist.
            if(result.next()) {

                String queried_email = result.getString("email");
                String queried_password = result.getString("password");

                if(email.equals(queried_email) && password.equals(queried_password)) {

                    // If logged in successfully, the currently logged-in email is stored.
                    currentlyLoggedInAccount = queried_email;

                    System.out.println("You are logged in!");

                    // prints the date and time on which the account is logged in.
                    System.out.println("Login data: " + LocalDate.now() + " ; " + LocalTime.now());
                    System.out.println("Account status: " + result.getString("status"));

                    return;
                } else {
                    System.out.println("Incorrect email or password! Try again.");
                    login(); // login is tried again if failed
                    return;
                }

            } else {
                System.out.println("Incorrect email or password! Try again.");
                login(); // login is tried again if failed
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } // end of try-catch

    }

    public static void create_new_account() {

        System.out.println("Enter your new email: ");

        String new_email = SCANNER.nextLine();

        // The loop repeats until an unused email is entered and until an email is entered that is in a valid format.
        while (checkIfEmailIsAlreadyInUse(new_email) || !EMAIL_FORMAT.test(new_email)) {

            System.out.println("Please enter \"exit\" to cancel.");
            new_email = SCANNER.nextLine(); // entering another email

            // In case that "exit" is entered
            if("exit".equals(new_email.toLowerCase())) {
                launch(); // return to the main menu.
                return;
            }

            if(checkIfEmailIsAlreadyInUse(new_email)) {
                System.out.println("This email is already in use! Please enter a new one: ");
            } else if(!EMAIL_FORMAT.test(new_email)) {
                System.out.println("The email must be in the format \"example@email.xyz\". Please enter a new one: ");
            }

        } // end of while

        // At this point, the email is valid.

        System.out.println("Please enter your new password: ");

        String password = SCANNER.nextLine();

        while (!PASSWORD_POLICY.test(password)) {
            System.out.println("The password must have at least 8 characters. Please enter a new password: ");
            password = SCANNER.nextLine();
        } // end of while

        // At this point, the password is successfully created.

        System.out.println("Please enter your first name: ");

        String first_name = SCANNER.nextLine();

        while (!VALID_NAME.test(first_name)) {
            System.out.println("Names must only contain A-Z or a-z. Please enter a new first name: ");
            first_name = SCANNER.nextLine();
        }

        System.out.println("Please enter your last name: ");

        String last_name = SCANNER.nextLine();

        while (!VALID_NAME.test(last_name)) {
            System.out.println("Names must only contain A-Z or a-z. Please enter a new last name: ");
            last_name = SCANNER.nextLine();
        }


        System.out.println("Please enter your phone number (optional). Leave empty or enter \"continue\" to skip.\"");
        String phone_number = SCANNER.nextLine();

        // loops infinitely until the phone number is valid.
        while (true) {
            if (phone_number.replaceAll(" ", "").isEmpty() || phone_number.toLowerCase().equals("continue")) {
                // do nothing
                phone_number = null;
                break;
            } else {
                if(PHONE_NUMBER_FORMAT.test(phone_number)) {

                    // replacing all white space to get a machine-readable format.
                    phone_number = phone_number.replaceAll(" ", "");
                    break;
                } else {
                    System.out.println("Invalid phone number! Please enter a valid phone number. Leave empty or enter \"continue\" to skip.");
                    phone_number = SCANNER.nextLine();
                }
            }
        }

        insertNewAccountIntoDatabase(new_email, password, first_name, last_name, phone_number);

    }


    /**
     * Inserting a new account to the database with the specified parameters.
     */
    private static void insertNewAccountIntoDatabase(String email, String password, String first_name, String last_name, String phone_number) {
        if(phone_number == null) {
            phone_number = "null"; // if phone number is null -> converting to an explicit string to insert into the database.
        }

        try {
            // connection via URL (relative path)
            Connection connection = DriverManager.getConnection(DATA_SOURCE_URL);

            // Checks if the specified email already exists in the data source. The email is not in use,
            // it would not exist in the data source.
            String sql_query = "insert into account values " +
                    "(" + hyphenator(email) + "," + hyphenator(password) + "," + hyphenator(first_name) + "," +
                    hyphenator(last_name) + "," + hyphenator("1990-12-23") // TODO: Change to dynamic birth date.
                    + "," + (phone_number.equals("null") ? "null" : hyphenator(phone_number)) + ","
                    + hyphenator("standard") + "," + DEFAULT_DEBT_LIMIT + ", " + '0' +
                    ")";

            System.out.println(sql_query);

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql_query); // SQL query to check if email already exists.


        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Account successfully created!");
        launch();

    }


    /**
     * Returns a {@link String} in the format 'example'.
     * @param string the specified string
     * @return string in the format 'example'.
     */
    private static String hyphenator(String string) {
        return "'" + string + "'";
    }


    /**
     * This static method checks if the specified email is already in use.
     *
     * @param email the specified email to be checked
     * @return true if the email is already in use.
     */
    private static boolean checkIfEmailIsAlreadyInUse(String email) {

        try {
            // connection via URL (relative path)
            Connection connection = DriverManager.getConnection(DATA_SOURCE_URL);

            // Checks if the specified email already exists in the data source. The email is not in use,
            // it would not exist in the data source.
            String sql_query = "SELECT * FROM account WHERE email = '" + email + "'";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql_query); // SQL query to check if email already exists.

            // If it returns false, it means that the specified email does not exist yet and can therefore be used
            // for creating a new account.
            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

}
