package se.adesso.aktienverwaltung.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Login {
    @Id
    String loginEmail;

}
