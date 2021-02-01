package com.app.account.service;

import com.app.account.dao.AccountRepository;
import com.app.account.exception.AccountNotFoundException;
import com.app.account.models.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    private Account account;
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Before
    public void init(){
        /*Date creationDate=new Date();
        account=new Account("159","458",159,0.0,"19-7-2021",creationDate,"compte 3000");*/
    }


    @Test
    public void getAllAccounts(){
        List<Account> accounts=new ArrayList<>();
        accounts.add(account);
        when(accountRepository.findAll()).thenReturn(accounts);
        assertEquals(accounts,accountService.getAll());
    }

    @Test
    public void saveAccount(){
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        assertEquals(account.getId(),accountService.saveOrUpdate(account).getId());
    }

    @Test
    public void findById(){
        when(accountRepository.findById(any(String.class)))
                .thenReturn(Optional.of(account));
        assertEquals(accountService.findById("159"),account);
    }

    @Test(expected = AccountNotFoundException.class)
    public void findByIdException(){
        accountService.findById(any(String.class));
    }


    @Test(expected = AccountNotFoundException.class)
    public void deleteAccount(){
        accountService.delete("45146");
        verify(accountRepository).deleteById(any(String.class));
    }
}