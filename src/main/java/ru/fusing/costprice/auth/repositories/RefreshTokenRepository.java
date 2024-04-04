package ru.fusing.costprice.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fusing.costprice.auth.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
