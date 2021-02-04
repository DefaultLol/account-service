package com.app.account.batch;

import com.app.account.models.Account;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class AccountProcessor implements ItemProcessor<Account, Account> {
    @Override
    public Account process(Account account) throws Exception {
        return account;
    }
}
