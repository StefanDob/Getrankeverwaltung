package de.tu.darmstadt.backend.backendOperations;

import com.vaadin.flow.server.VaadinService;
import de.tu.darmstadt.backend.database.SpringContext;

import de.tu.darmstadt.Utils.SessionManagement;
import de.tu.darmstadt.backend.database.Token.RememberMeTokenService;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.RememberMeToken;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;

import java.util.Optional;

/**
 * The {@link CookieOperations} class provides utility methods for handling remember-me cookies
 * to save, retrieve, and delete accounts using cookies.
 */
public class CookieOperations {

    private static final String REMEMBER_ME_COOKIE_NAME = "remember_me";

    /**
     * Saves the specified {@link Account} by creating a remember-me cookie for it.
     * The cookie is valid for 30 days and contains a token linked to the account.
     *
     * @param account the {@link Account} to be saved.
     * @throws IllegalArgumentException if the account or account ID is null.
     */
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

    /**
     * Retrieves the {@link Account} associated with the current remember-me cookie.
     *
     * @return the {@link Account} if a valid remember-me cookie is found and the token is valid;
     *         otherwise, returns {@code null}.
     */
    public static Account getAccount() {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        if (cookies == null) {
            return null; // No cookies present
        }

        String token = getRememberMeToken(cookies);
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

    /**
     * Deletes the remember-me token associated with the currently logged-in {@link Account}.
     *
     * @throws IllegalStateException if no account is currently logged in.
     */
    public static void deleteCurrentAccount() {
        Account account = SessionManagement.getAccount();
        if (account == null || account.getId() == null) {
            throw new IllegalStateException("No account is currently logged in.");
        }

        RememberMeTokenService rememberMeTokenService = SpringContext.getBean(RememberMeTokenService.class);
        rememberMeTokenService.deleteTokenByUserId(account.getId());
    }

    /**
     * Helper method to extract the remember-me token from the list of cookies.
     *
     * @param cookies the array of cookies from the request.
     * @return the token if the remember-me cookie is found; otherwise, {@code null}.
     */
    private static String getRememberMeToken(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (REMEMBER_ME_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}


