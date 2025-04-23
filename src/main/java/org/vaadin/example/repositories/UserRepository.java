package org.vaadin.example.repositories;

import org.vaadin.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// vastaa User-entiteetin tietokantaoperaatioista
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}