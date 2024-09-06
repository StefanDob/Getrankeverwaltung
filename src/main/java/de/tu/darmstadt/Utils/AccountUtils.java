package de.tu.darmstadt.Utils;

import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class AccountUtils {
    //TODO redo or delete if not needed
    public static void main(String[] args) {
        try {
            for(int i = 0 ; i < 50 ; i++){}
                //System.out.println(generateID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Date convertToDate(LocalDate localDate) {
        // Convert LocalDate to LocalDateTime at the start of the day
        LocalDateTime localDateTime = localDate.atStartOfDay();

        // Convert LocalDateTime to Instant using the system's default time zone
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        // Convert Instant to Date
        return Date.from(instant);
    }

    public static List<Account> generateExampleAccounts() throws AccountPolicyException {
        List<Account> accounts = new ArrayList<>();

        // Define a date format for simplicity
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Create example accounts with deterministic values and emails without dots
        accounts.add(new Account("alicesmith@example.com", "password123", "Alice", "Smith",
                parseDate("1990-01-15"), "5551234567"));
        accounts.add(new Account("bobjohnson@example.com", "password456", "Bob", "Johnson",
                parseDate("1985-05-22"), "5552345678"));
        accounts.add(new Account("caroldavis@example.com", "password789", "Carol", "Davis",
                parseDate("1992-09-30"), "5553456789"));
        accounts.add(new Account("davemiller@example.com", "password101", "Dave", "Miller",
                parseDate("1988-03-10"), "5554567890"));
        accounts.add(new Account("evagarcia@example.com", "password202", "Eva", "Garcia",
                parseDate("1991-07-04"), "5555678901"));
        accounts.add(new Account("frankwilson@example.com", "password303", "Frank", "Wilson",
                parseDate("1989-12-12"), "5556789012"));
        accounts.add(new Account("gracemoore@example.com", "password404", "Grace", "Moore",
                parseDate("1993-11-20"), "5557890123"));
        accounts.add(new Account("hanktaylor@example.com", "password505", "Hank", "Taylor",
                parseDate("1986-04-25"), "5558901234"));
        accounts.add(new Account("irisanderson@example.com", "password606", "Iris", "Anderson",
                parseDate("1994-08-16"), "5559012345"));
        accounts.add(new Account("jackwhite@example.com", "password707", "Jack", "White",
                parseDate("1987-06-05"), "5550123456"));
        accounts.add(new Account("karenthompson@example.com", "password808", "Karen", "Thompson",
                parseDate("1995-02-28"), "5551234568"));
        accounts.add(new Account("leojones@example.com", "password909", "Leo", "Jones",
                parseDate("1990-11-11"), "5552345679"));
        accounts.add(new Account("monamartinez@example.com", "password010", "Mona", "Martinez",
                parseDate("1987-07-13"), "5553456780"));
        accounts.add(new Account("natebrown@example.com", "password121", "Nate", "Brown",
                parseDate("1992-10-30"), "5554567891"));
        accounts.add(new Account("oliviawilson@example.com", "password232", "Olivia", "Wilson",
                parseDate("1991-09-08"), "5555678902"));
        accounts.add(new Account("paulclark@example.com", "password343", "Paul", "Clark",
                parseDate("1989-12-25"), "5556789013"));
        accounts.add(new Account("quinnlewis@example.com", "password454", "Quinn", "Lewis",
                parseDate("1993-06-22"), "5557890124"));
        accounts.add(new Account("rachelkim@example.com", "password565", "Rachel", "Kim",
                parseDate("1988-04-15"), "5558901235"));
        accounts.add(new Account("sammartin@example.com", "password676", "Sam", "Martin",
                parseDate("1994-01-19"), "5559012346"));
        accounts.add(new Account("tinaroberts@example.com", "password787", "Tina", "Roberts",
                parseDate("1986-08-30"), "5550123457"));
        accounts.add(new Account("ursulaharris@example.com", "password898", "Ursula", "Harris",
                parseDate("1995-03-22"), "5551234569"));
        accounts.add(new Account("victorwalker@example.com", "password909", "Victor", "Walker",
                parseDate("1990-12-12"), "5552345680"));

        return accounts;
    }

    public static void createExampleAccounts() {
        List<Account> exampleAccounts = null;
        try {
            exampleAccounts = AccountUtils.generateExampleAccounts();
        } catch (AccountPolicyException e) {
            throw new RuntimeException(e);
        }
        exampleAccounts.forEach(account -> {
            AccountOperations.createAccount(account);
        });
    }
    /*

     */
    private static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts a java.util.Date to java.time.LocalDate using the system default time zone.
     *
     * @param date the java.util.Date to be converted
     * @return the converted LocalDate
     */
    public static LocalDate convertToLocalDate(Date date) {
        //TODO this method does not work right, check that it works fine
        if (date == null) {
            return null;
        }

        // Use Calendar to extract year, month, and day from java.util.Date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Extract year, month, and day
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and return LocalDate
        return LocalDate.of(year, month, day);
    }
}