package de.tu.darmstadt.backend.database.Token;

import de.tu.darmstadt.dataModel.RememberMeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RememberMeTokenService {

    @Autowired
    private RememberMeTokenRepository tokenRepository;

    public RememberMeToken createToken(Long userId) {
        RememberMeToken token = new RememberMeToken();
        token.setUserId(userId);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusDays(30)); // Token valid for 30 days
        return tokenRepository.save(token);
    }

    public Optional<RememberMeToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }
    @Transactional
    public void deleteTokenByUserId(Long userId) {
        tokenRepository.deleteByUserId(userId);
    }
}

