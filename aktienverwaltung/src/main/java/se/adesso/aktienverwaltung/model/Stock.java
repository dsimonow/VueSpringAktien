package se.adesso.aktienverwaltung.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class Stock {
    @Id
    String symbol;
    String name;
    @OneToMany(cascade= CascadeType.ALL)
    Map<String, StockPerformanceEntry> performance;

    public Stock(){
        this.performance = new HashMap<>();
    }
    public Stock(StockPerformanceEntry stockPerformance){
        this.performance.put(stockPerformance.latestTradingDay,stockPerformance);
    }

    public Stock(String symbol, String name){
        this.symbol = symbol;
        this.name = name;
        this.performance = new HashMap<>();
    }
    public void addPerformance(StockPerformanceEntry stockPerformance){
        performance.put(stockPerformance.latestTradingDay,stockPerformance);
    }


}
