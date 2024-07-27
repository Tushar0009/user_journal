package com.journalapp.journal.app.controller;

import com.journalapp.journal.app.entity.JournalEntry;
import com.journalapp.journal.app.entity.User;
import com.journalapp.journal.app.services.JournalEntryService;
import com.journalapp.journal.app.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllEntry(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> journalEntryList = user.getJournalEntries();

        if(journalEntryList != null && !journalEntryList.isEmpty()){
            return new ResponseEntity<>(journalEntryList , HttpStatus.OK);
        }
        return new ResponseEntity<>(journalEntryList , HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            journalEntryService.saveEntry(journalEntry , authentication.getName());
            return new ResponseEntity<>(journalEntry , HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<JournalEntry> entryList = user.getJournalEntries().stream().filter(e -> e.getId().equals(myId)).toList();
        if(!entryList.isEmpty()){
            var journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry , HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        boolean response = journalEntryService.deleteById(myId , user.getUsername());
        if (response)return new ResponseEntity<>(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
