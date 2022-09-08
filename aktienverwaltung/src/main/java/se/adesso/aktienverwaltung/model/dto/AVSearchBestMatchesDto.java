package se.adesso.aktienverwaltung.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AVSearchBestMatchesDto {
    List<AVSearchDto> bestMatches;
}
