package de.tu.darmstadt.Utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Manages language translations for the application.
 * <p>
 * This class provides methods to get localized text based on the current language setting and to change the language.
 */
public class LanguageManager {

    /** Default locale set to English. */
    private static Locale locale = Locale.ENGLISH;

    /** Map storing translations for different keys and locales. */
    private static final Map<String, Map<Locale, String>> languageMap = new HashMap<>();

    static {
        // Add translations for various keys
        addTranslation("buy", "Buy", "Kaufen");
        addTranslation("Last Transactions", "Last Transactions", "Letzte Transaktionen");
        addTranslation("Store", "Store", "Laden");
        addTranslation("sender", "sender", "sender");
        addTranslation("receiver", "receiver", "Empfänger");
        addTranslation("amount", "amount", "Betrag");
        addTranslation("transactionDate", "transactionDate", "Transaktionsdatum");
        addTranslation("Please login", "Please login", "Bitte einloggen");
        addTranslation("General Information", "General Information", "Allgemeine Informationen");
        addTranslation("First Name", "First Name", "Vorname");
        addTranslation("Last Name", "Last Name", "Nachname");
        addTranslation("Email", "Email", "Email");
        addTranslation("Password", "Password", "Passwort");
        addTranslation("Birth Date", "Birth Date", "Geburtsdatum");
        addTranslation("Phone Number", "Phone Number", "Telefonnummer");
        addTranslation("Account Balance", "Account Balance", "Kontostand");
        addTranslation("Debt Limit", "Debt Limit", "Schuldengrenze");
        addTranslation("Status", "State", "Status");
        addTranslation("Receiver", "Receiver", "Empfänger");
        addTranslation("Amount", "Amount", "Betrag");
        addTranslation("Transaction Text", "Transaction Text", "Transaktionstext");
        addTranslation("Save", "Save", "Speichern");
        addTranslation("Cancel", "Cancel", "Abbrechen");
        addTranslation("Transaction commited", "Transaction commited", "Transaktion bestätigt");
        addTranslation("Please Enter Receiver", "Please Enter Receiver", "Bitte fügen sie den Empfänger hinzu");
        addTranslation("Receiver does not exist", "Receiver does not exist", "Empfänger existiert nicht");
        addTranslation("Enter Amount", "Enter Amount", "Betrag einfügen!");
        addTranslation("Amount cannot be negative or 0", "Amount cannot be negative or 0", "Betrag darf nicht negativ oder 0 sein");
        addTranslation("Your account does not have enough coverage", "Your account does not have enough coverage", "Konto hat nicht genug liquide Mittel");
        addTranslation("Transactions", "Transactions", "Transaktionen");
        addTranslation("Transfer Money", "Transfer Money", "Geld senden");
        addTranslation("Logout", "Logout", "Abmelden");
        addTranslation("Sender", "Sender", "Sender");
        addTranslation("Text", "Text", "Text");
        addTranslation("Date", "Date", "Datum");
        addTranslation("Login", "Login", "Anmelden");
        addTranslation("Remember Me", "Remember Me", "Angemeldet bleiben");
        addTranslation("Create Account", "Create Account", "Konto erstellen");
        addTranslation("Phone Number (optional)", "Phone Number (optional)", "Telefonnummer (freiwillig)");
        addTranslation("Account created successfully!", "Account created successfully!", "Konto wurde erfolgreich erstellt");
        addTranslation("Balance", "Balance", "Saldo");
        addTranslation("State", "State", "Status");
        addTranslation("Invalid debt limit format", "Invalid debt limit format", "Invalide Schuldengrenze Format");
        addTranslation("Invalid balance format", "Invalid balance format", "Invalider Saldo Format");
        addTranslation("Account updated successfully", "Account updated successfully", "Konto wurde erfolgreich geändert");
        addTranslation("Admin Options", "Admin Options", "Administrator Einstellungen");
        addTranslation("Items List", "Items List", "Artikelliste");
        addTranslation("Item image", "Item image", "Artikelfoto");
        addTranslation("Image", "Image", "Foto");
        addTranslation("Name", "Name", "Name");
        addTranslation("Description", "Description", "Beschreibung");
        addTranslation("Price", "Price", "Preis");
        addTranslation("Stock", "Stock", "Lager");
        addTranslation("Create Item", "Create Item", "Artikel hinzufügen");
        addTranslation("Accounts List", "Accounts List", "Konten Liste");
        addTranslation("Item removed", "Item removed", "Artikel löschen");
        addTranslation("Quantity updated", "Quantity updated", "Menge ändern");
        addTranslation("Quantity", "Quantity", "Menge");
        addTranslation("Buy Now", "Buy Now", "Jetzt kaufen");
        addTranslation("Buying completed...", "Buying completed...", "Kauf beendet...");
        addTranslation("You are not logged in yet", "You are not logged in yet", "Sie sind noch nicht angemeldet");
        addTranslation("Please login if you want to use the Shopping Cart 🤗", "Please login if you want to use the Shopping Cart 🤗", "Bitte anmelden um den Warenkorb zu nutzen");
        addTranslation("Menu toggle", "Menu toggle", "Menü schalter");
        addTranslation("Shopping", "Shopping", "Einkaufen");
        addTranslation("Account", "Account", "Konto");
        addTranslation("Shopping Cart", "Shopping Cart", "Einkaufswagen");
        addTranslation("Admin", "Admin", "Admin");
        addTranslation("Failed to load image from URL: ", "Failed to load image from URL: ", "Fehler beim Laden des Bildes von der URL");
        addTranslation("Image upload failed: ", "Image upload failed: ", "Fehler beim hochladen");

        addTranslation("Delete Item", "Delete Item", "Artikel Löschen");
        addTranslation("Are you sure you want to delete this item?", "Are you sure you want to delete this item?", "Artikel wirklich löschen?");
        addTranslation("Confirm", "Confirm", "Bestätigen");
        addTranslation("Buy", "Buy", "Kaufen");
        addTranslation("Cart", "Cart", "Korb");
        addTranslation("Item added to cart", "Item added to cart", "Artikel wurde hinzugefügt");
        addTranslation("Search items...", "Search items...", "Artikel suchen");
        addTranslation("Add to Shopping Cart", "Add to Shopping Cart", "Zum Warenkorb hinzufügen");
        addTranslation("Close", "Close", "Schließen");
        addTranslation("Your account is restricted. Contact an admin.", "Your account is restricted. Contact an admin.", "Das Konto ist restricted. Kontaktiere einen Admin");
        addTranslation("You bought a ", "You bought a ", "Einkauf");
        addTranslation("You bought the Shopping Cart ", "You bought the Shopping Cart ", "Warenkorb wurde gekauft");
        addTranslation("Image URL", "Image URL", "Foto URL");
        addTranslation("Select file...", "Select file...", "Datei auswählen...");
        addTranslation("Drop file here...", "Drop file here...", "Datei hier einfügen");
        addTranslation("Uploading file...", "Uploading file...", "Hochladen läuft");
        addTranslation("Only a-z or A-Z, as well as '-' and white spaces are allowed in names.", "Only a-z or A-Z, as well as '-' and white spaces are allowed in names.", "Nur a-z, A-Z, oder '-' und white spaces sind im Namen erlaubt.");
        addTranslation("This email is already in use: ", "This email is already in use: ", "Diese E-mail wird bereits benutzt: ");
        addTranslation("Password must contain at least ", "Password must contain at least ", "Passwort muss mindestens ");
        addTranslation(" characters.", " characters.", "Zeichen enthalten.");
        addTranslation("Password is not safe. Enter a new password", "Password is not safe. Enter a new password", "Passwort ist unsicher. Bitte ein neues Passwort eingeben.");
        addTranslation("Balance should not exceed the debt limit. ", "Balance should not exceed the debt limit. ", "Kontostand nach der Transaktion darf die Schuldengrenze nicht überschreiten");
        addTranslation("Current debt limit: ", "Current debt limit: ", "Aktuelle Schuldengrenze: ");
        addTranslation(" ; Balance after setting to new value: ", " ; Balance after setting to new value: ", " ; Kontostand nach der Trnsaktion: ");
        addTranslation("Email/Password combination does not exist", "Email/Password combination does not exist", "Email/Passwort Kombination existiert nicht");
        addTranslation("Download Instruction Manual", "Download Instruction Manual", "Anleitung runterladen");
        addTranslation("Suggestions", "Suggestions", "Vorschläge");
        addTranslation("Enter your suggestions here...", "Enter your suggestions here...", "Vorschläge hier eingeben ...");
        addTranslation("Submit", "Submit", "Einreichen");
        addTranslation("Please enter a suggestion", "Please enter a suggestion", "Bitte Vorschlage eingeben");
        addTranslation("Thank you for your suggestion!", "Thank you for your suggestion!", "Vielen Dank für den Vorschlag");
        addTranslation("Your account does not have enough coverage", "Your account does not have enough coverage", "Dein Konto ist nicht ausreichend gedeckt!");
        addTranslation("The product is currently not available", "The product is currently not available", "Das Produkt ist momentan nicht auf Lager");
        addTranslation("One of the products exceeds the stock, please make it fit", "One of the products exceeds the stock, please make it fit", "Eines der Einträge ist mit einer höheren Menge als es im Lager gibt angegeben. Bitte ändern!");









    }
    /**
     * Adds a translation for a given key in English and German.
     *
     * @param key the key for the translation
     * @param englishTranslation the translation in English
     * @param germanTranslation the translation in German
     */
    private static void addTranslation(String key, String englishTranslation, String germanTranslation) {
        Map<Locale, String> translations = new HashMap<>();
        translations.put(Locale.ENGLISH, englishTranslation);
        translations.put(Locale.GERMAN, germanTranslation);
        languageMap.put(key, translations);
    }

    /**
     * Returns the localized text for a given key based on the current language setting.
     *
     * @param key the key for which to retrieve the localized text
     * @return the localized text if available, otherwise returns the key itself
     */
    public static String getLocalizedText(String key) {
        Map<Locale, String> translations = languageMap.get(key);
        return translations != null ? translations.get(locale) : key;
    }

    /**
     * Returns the current language locale.
     *
     * @return the current locale
     */
    public static Locale getCurrentLanguage() {
        return locale;
    }

    /**
     * Sets the language for the application.
     *
     * @param selectedLocale the locale to set as the current language
     */
    public static void setLanguage(Locale selectedLocale) {
        locale = selectedLocale;
    }
}

