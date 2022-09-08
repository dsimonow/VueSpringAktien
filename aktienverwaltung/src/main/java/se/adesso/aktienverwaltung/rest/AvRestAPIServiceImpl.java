package se.adesso.aktienverwaltung.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AvRestAPIServiceImpl implements AvRestAPIService{


    // Login and URL for Alphavantage API Endpoint
    String baseurl = "https://www.alphavantage.co/";
    String token = "LH3RL0QE8FD6YQYJ";

}
