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

    public PersonnageMongoModel(Personnage personnage){
        this.id = UUID.randomUUID();
        this.name = personnage.getName();
        this.classe = personnage.getClasse();
        this.race = personnage.getRace();
        this.level = personnage.getLevel();
    }

}
