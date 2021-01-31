package com.app.account.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PaymentRequest {
    private String id;
    private double amount;
}
