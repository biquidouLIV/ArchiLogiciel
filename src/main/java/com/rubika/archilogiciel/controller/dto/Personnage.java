package com.rubika.archilogiciel.controller.dto;

import com.rubika.archilogiciel.controller.HelloController;

import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;

public class Personnage {
    private String name;
    private String classe;
    private String race;
    private int level;

    private int force;
    private int agilite;
    private int endurance;
    private int rapidite;
    private int intelligence;
    private int chance;
    private int charisme;


    public Personnage(){
        for (int i = 0; i < 20; i++)
        {
            switch ((int)Math.random()*7){
                case 1:
                    force++;
                    break;
                case 2:
                    agilite++;
                    break;
                case 3:
                    endurance++;
                    break;
                case 4:
                    rapidite++;
                    break;
                case 5:
                    intelligence++;
                    break;
                case 6:
                    chance++;
                    break;
                case 7:
                    charisme++;
                    break;
            }
        }
    }

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

    public String getRace(){ return this.race;  }
    public void setRace(String race){
        this.race = race;
    }



    public int getForce(){
        return this.force;
    }
    public void setForce(int force){
        this.force = force;
    }



    public int getAgilite(){
        return this.agilite;
    }
    public void setAgilite(int agilite){
        this.agilite = agilite;
    }



    public int getEndurance(){
        return this.endurance;
    }
    public void setEndurance(int endurance){
        this.endurance = endurance;
    }



    public int getRapidite(){
        return this.rapidite;
    }
    public void setRapidite(int rapidite){
        this.rapidite = rapidite;
    }


    public int getIntelligence(){
        return this.intelligence;
    }
    public void setIntelligence(int intelligence){
        this.intelligence = intelligence;
    }



    public int getChance(){
        return this.chance;
    }
    public void setChance(int chance){
        this.chance = chance;
    }


    public int getCharisme(){
        return this.charisme;
    }
    public void setCharisme(int charisme){
        this.charisme = charisme;
    }

}
