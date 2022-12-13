package com.projet6opcr.paymybuddy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private String creditorEmail;
    private Double amount;
    private String reason;

}
