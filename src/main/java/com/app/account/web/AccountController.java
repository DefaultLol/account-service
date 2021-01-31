package com.app.account.web;

import com.app.account.models.Account;
import com.app.account.service.AccountService;
import com.app.account.utils.AddCreditRequest;
import com.app.account.utils.JwtHandler;
import com.app.account.utils.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    private AccountService service;

    @GetMapping("/")
    public List<Account> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Account findAccount(@PathVariable String id){
        return service.findById(id);
    }

    @PostMapping("/save")
    public Account save(@Valid @RequestBody Account account){
        return service.saveOrUpdate(account);
    }

    @PostMapping("/addCredit")
    public String payWithAccount(@RequestBody AddCreditRequest addCreditRequest){
        service.addCredit(addCreditRequest);
        return "Success";
    }

    @GetMapping("/testing")
    public String test(){
        return JwtHandler.token;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        service.delete(id);
        return "Account with id : " + id + " deleted successfully";
    }
}
