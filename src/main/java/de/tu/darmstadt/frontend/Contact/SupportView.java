package de.tu.darmstadt.frontend.Contact;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.tu.darmstadt.Utils.LanguageManager;
import de.tu.darmstadt.backend.backendService.EmailOperations;
import de.tu.darmstadt.frontend.MainLayout;
import jakarta.validation.constraints.Email;

@PageTitle("Support")
@Route(value = "support", layout = MainLayout.class)
public class SupportView extends VerticalLayout {

    public SupportView() {
        // Instruction manual PDF link
        Anchor pdfLink = new Anchor("/manual.pdf", LanguageManager.getLocalizedText("Download Instruction Manual"));
        pdfLink.getElement().setAttribute("download", true);
        pdfLink.getStyle().set("font-size", "18px");

        // Suggestions section
        TextArea suggestionsTextArea = new TextArea("Suggestions");
        suggestionsTextArea.setPlaceholder(LanguageManager.getLocalizedText("Enter your suggestions here..."));
        suggestionsTextArea.setWidthFull();
        suggestionsTextArea.setHeight("150px");

        Button submitButton = new Button(LanguageManager.getLocalizedText("Submit"), event -> {
            String suggestion = suggestionsTextArea.getValue();
            if (suggestion.isEmpty()) {
                Notification.show(LanguageManager.getLocalizedText("Please enter a suggestion"), 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            } else {
                EmailOperations.sendSuggestionToAdmin(suggestion);
                Notification.show(LanguageManager.getLocalizedText("Thank you for your suggestion!"), 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                suggestionsTextArea.clear();
            }
        });




        // Add components to layout
        add(pdfLink,suggestionsTextArea,submitButton);
    }
}
