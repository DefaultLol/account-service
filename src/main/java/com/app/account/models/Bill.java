package com.app.account.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Bill {
    private Long id;
    private double amount;
    private Date billingDate;
    private Date payedDate;
    private boolean payed;
    private boolean isBatched;
    private String codeCreance;

}
