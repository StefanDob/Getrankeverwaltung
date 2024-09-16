package de.tu.darmstadt.dataModel;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * This class represents the JPA mapping of the "remember me" session tokens used for persistent login functionality.
 * It maps the tokens stored in browser cookies to specific user accounts, enabling the application to remember users
 * through cookies for future sessions.
 */
@Entity
@Table(name = "remember_me_token")
public class RememberMeToken {

    /**
     * A unique identifier for the {@link RememberMeToken}. It serves as the primary key in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The token value associated with the "remember me" session. It is stored as a unique string in the database.
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * The ID of the user associated with this token. This corresponds to the user being "remembered" by the session.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * The expiration date and time of the token. Once this date is reached, the token will no longer be valid.
     */
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    // :::::::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::::::

    /**
     * Gets the unique identifier of the {@link RememberMeToken}.
     *
     * @return the id of the token
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the {@link RememberMeToken}.
     *
     * @param id the new id of the token
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the token value.
     *
     * @return the token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token value.
     *
     * @param token the new token string
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the user ID associated with the token.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the token.
     *
     * @param userId the new user ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the expiration date and time of the token.
     *
     * @return the expiration date and time
     */
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiration date and time of the token.
     *
     * @param expiryDate the new expiration date and time
     */
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}


