package com.rubika.archilogiciel.controller;

import com.rubika.archilogiciel.controller.dto.Personnage;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.UUID;

@Document(collection = "Personnage")
public class PersonnageMongoModel {

    @MongoId
    private UUID id;
    private String name;
    private String classe;
    private String race;
    private int level;
    private String ownerId;
    private int force;
    private int agilite;
    private int endurance;
    private int rapidite;
    private int intelligence;
    private int chance;
    private int charisme;
    private int statsPoints;

    public PersonnageMongoModel() {}

    public PersonnageMongoModel(Personnage personnage) {
        this.id = UUID.randomUUID();
        this.name = personnage.getName();
        this.classe = personnage.getClasse();
        this.race = personnage.getRace();
        this.level = personnage.getLevel();
        this.force = personnage.getForce();
        this.agilite = personnage.getAgilite();
        this.endurance = personnage.getEndurance();
        this.rapidite = personnage.getRapidite();
        this.intelligence = personnage.getIntelligence();
        this.chance = personnage.getChance();
        this.charisme = personnage.getCharisme();
        this.statsPoints = personnage.getStatsPoints();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public String getRace() { return race; }
    public void setRace(String race) {this.race = race;}
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    public int getForce() { return force; }
    public void setForce(int force) { this.force = force; }
    public int getAgilite() { return agilite; }
    public void setAgilite(int agilite) { this.agilite = agilite; }
    public int getEndurance() { return endurance; }
    public void setEndurance(int endurance) { this.endurance = endurance; }
    public int getRapidite() { return rapidite; }
    public void setRapidite(int rapidite) { this.rapidite = rapidite; }
    public int getIntelligence() { return intelligence; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }
    public int getChance() { return chance; }
    public void setChance(int chance) { this.chance = chance; }
    public int getCharisme() { return charisme; }
    public void setCharisme(int charisme) { this.charisme = charisme; }
    public int getStatsPoints() { return statsPoints; }
    public void setStatsPoints(int statsPoints) { this.statsPoints = statsPoints; }
}