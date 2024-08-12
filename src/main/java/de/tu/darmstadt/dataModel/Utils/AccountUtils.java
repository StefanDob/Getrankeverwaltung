package de.tu.darmstadt.dataModel.Utils;

import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        Random random = new Random();
        String[] firstNames = {"John", "Jane", "Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Hank"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor"};
        String[] emailDomains = {"example.com", "test.com", "demo.com", "sample.com", "mail.com"};

        for (int i = 0; i < 20; i++) {
            String email = "user" + random.nextInt(1000) + "@" + emailDomains[random.nextInt(emailDomains.length)];
            String password = generateRandomString(8); // Random password with 8 characters
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            long minDate = new Date(70, 0, 1).getTime(); // January 1, 1970
            long maxDate = new Date(100, 0, 1).getTime(); // January 1, 2000
            Date birthDate = new Date(minDate + (long) (random.nextDouble() * (maxDate - minDate)));
            String phoneNumber = "+1" + (100 + random.nextInt(900))  + (100 + random.nextInt(900))  + (1000 + random.nextInt(9000));

            Account account = new Account(email, password, firstName, lastName, birthDate, phoneNumber);
            accounts.add(account);
        }

        return accounts;
    }

    private static String generateRandomString(int length) {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
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
}
