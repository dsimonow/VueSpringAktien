package se.adesso.aktienverwaltung.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class AVGlobalQuoteDto {
    @JsonAlias("01. symbol")
    String symbol;
    @JsonAlias("02. open")
    double open;
    @JsonAlias("03. high")
    double high;
    @JsonAlias("04. low")
    double low;
    @JsonAlias("05. price")
    double price;
    @JsonAlias("06. volume")
    long volume;
    @JsonAlias("07. latest trading day")
    String latestTradingDay;
    @JsonAlias("08. previous close")
    double previousClose;
    @JsonAlias("09. change")
    double change;
    @JsonAlias("10. change percent")
    String changePercent;
}
