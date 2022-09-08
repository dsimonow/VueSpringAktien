package se.adesso.aktienverwaltung.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userId;
    String firstName;
    String lastName;
    String password;
    String userName;

    @OneToOne(cascade= CascadeType.ALL)
    Portfolio portfolio;

    public User(){
        portfolio = new Portfolio();
    }
    public User(String username, String password){
        this.userName = username;
        this.password = password;
        portfolio = new Portfolio();
    }



}
