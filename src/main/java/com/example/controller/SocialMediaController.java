package com.example.controller;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import org.h2.util.IntArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
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
    // @Autowired
    // private AccountRepository accountRepository;
    // @Autowired
    // private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    // public SocialMediaController(AccountService accountService,MessageService messageService){
    //     this.accountService = new AccountService(accountRepository);
    //     this.messageService = new MessageService(messageRepository);
    // }

    @PostMapping("/register")
    public  ResponseEntity<?> addNewUser(@RequestBody Account accnt){
        String username = accnt.getUsername();
        String password = accnt.getPassword();
        if(!username.isBlank() && password.length() >= 4){
                Account attempt = accountService.getUserbyUsername(username);
                if(attempt != null)
                    return ResponseEntity.status(409).body(null);
                else{
                    Account newAccount = accountService.newUserAccount(accnt);
                    return ResponseEntity.status(200).body(newAccount);
                }
            }
        else{
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/login")
    public  @ResponseBody ResponseEntity<?> logger(@RequestBody Account account ){
        
        try{
            Account attempt = accountService.loginAccount(account);
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
    public @ResponseBody ResponseEntity<?> messageUpdate(@PathVariable int message_id,@RequestBody Message newMessageText ){
        if(newMessageText.getMessageText().length() >0 && newMessageText.getMessageText().length() <= 255){
            try{
                Message m = messageService.updateMessage(message_id, newMessageText.getMessageText());
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


