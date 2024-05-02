package com.example.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.h2.util.IntArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import javafx.beans.binding.IntegerBinding;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> addNewUser(@RequestParam String username,@RequestParam String password){
        if(username.length() !=0 || password.length() >= 4){
            try{
                Account attempt = accountService.getUserbyUsername(username);
                return ResponseEntity.status(409).body(null);

            }catch(Exception e){
                Account newAccount = accountService.newUser(username,password);
                return ResponseEntity.status(200).body(newAccount);
            }
        }
        else{
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/login")
    public  @ResponseBody ResponseEntity<Account> logger(@RequestParam String username,@RequestParam String password){
        
        try{
            Account attempt = accountService.loginAccount(username,password);
            return ResponseEntity.status(200).body(attempt);
        }
        catch (EntityNotFoundException e) {
            // TODO: handle exception
            return ResponseEntity.status(401).body(null);
        }
        


    }

    @PostMapping("messages")
    public  @ResponseBody ResponseEntity<Message> messageGenerator(@RequestParam int postedBy, @RequestParam String messageText,  @RequestParam long timePostedEpoch){
        try{
            Account poster = accountService.getUserbyId(postedBy);
            if(messageText.length()>0 && messageText.length() <= 255){
                Message msg = new Message(postedBy,messageText,timePostedEpoch);
                messageService.addMessage(msg);
                return ResponseEntity.status(200).body(msg);
            }
            else{
                return ResponseEntity.status(400).body(null);
            }
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.status(400).body(null);
        }
    }


    @GetMapping("/messages")
    public  @ResponseBody ResponseEntity<List<Message>> messageFeed(){
        List<Message> msgs = messageService.getAllMessages();
        return ResponseEntity.status(200).body(msgs);
    }

    @GetMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Message> messageRetrieve(@PathVariable int message_id){
        try{
            Message m = messageService.getMessage(message_id);
            return ResponseEntity.status(200).body(m);
        }
        catch(Exception e){
            return ResponseEntity.status(200).body(null);
        }
    }

    @DeleteMapping("/messages/{message_id}")
    public  @ResponseBody ResponseEntity<Integer> messageDelete(@PathVariable int message_id){
        try{
            Message m = messageService.getMessage(message_id);
            messageService.deleteMessage(message_id);
            return ResponseEntity.status(200).body(1);
        }
        catch(Exception e){
            return ResponseEntity.status(200).body(null);
        }
    }

    @PatchMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> messageUpdate(@PathVariable int message_id,@RequestParam String newMessageText ){
        if(newMessageText.length() >0 && newMessageText.length() <= 255){
            try{
                Message m = messageService.updateMessage(message_id, newMessageText);
                return ResponseEntity.status(200).body(1);
            }catch(Exception e){
                return ResponseEntity.status(400).body(null);
            }
        }
        else{
            return ResponseEntity.status(400).body(null);
        }

    
    }

    @GetMapping("/accounts/{account_id}")
    public @ResponseBody ResponseEntity<List<Message>> userFeed(@PathVariable int account_id){
        List<Message> msgs = messageService.getAllMessagesbyUser(account_id);
        return ResponseEntity.status(200).body(msgs);
    }
}


