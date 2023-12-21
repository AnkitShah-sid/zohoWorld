package com.spring.mongo.demo.repository;

import com.spring.mongo.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    @Query
    Optional<User> findById(String id);

}
