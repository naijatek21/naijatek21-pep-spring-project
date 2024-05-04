package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }
    public Message addMessage(Message msg) {
        return messageRepository.save(msg);
    }

    public Message getMessage(int id) {
        return messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found with id " + id));
    }
    public void deleteMessage(int id){
        messageRepository.deleteById(id);
    }

    public Message updateMessage(int id,String newMessageText){
        Message currentMessage = messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found with id " + id));
        currentMessage.setMessageText(newMessageText);
        messageRepository.save(currentMessage);
        Message updatedMessage =  messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found with id " + id));
        return updatedMessage;
    }

    public List<Message> getAllMessagesbyUser(int userId) {
        return messageRepository.findAllByUser(userId);
    }


    
}

