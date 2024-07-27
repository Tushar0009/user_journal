package com.journalapp.journal.app.repository;

import com.journalapp.journal.app.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User , ObjectId> {
    User findByUsername(String username);

    void deleteByUsername(String username);
}
