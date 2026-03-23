package com.rubika.archilogiciel.controller.dto;

import java.util.UUID;

public class Personnage {
    private String name;
    private String classe;
    private String race;
    private int level;




    public int getLevel(){
        return this.level;
    }
    public void setLevel(int level){
        this.level = level;
    }



    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }


    public String getClasse(){
        return this.classe;
    }
    public void setClasse(String classe){
        this.classe = classe;
    }

    public String getRace(){
        return this.race;
    }
    public void setRace(String race){
        this.race = race;
    }
}
