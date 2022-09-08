package se.adesso.aktienverwaltung.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.adesso.aktienverwaltung.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>
{
}
