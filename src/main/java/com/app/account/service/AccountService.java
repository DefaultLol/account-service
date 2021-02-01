package com.app.account.service;

import com.app.account.dao.AccountRepository;
import com.app.account.exception.AccountAmountNotEnoughException;
import com.app.account.exception.AccountNotFoundException;
import com.app.account.models.Account;
import com.app.account.utils.AddCreditRequest;
import com.app.account.utils.PaymentRequest;
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

    public void addCredit(AddCreditRequest addCreditRequest){
        String accountId=addCreditRequest.getAccountID();
        Account account=repo.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account with id : "+accountId+" not found"));
        double credit = addCreditRequest.getCredit();
        if(account.getAmount() - (account.getCredit()+credit) < 0) throw new AccountAmountNotEnoughException("Not enough amount");
        account.setCredit(account.getCredit()+credit);
    }

    public Account saveOrUpdate(Account account){
        return repo.save(account);
    }

    public void delete(String id){
        repo.findById(id).orElseThrow(()->new AccountNotFoundException("Account not found with id : "+id));
        repo.deleteById(id);
    }


}
