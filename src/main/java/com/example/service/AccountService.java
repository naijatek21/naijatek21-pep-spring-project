package com.example.service;

import javax.security.auth.login.AccountNotFoundException;

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


    public Account newUserAccount(Account account){
        accountRepository.save(account);
        return accountRepository.findByUsername(account.getUsername());
    }
    public Account getUserbyId(int userId){
        return accountRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }

    public Account loginAccount(Account account){
        Account a = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        return a;
    }

    public Account getUserbyUsername(String username){
        return accountRepository.findByUsername(username);
    }


}
