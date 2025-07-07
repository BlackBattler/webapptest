package com.openclassrooms.webapp.model;

import lombok.Data;

//@Data permet de generer automatiquement les getters et setters pour chaque attribut
@Data
public class Employee {

    private Integer id ;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;

}
