package com.example.springbootblog.services;

import com.example.springbootblog.models.Account;
import com.example.springbootblog.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    // method for new_post on the postController
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findOneByEmail(email);
    }
}
