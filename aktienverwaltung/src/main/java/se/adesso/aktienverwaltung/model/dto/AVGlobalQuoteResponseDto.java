package se.adesso.aktienverwaltung.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class AVGlobalQuoteResponseDto {
    @JsonAlias("Global Quote")
    AVGlobalQuoteDto globalQuote;
}
