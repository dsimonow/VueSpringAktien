package se.adesso.aktienverwaltung.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.adesso.aktienverwaltung.model.Portfolio;
import se.adesso.aktienverwaltung.model.Stock;
import se.adesso.aktienverwaltung.model.User;
import se.adesso.aktienverwaltung.persistence.repository.PortfolioRepository;
import se.adesso.aktienverwaltung.persistence.repository.StockRepository;
import se.adesso.aktienverwaltung.persistence.repository.UserRepository;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, StockRepository stockRepository, PortfolioRepository portfolioRepository) {
        return args -> {

            User u1 = new User("Berne","hermann");
            User u2 = new User("Brot","bernd");

            log.info("Preloading " + userRepository.save(u1));
            log.info("Preloading " + userRepository.save(u2));

        };
    }

}