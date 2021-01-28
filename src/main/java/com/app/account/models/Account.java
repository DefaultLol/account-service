package com.app.account.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
@Document(collection = "accounts")
public class Account {
    @Id
    private String id;
    private String accountNumber;
    @NotNull
    private double amount;
    private double credit;
    @Transient
    private String strCreationDate;
    private Date creationDate;
    @NotBlank
    private String accountType;
}
