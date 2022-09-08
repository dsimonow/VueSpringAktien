package se.adesso.aktienverwaltung.model.service;

import se.adesso.aktienverwaltung.model.dto.StockDto;
import se.adesso.aktienverwaltung.model.dto.UserDto;

public interface UserService {
    public void addStocktoUser(StockDto stockDto, String useremail);
    public UserDto findUserByEmail(String email);
}
