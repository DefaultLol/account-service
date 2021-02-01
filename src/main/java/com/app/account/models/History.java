package com.app.account.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
@Document(collection = "history")
public class History {
    @Id
    private String id;
    private Bill bill;
    private String creancier;
    private String accountID;
}
