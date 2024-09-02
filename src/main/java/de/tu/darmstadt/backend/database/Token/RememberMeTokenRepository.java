package de.tu.darmstadt.backend.database.Token;

import de.tu.darmstadt.dataModel.RememberMeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, Long> {

    Optional<RememberMeToken> findByToken(String token);

    void deleteByUserId(Long userId);
}
