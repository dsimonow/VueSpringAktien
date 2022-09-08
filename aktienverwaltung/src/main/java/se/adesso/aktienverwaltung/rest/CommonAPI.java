package se.adesso.aktienverwaltung.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.adesso.aktienverwaltung.model.*;
import se.adesso.aktienverwaltung.model.dto.AVGlobalQuoteDto;
import se.adesso.aktienverwaltung.model.dto.AVGlobalQuoteResponseDto;
import se.adesso.aktienverwaltung.model.dto.AVSearchBestMatchesDto;
import se.adesso.aktienverwaltung.model.dto.AVSearchDto;
import se.adesso.aktienverwaltung.persistence.repository.PortfolioRepository;
import se.adesso.aktienverwaltung.persistence.repository.StockPerformanceEntryRepository;
import se.adesso.aktienverwaltung.persistence.repository.StockRepository;
import se.adesso.aktienverwaltung.persistence.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class CommonAPI {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    StockPerformanceEntryRepository stockPerformanceEntryRepository;
    String baseurl = "https://www.alphavantage.co/";
    String token = "LH3RL0QE8FD6YQYJ";
    private WebClient client;

    public CommonAPI(WebClient.Builder builder) {
        this.client = builder.baseUrl(baseurl).build();
    }

    @GetMapping("/aviav/search/{searchterm}")
    public Flux<AVSearchDto> getSearchResults(@PathVariable String searchterm) {
        return aquireSearchResults(searchterm);
    }

    @GetMapping("/aviav/getStockQuote/{stocksymbol}")
    public Mono<AVGlobalQuoteDto> getStockQuote(@PathVariable String stocksymbol){
        return aquireStockGlobalQuote(stocksymbol);
    }

    @GetMapping("/aviav/addStock")
    public String addStockToUser(@RequestParam String stockSymbol, @RequestParam String stockName, @RequestParam Long requestingUserId, @RequestParam Long amount){
        AVGlobalQuoteDto globalQuoteDTO = aquireStockGlobalQuote(stockSymbol).block();
        String searchedStockName = aquireSearchResults(stockSymbol).blockFirst().getName();

        // todo: transfer Service Function

        User requestingUser = userRepository.findById(requestingUserId).get();
        Stock transferStock = new Stock(stockSymbol,searchedStockName);
        transferStock.addPerformance(new StockPerformanceEntry(globalQuoteDTO));
        StockOwned tempStockOwned = new StockOwned(transferStock,amount);
        requestingUser.getPortfolio().addStock(tempStockOwned);

        stockRepository.save(transferStock);
        userRepository.save(requestingUser);

        return searchedStockName+requestingUserId;
    }

    // Serverside Approach ##########################
    @GetMapping("/aviav/addStockJson")
    public String addStockToUserJson(@RequestParam String stockSymbol, @RequestParam String stockName, @RequestParam Long requestingUserId, @RequestParam Long amount){
        JsonNode notCheckedResponse = aquireStockGlobalQuoteString(stockSymbol);
        String searchedStockName = stockName;
        String response = "";
        ObjectMapper mapper = new ObjectMapper();
        if(notCheckedResponse.has("Error Message")){
            try {
                response = mapper.writeValueAsString(notCheckedResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if (notCheckedResponse.has("Note")) {
            try {
                response = mapper.writeValueAsString(notCheckedResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            AVGlobalQuoteResponseDto globalQuoteDTO = null;
            try {
                globalQuoteDTO = mapper.treeToValue(notCheckedResponse, AVGlobalQuoteResponseDto.class);
            } catch (JsonProcessingException e) {
                log.info(e.getMessage());
                e.printStackTrace();
            }

            User requestingUser = userRepository.findById(requestingUserId).get();
            Stock transferStock = new Stock(stockSymbol,searchedStockName);
            transferStock.addPerformance(new StockPerformanceEntry(globalQuoteDTO.getGlobalQuote()));
            StockOwned tempStockOwned = new StockOwned(transferStock,amount);
            requestingUser.getPortfolio().addStock(tempStockOwned);

            stockRepository.save(transferStock);
            userRepository.save(requestingUser);
            response = "added";
        }
        // todo: transfer Service Function
        return response;
    }

    @GetMapping("/aviav/deleteStock")
    public String deleteStockFromUser(@RequestParam String stockSymbol, @RequestParam Long requestingUserId){
        // todo: transfer Service Function

        User requestingUser = userRepository.findById(requestingUserId).get();
        Portfolio portfolio = portfolioRepository.findById(requestingUser.getPortfolio().getPortfolioId()).get();
        List<StockOwned> stocks = portfolio.getOwnedStocks();
        StockOwned todeleteStockOwned = null;
        Stock stockReference = null;
        for(StockOwned item : stocks){
            if(item.getStockReference().getSymbol().equals(stockSymbol)){
                stockReference = item.getStockReference();
                log.info("stockreference with symbol found!");
                todeleteStockOwned = item;
            }
        }
        if(stockReference != null){
            stocks.remove(todeleteStockOwned);
            stockRepository.save(stockReference);
        }
        portfolioRepository.saveAndFlush(portfolio);
        return stockSymbol+requestingUserId+"deleted";
    }

    @GetMapping("/login")
    public String handleLogin(){
        return "you should login";
    }

    @GetMapping("/aviav/getAll")
    public String getAllUserandStocks(){
        List<User> users = userRepository.findAll();
        String response = "";
        for (User u : users) {
            response += "User"+u.getUserId()+"\n";
            Portfolio portfolio = u.getPortfolio();
            List<StockOwned> stocks = portfolio.getOwnedStocks();
            for(StockOwned s : stocks){
                response += "Stock"+s.getStockReference().getSymbol()+" "+s.getStockReference().getName()+": ";
                for(Map.Entry<String,StockPerformanceEntry> entry : s.getStockReference().getPerformance().entrySet()){
                    response += "Entry"+entry.getKey()+" "+entry.getValue()+"\n";
                }
            }
        }
        return response;
    }

    @GetMapping("/aviav/getAllStockforUser")
    public Flux<StockOwned> getAllStockForUser(@RequestParam Long requestingUserId){
        User requestingUser = userRepository.findById(requestingUserId).get();
        Flux<StockOwned> response = Flux.fromIterable(requestingUser.getPortfolio().getOwnedStocks());
        log.info("getAllStock For User Request received");
        return response;
    }

    @GetMapping("/aviav/getStockPerformance")
    public Flux<StockPerformanceEntry> getStockPerformance(@RequestParam String stockSymbol, @RequestParam Long requestingUserId){
        User requestingUser = userRepository.findById(requestingUserId).get();
        List<StockOwned> liste = requestingUser.getPortfolio().getOwnedStocks();
        Map<String, StockPerformanceEntry> stockPerformanceEntryMap = null;
        Flux<StockPerformanceEntry> response;
        for(StockOwned stock : liste){
            if(stock.getStockReference().getSymbol().equals(stockSymbol)){
                stockPerformanceEntryMap = stock.getStockReference().getPerformance();
            }
        }
        if(stockPerformanceEntryMap == null){
            response = null;
        } else {
            response = Flux.fromIterable(stockPerformanceEntryMap.values());
        }
        return response;
    }

    // Utilities eigener Service
    // todo: in eigenen AV Service
    public Mono<AVGlobalQuoteDto> aquireStockGlobalQuote(String stockSymbol){
        return client.get()
                .uri("query?function=GLOBAL_QUOTE&symbol="+stockSymbol+"&apikey="+token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AVGlobalQuoteResponseDto.class)
                .map(AVGlobalQuoteResponseDto::getGlobalQuote);
    }

    // todo: in eigenen AV Service
    public Flux<AVSearchDto> aquireSearchResults(String searchTerm){
        return client.get()
                .uri("query?function=SYMBOL_SEARCH&keywords="+searchTerm+"&apikey="+token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AVSearchBestMatchesDto.class)
                .flatMapMany(searchResult -> Flux.fromIterable(searchResult.getBestMatches()));
    }

    public JsonNode aquireStockGlobalQuoteString(String stockSymbol){
        JsonNode response = client.get()
                .uri("query?function=GLOBAL_QUOTE&symbol="+stockSymbol+"&apikey="+token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        return response;
    }
    /*
    @Override
    public Mono<AVSearchResultDto> getBestMatchesForSearch(String searchterm){
        logger.info(searchterm);
        return this.webClient.get()
                .uri("query?function=SYMBOL_SEARCH&keywords="+searchterm+"&apikey="+token)
                .retrieve().bodyToMono(AVSearchResultDto.class);
    }

    @Override
    public Mono<AVQuoteResultDTO> getQuoteForStock(String stockSymbol){
        return this.webClient.get()
                .uri("query?function=GLOBAL_QUOTE&symbol="+stockSymbol+"&apikey="+token)
                .retrieve().bodyToMono(AVQuoteResultDTO.class);
    }
    */

}
