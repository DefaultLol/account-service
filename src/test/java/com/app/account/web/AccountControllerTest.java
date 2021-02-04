package com.app.account.web;

import com.app.account.dao.AccountRepository;
import com.app.account.exception.AccountNotFoundException;
import com.app.account.filter.SecurityFilter;
import com.app.account.models.Account;
import com.app.account.models.Bill;
import com.app.account.service.AccountService;
import com.app.account.utils.AddCreditRequest;
import com.app.account.utils.PaymentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public void testListAccountsNotAuthorized() throws Exception{
        List<Account> accounts=new ArrayList<>();
        accounts.add(new Account("159","451",158.0,0.0,"14-7-2021",new Date(),"compte 30000",null));
        when(accountService.getAll()).thenReturn(accounts);

        String url="/api/account/";
        System.out.println(mockMvc);
        mockMvc.perform(get(url)).andExpect(status().isUnauthorized());
    }

    @Test
    public void testListAccounts() throws Exception {
        List<Account> accounts=new ArrayList<>();
        accounts.add(new Account("159","451",158.0,0.0,"14-7-2021",new Date(),"compte 30000",null));
        accounts.add(new Account("179","487",157.0,0.0,"15-8-2023",new Date(),"compte 40000",null));
        when(accountService.getAll()).thenReturn(accounts);

        String url="https://ensaspay-zuul-gateway.herokuapp.com/api/account/";
        System.out.println(mockMvc);
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    public void testAddAccount() throws Exception {
        Account account =new Account("4865","451",158.0,0.0,"14-7-2021",new Date(),"compte 30000",null);
        when(accountService.saveOrUpdate(account)).thenReturn(account);
        String url="https://ensaspay-zuul-gateway.herokuapp.com/api/account/save";
        MvcResult mvcResult=mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk()).andReturn();

        String response=mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(account),response);
    }

    /*@Test
    public void testAmountShouldNotBeNull() throws Exception {
        Account account =new Account();
        account.setAccountNumber("5146");
        account.setAccountType("compt 3000");
        String url="https://ensaspay-zuul-gateway.herokuapp.com/api/account/save";
        mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isInternalServerError());

        Mockito.verify(accountService,times(0)).saveOrUpdate(account);
    }*/

    @Test
    public void testFindAccountById() throws Exception {
        String accountId="4865";
        Account account =new Account("4865","451",158.0,0.0,"14-7-2021",new Date(),"compte 30000",null);
        when(accountService.findById(accountId)).thenReturn(account);

        String url="https://ensaspay-zuul-gateway.herokuapp.com/api/account/" + accountId;
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        assertEquals(objectMapper.writeValueAsString(account),mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPaymentRequest() throws Exception {
        Bill bill=new Bill();
        bill.setAmount(158.0);
        bill.setId(8465L);
        bill.setPayed(false);
        bill.setBillingDate(new Date());
        PaymentRequest request=new PaymentRequest(bill,"iam","8465");
        doNothing().when(accountService).payBill(request);
        String url="https://ensaspay-zuul-gateway.herokuapp.com/api/account/payment/payBill";

        MvcResult mvcResult=mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn();

        String response=mvcResult.getResponse().getContentAsString();

        assertEquals(response,"Payment is successful");

    }

    @Test
    public void testAddCredit() throws Exception {
        AddCreditRequest request=new AddCreditRequest("846",79.0);
        doNothing().when(accountService).addCredit(request);
        String url="https://ensaspay-zuul-gateway.herokuapp.com/api/account/payment/addCredit";

        MvcResult mvcResult=mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn();

        String response=mvcResult.getResponse().getContentAsString();

        assertEquals(response,"Credit Added");
    }

    @Test
    public void testAccountByIdNotFound() throws Exception {
        String accountId="4865";
        when(accountService.findById(accountId)).thenThrow(AccountNotFoundException.class);

        String url="https://ensaspay-zuul-gateway.herokuapp.com/api/account/" + accountId;
        mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAccount() throws Exception {
        String accountId="159";
        doNothing().when(accountService).delete(accountId);

        String url="https://ensaspay-zuul-gateway.herokuapp.com/api/account/" + accountId;

        mockMvc.perform(delete(url)).andExpect(status().isOk());
    }
}