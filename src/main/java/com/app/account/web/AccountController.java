package com.app.account.web;

import com.app.account.models.Account;
import com.app.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    private AccountService service;

    @GetMapping("")
    public List<Account> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Account findAccount(@PathVariable String id){
        return service.findById(id);
    }

    @PostMapping("/save")
    public Account save(@RequestBody Account account){
        return service.saveOrUpdate(account);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        service.delete(id);
        return "Account with id : " + id + " deleted successfully";
    }
}
