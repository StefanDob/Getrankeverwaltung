package de.tu.darmstadt.backend.backendService;

import com.vaadin.flow.server.VaadinService;
import de.tu.darmstadt.backend.database.SpringContext;

import de.tu.darmstadt.frontend.account.SessionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import de.tu.darmstadt.backend.database.Token.RememberMeTokenService;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.RememberMeToken;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;

import java.util.Optional;

public class CookieOperations {

    private static final String REMEMBER_ME_COOKIE_NAME = "remember_me";

    // Method to save the account using a remember-me cookie
    public static void saveAccount(Account account) {
        if (account == null || account.getId() == null) {
            throw new IllegalArgumentException("Account or Account ID cannot be null");
        }
        RememberMeTokenService rememberMeTokenService = SpringContext.getBean(RememberMeTokenService.class);
        RememberMeToken rememberMeToken = rememberMeTokenService.createToken(account.getId());
        Cookie cookie = new Cookie(REMEMBER_ME_COOKIE_NAME, rememberMeToken.getToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    // Method to retrieve the account associated with a remember-me cookie
    public static Account getAccount() {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        if (cookies == null) {
            return null; // No cookies present
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (REMEMBER_ME_COOKIE_NAME.equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null) {
            return null; // No remember-me cookie found
        }
        RememberMeTokenService rememberMeTokenService = SpringContext.getBean(RememberMeTokenService.class);
        Optional<RememberMeToken> rememberMeToken = rememberMeTokenService.getToken(token);
        if (!rememberMeToken.isPresent() || rememberMeToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            return null; // Token not found or expired
        }

        Long accountId = rememberMeToken.get().getUserId();
        try {
            return AccountOperations.getAccountByID(accountId);
        } catch (AccountPolicyException e) {
            throw new RuntimeException("Error retrieving account", e);
        }
    }

    public static void deleteCurrentAccount() {
        RememberMeTokenService rememberMeTokenService = SpringContext.getBean(RememberMeTokenService.class);
        Account account = SessionManagement.getAccount();

        if (account == null || account.getId() == null) {
            throw new IllegalStateException("No account is currently logged in.");
        }

        rememberMeTokenService.deleteTokenByUserId(account.getId());
    }
}

