package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Item;

public class EmailOperations {

    public static void sendSuggestionToAdmin(String message){
        MailjetEmailSender.sendEmailToAdmins( "Suggestion received", "You received the following Sugggestion:\n" + message);
    }

    public static void sendLowStockNotification(Item item){
        MailjetEmailSender.sendEmailToAdmins( "Low Stock Notification" , "The stock for the item: " + item.getName() + " is low. It i" +
                "is currently at: " + item.getStock() + "." );
    }
}
