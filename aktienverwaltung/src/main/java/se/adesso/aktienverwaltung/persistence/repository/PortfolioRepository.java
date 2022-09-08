package se.adesso.aktienverwaltung.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.adesso.aktienverwaltung.model.Portfolio;
import se.adesso.aktienverwaltung.model.Stock;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
