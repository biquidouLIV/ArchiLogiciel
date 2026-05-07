package com.rubika.archilogiciel.controller;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "Users")
public class UserMongoModel {

    @MongoId
    private String login;
    private String password;

    public UserMongoModel() {}

    public UserMongoModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() { return login; }
    public String getPassword() { return password; }
}