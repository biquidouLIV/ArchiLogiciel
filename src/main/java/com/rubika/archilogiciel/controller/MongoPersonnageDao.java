package com.rubika.archilogiciel.controller;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MongoPersonnageDao extends MongoRepository<PersonnageMongoModel, UUID> {

    PersonnageMongoModel findPersonnageMongoModelByName(String name);
    List<PersonnageMongoModel> findByOwnerId(String ownerId);

}
