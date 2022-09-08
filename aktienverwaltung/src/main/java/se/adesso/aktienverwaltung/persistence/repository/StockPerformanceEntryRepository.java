package se.adesso.aktienverwaltung.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.adesso.aktienverwaltung.model.Stock;
import se.adesso.aktienverwaltung.model.StockPerformanceEntry;

public interface StockPerformanceEntryRepository extends JpaRepository<StockPerformanceEntry, Long> {
}
