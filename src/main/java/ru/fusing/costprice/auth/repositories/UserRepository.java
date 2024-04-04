package ru.fusing.costprice.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fusing.costprice.auth.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);

}