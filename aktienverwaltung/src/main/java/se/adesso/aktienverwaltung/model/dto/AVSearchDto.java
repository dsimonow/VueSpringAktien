package se.adesso.aktienverwaltung.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class AVSearchDto {
    @JsonAlias("1. symbol")
    String symbol;
    @JsonAlias("2. name")
    String name;
    @JsonAlias("3. type")
    String type;
    @JsonAlias("4. region")
    String region;
    @JsonAlias("5. marketOpen")
    String marketOpen;
    @JsonAlias("6. marketClose")
    String marketClose;
    @JsonAlias("7. timezone")
    String timezone;
    @JsonAlias("8. currency")
    String currency;
    @JsonAlias("9. matchScore")
    double matchScore;
}
