package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    @Autowired
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
        return messageRepository.findById(id).orElseThrow();
    }
    public void deleteMessage(int id){
        messageRepository.deleteById(id);
    }

    public Message updateMessage(int id,Message newMessage){
        Message currentMessage = messageRepository.findById(id).orElseThrow();
        currentMessage.setPostedBy(newMessage.getPostedBy());
        currentMessage.setMessageText(newMessage.getMessageText());
        currentMessage.setTimePostedEpoch(newMessage.getTimePostedEpoch());
        messageRepository.save(currentMessage);
        Message updatedMessage =  messageRepository.findById(id).orElseThrow();
        return updatedMessage;
    }

    public List<Message> getAllMessagesbyUser(int userId) {
        return messageRepository.findAllByPostedby(userId);
    }


    
}

