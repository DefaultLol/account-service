package com.app.account.web;

import com.app.account.dao.AccountRepository;
import com.app.account.exception.AccountNotFoundException;
import com.app.account.models.Account;
import com.app.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Test
    public void testListAccounts() throws Exception {
        /*List<Account> accounts=new ArrayList<>();
        accounts.add(new Account("159","451",158.0,0.0,"14-7-2021",new Date(),"compte 30000"));
        accounts.add(new Account("179","487",157.0,0.0,"15-8-2023",new Date(),"compte 40000"));
        when(accountService.getAll()).thenReturn(accounts);

        String url="/api/account/";
        System.out.println(mockMvc);
        mockMvc.perform(get(url)).andExpect(status().isOk());*/
    }

    @Test
    public void testAddAccount() throws Exception {
        /*Account account =new Account("4865","451",158.0,0.0,"14-7-2021",new Date(),"compte 30000");
        when(accountService.saveOrUpdate(account)).thenReturn(account);
        String url="/api/account/save";
        MvcResult mvcResult=mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk()).andReturn();

        String response=mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(account),response);*/
    }

    @Test
    public void testAmountShouldNotBeNull() throws Exception {
        Account account =new Account();
        account.setAccountNumber("5146");
        account.setAccountType("");
        String url="/api/account/save";
        mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isInternalServerError());

        Mockito.verify(accountService,times(0)).saveOrUpdate(account);
    }

    @Test
    public void testFindAccountById() throws Exception {
        /*String accountId="4865";
        Account account =new Account("4865","451",158.0,0.0,"14-7-2021",new Date(),"compte 30000");
        when(accountService.findById(accountId)).thenReturn(account);

        String url="/api/account/" + accountId;
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        assertEquals(objectMapper.writeValueAsString(account),mvcResult.getResponse().getContentAsString());*/
    }

    @Test
    public void testAccountByIdNotFound() throws Exception {
        String accountId="4865";
        when(accountService.findById(accountId)).thenThrow(AccountNotFoundException.class);

        String url="/api/account/" + accountId;
        mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAccount() throws Exception {
        String accountId="159";
        doNothing().when(accountService).delete(accountId);

        String url="/api/account/" + accountId;

        mockMvc.perform(delete(url)).andExpect(status().isOk());
    }
}