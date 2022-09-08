package se.adesso.aktienverwaltung.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.adesso.aktienverwaltung.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUserName(String userName);
}
