package de.tu.darmstadt.frontend.support;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.backend.backendOperations.EmailOperations;
import de.tu.darmstadt.frontend.MainLayout;

/**
 * The SupportView class represents the support page of the application, where users can download an instruction manual
 * and submit suggestions. The view is available only to logged-in users.
 */
@PageTitle("Support")
@Route(value = "support", layout = MainLayout.class)
public class SupportView extends VerticalLayout {

    /**
     * Constructs the SupportView page.
     * Displays a link to download the instruction manual and, if the user is logged in, a text area to submit suggestions.
     */
    public SupportView() {

        // Create a link to download the instruction manual PDF
        Anchor pdfLink = new Anchor("/manual.pdf", LanguageManager.getLocalizedText("Download Instruction Manual"));
        pdfLink.getElement().setAttribute("download", true); // Set the link as a downloadable file
        pdfLink.getStyle().set("font-size", "18px"); // Style the link

        // Check if the user is logged in before displaying the suggestion form
        if (SessionManagement.getAccount() != null) {
            // Create a text area for submitting suggestions
            TextArea suggestionsTextArea = new TextArea("Suggestions");
            suggestionsTextArea.setPlaceholder(LanguageManager.getLocalizedText("Enter your suggestions here...")); // Placeholder text
            suggestionsTextArea.setWidthFull();
            suggestionsTextArea.setHeight("150px"); // Set height for the text area

            // Create a button to submit the suggestions
            Button submitButton = new Button(LanguageManager.getLocalizedText("Submit"), event -> {
                String suggestion = suggestionsTextArea.getValue();

                // Check if the suggestion is not empty before submitting
                if (suggestion.isEmpty()) {
                    // Show error notification if the text area is empty
                    Notification.show(LanguageManager.getLocalizedText("Please enter a suggestion"), 3000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                } else {
                    // Send the suggestion via email and show a success notification
                    EmailOperations.sendSuggestionToAdmin(suggestion);
                    Notification.show(LanguageManager.getLocalizedText("Thank you for your suggestion!"), 3000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    suggestionsTextArea.clear(); // Clear the text area after submission
                }
            });

            // Add the PDF link, suggestions text area, and submit button to the layout
            add(pdfLink, suggestionsTextArea, submitButton);
        } else {
            // If the user is not logged in, only display the instruction manual link
            add(pdfLink);
        }
    }
}

