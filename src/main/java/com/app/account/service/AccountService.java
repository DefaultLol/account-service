package com.app.account.service;

import com.app.account.dao.AccountRepository;
import com.app.account.dao.HistoryRepository;
import com.app.account.exception.AccountAmountNotEnoughException;
import com.app.account.exception.AccountNotFoundException;
import com.app.account.models.Account;
import com.app.account.models.History;
import com.app.account.utils.AddCreditRequest;
import com.app.account.utils.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    @Autowired
    private HistoryRepository historyRepo;

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
        repo.save(account);
        
    }

    public Account saveOrUpdate(Account account){
        return repo.save(account);
    }

    public void payBill(PaymentRequest request){
        String accountId=request.getAccountID();
        Account account=repo.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account with id : "+accountId+" not found"));
        if(account.getAmount() - account.getCredit() < 0) throw new AccountAmountNotEnoughException("Not enough money in account");
        account.setAmount(account.getAmount() - request.getBill().getAmount());
        account.setCredit(account.getCredit() - request.getBill().getAmount());
        repo.save(account);
        History history=new History(null,request.getBill(),request.getCreancier(),request.getAccountID());
        historyRepo.save(history);
    }

    public void delete(String id){
        repo.findById(id).orElseThrow(()->new AccountNotFoundException("Account not found with id : "+id));
        repo.deleteById(id);
    }


}
