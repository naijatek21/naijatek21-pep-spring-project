package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account newUser(String username, String password){
        Account user  = new Account(username, password);
        accountRepository.save(user);
        return user;
    }


    public Account loginAccount(String username, String password){
        Account a = accountRepository.findByUsernameAndPassword(username, password);
        return a;
    }

    public Account getUserbyId(int userId){
        return accountRepository.getById(userId);
    }

    public Account getUserbyUsername(String username){
        return accountRepository.findByUsername( username);
    }


}
