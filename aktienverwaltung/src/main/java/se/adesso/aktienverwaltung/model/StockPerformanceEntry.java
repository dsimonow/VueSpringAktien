package se.adesso.aktienverwaltung.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import se.adesso.aktienverwaltung.model.dto.AVGlobalQuoteDto;

import javax.persistence.*;

@Entity
@Data
public class StockPerformanceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long stockPerformanceEntryId;
    String latestTradingDay;
    double open;
    double high;
    double low;
    double price;
    long volume;
    double previousClose;
    double change;
    String changePercent;

    public StockPerformanceEntry(AVGlobalQuoteDto avGlobalQuoteDto){
        this.latestTradingDay = avGlobalQuoteDto.getLatestTradingDay();
        this.open = avGlobalQuoteDto.getOpen();
        this.high = avGlobalQuoteDto.getHigh();
        this.low = avGlobalQuoteDto.getLow();
        this.price = avGlobalQuoteDto.getPrice();
        this.volume = avGlobalQuoteDto.getVolume();
        this.previousClose = avGlobalQuoteDto.getPreviousClose();
        this.change = avGlobalQuoteDto.getChange();
        this.changePercent = avGlobalQuoteDto.getChangePercent();
    }

    public StockPerformanceEntry(){

    }

}
