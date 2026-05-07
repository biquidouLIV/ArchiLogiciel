package com.rubika.archilogiciel.controller;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserDao extends MongoRepository<UserMongoModel, String> {
    UserMongoModel findByLoginAndPassword(String login, String password);
}
