package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account newUser(String username, String password){
        return null;

    }


    public Account loginAccount(String username, String password){
        return null;
    }

    public Account getUserbyId(int userId){
        return accountRepository.getById(userId);
    }


}
