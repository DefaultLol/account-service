package com.app.account.web;

import com.app.account.models.Account;
import com.app.account.service.AccountService;
import com.app.account.utils.AddCreditRequest;
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

    @GetMapping("/testing")
    public String test(){
        return "testing";
    }

    @PostMapping("/payment/addCredit")
    public String addCredit(@RequestBody AddCreditRequest request){
        service.addCredit(request);
        return "Credit Added";
    }

    @PostMapping("/payment/payBill")
    public String payBill(@RequestBody PaymentRequest request){
        service.payBill(request);
        return "Payment is successful";
    }

    @GetMapping("/payment/test")
    public String text(){
        return "testing";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        service.delete(id);
        return "Account with id : " + id + " deleted successfully";
    }
}
