package de.tu.darmstadt;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import de.tu.darmstadt.backend.backendService.AccountOperations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation to make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "getrankelager")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //System.out.println(AccountOperations.getAccountByEmail("example@foo.de").getFirst_name());
    }

}
