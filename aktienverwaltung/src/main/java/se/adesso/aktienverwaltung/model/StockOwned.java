package se.adesso.aktienverwaltung.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class StockOwned {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long stockOwnedId;

    @JsonIgnore
    @OneToOne
    @JoinColumn
    Stock stockReference;
    long amount;

    public StockOwned(Stock stockReference, Long amount){
        this.stockReference = stockReference;
        this.amount = amount;
    }

    public StockOwned(){ }

    @JsonGetter("stockName")
    protected String getStockName(){
      return stockReference.getName();
    }

    @JsonGetter("stockSymbol")
    protected String getStockSymbol(){
        return stockReference.getSymbol();
    }

}
