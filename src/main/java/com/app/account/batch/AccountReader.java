package com.app.account.batch;

import com.app.account.dao.AccountRepository;
import com.app.account.models.Account;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class AccountReader implements ItemReader<Account> {
    @Autowired
    private AccountRepository accountRepository;
    private int nextIndex;
    private List<Account> accounts;

    @PostConstruct
    public void init(){
        accounts=accountRepository.findAll();
    }

    @Override
    public Account read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Account account = null;
        if (nextIndex < accounts.size()) {
            account = accounts.get(nextIndex);
            nextIndex++;
        }
        else {
            nextIndex = 0;
        }

        return account;
    }
}
