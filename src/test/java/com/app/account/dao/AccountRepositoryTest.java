package com.app.account.dao;

import com.app.account.models.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AccountRepositoryTest {
    private Account account;
    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp() {
        for(int i=0;i<5;i++){
            Date creationDate=new Date();
            account=new Account(String.valueOf(i),"458"+i,159,0.0,"19-7-2021",creationDate,"compte 3000",null);
            accountRepository.save(account);
        }
    }

    @Test
    public void test_save(){
        Account expected=accountRepository.save(account);
        assertNotNull(expected);
        assertEquals(account.getAmount(),expected.getAmount());
    }

    @Test
    public void test_findById(){
        Account account=new Account();
        Account created=accountRepository.save(account);
        Account expected=accountRepository.findById(created.getId()).orElse(null);
        assertNotNull(expected);
    }

    @Test
    public void test_findAll(){
        List<Account> accounts=accountRepository.findAll();
        System.out.println(accounts.size());
        assertTrue(accounts.size() > 0);
        assertEquals(accounts.get(0).getAmount(),159);
    }

    @Test
    public void test_delete(){
        List<Account> accounts=accountRepository.findAll();
        System.out.println(accounts.size());
        assertTrue(accounts.size() == 6);
        accountRepository.delete(accounts.get(0));
        accounts=accountRepository.findAll();
        assertTrue(accounts.size() == 5);
    }

}