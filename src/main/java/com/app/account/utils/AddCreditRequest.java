package com.app.account.utils;

import lombok.Data;

@Data
public class AddCreditRequest {
    private String accountID;
    private double credit;

}
