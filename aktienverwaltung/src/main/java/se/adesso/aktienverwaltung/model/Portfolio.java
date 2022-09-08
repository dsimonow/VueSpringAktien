package se.adesso.aktienverwaltung.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long portfolioId;
    @JoinTable
    @OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
    List<StockOwned> ownedStocks;

    public Portfolio(){
        this.ownedStocks = new ArrayList<>();
    }

    public String toString(){
        return getPortfolioId()+" ";
    }

    public void addStock(StockOwned stockOwned){
        boolean stockExists = false;
        for(StockOwned item : ownedStocks) {
            if(item.stockReference.getSymbol().equals(stockOwned.stockReference.getSymbol())){
                stockExists = true;
                item.setAmount(stockOwned.getAmount());
            }
        }
        if(!stockExists) {
            ownedStocks.add(stockOwned);
        }
    }
}
