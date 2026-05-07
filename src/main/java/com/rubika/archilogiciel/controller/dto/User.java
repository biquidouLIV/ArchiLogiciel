package com.rubika.archilogiciel.controller.dto;

public class User {
    private String id;
    private String login;
    private String password;


    public String getId(){return this.id;}
    public void setId(String id){this.id = id;}

    public String getLogin(){return this.login;}
    public void setLogin(String login){this.login = login;}

    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;}

}
