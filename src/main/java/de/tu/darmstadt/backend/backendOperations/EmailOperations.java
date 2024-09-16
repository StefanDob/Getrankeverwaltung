package de.tu.darmstadt.backend.backendOperations;

import de.tu.darmstadt.dataModel.Item;

/**
 * The {@link EmailOperations} class provides utility methods to send various types of notifications via email.
 * It includes methods for sending suggestions to admins and low stock notifications.
 */
public class EmailOperations {

    /**
     * Sends a suggestion message to the admin via email.
     *
     * @param message the suggestion message to be sent to the admin.
     */
    public static void sendSuggestionToAdmin(String message) {
        String subject = "Suggestion received";
        String body = "You received the following suggestion:\n" + message;

        MailjetEmailSender.sendEmailToAdmins(subject, body);
    }

    /**
     * Sends a low stock notification for the specified {@link Item} to the admin via email.
     *
     * @param item the {@link Item} that is low in stock.
     */
    public static void sendLowStockNotification(Item item) {
        String subject = "Low Stock Notification";
        String body = "The stock for the item: " + item.getName() + " is low. It is currently at: " + item.getStock() + ".";

        MailjetEmailSender.sendEmailToAdmins(subject, body);
    }
}

