package com.journalapp.journal.app.services;

import com.journalapp.journal.app.entity.User;
import com.journalapp.journal.app.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    final private  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAdminUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER" , "ADMIN"));
        userRepo.save(user);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    public List<User> getUsers(){
        return userRepo.findAll();
    }

    public Optional<User> getUserById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepo.deleteById(id);
    }

    public User findByUserName(String username) {
        return userRepo.findByUsername(username);
    }
}
