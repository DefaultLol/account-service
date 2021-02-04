package com.app.account.batch;

import com.app.account.dao.AccountRepository;
import com.app.account.models.Account;
import com.app.account.service.AccountService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountWriter implements ItemWriter<Account> {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public void write(List<? extends Account> list) throws Exception {
        for(Account cr:list){
            try{
                String accountType=cr.getAccountType();
                double amount=Double.parseDouble(accountType.split(" ")[1]);
                cr.setAmount(cr.getAmount() + amount);
            }catch(Exception err){
                System.out.println("Error parsing string");
            }
        }
    }
}
