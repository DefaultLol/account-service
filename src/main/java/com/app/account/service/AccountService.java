package com.app.account.service;

import com.app.account.dao.AccountRepository;
import com.app.account.exception.AccountNotFoundException;
import com.app.account.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    public List<Account> getAll(){
        List<Account> accounts=repo.findAll();
        return accounts;
    }

    public Account findById(String id){
        Account account=repo.findById(id).orElseThrow(()->new AccountNotFoundException("Account not found with id : "+id));
        return account;
    }

    public Account saveOrUpdate(Account account){
        return repo.save(account);
    }

    public void delete(String id){
        repo.findById(id).orElseThrow(()->new AccountNotFoundException("Account not found with id : "+id));
        repo.deleteById(id);
    }


}
