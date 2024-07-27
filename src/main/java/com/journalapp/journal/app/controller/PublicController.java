package com.journalapp.journal.app.controller;

import com.journalapp.journal.app.entity.User;
import com.journalapp.journal.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        userService.saveNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
