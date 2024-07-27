package com.journalapp.journal.app.controller;

import com.journalapp.journal.app.entity.User;
import com.journalapp.journal.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUser = userService.getUsers();
        if(allUser != null && !allUser.isEmpty()){
            return new ResponseEntity<>(allUser , HttpStatus.FOUND);
        }
        return null;
    }

    @PostMapping("/create-admin")
    public void createAdminUser(@RequestBody User user){
        userService.saveAdminUser(user);
    }
}
