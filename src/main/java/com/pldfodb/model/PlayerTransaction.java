package com.pldfodb.model;

import com.pldfodb.controller.model.yahoo.TransactionSourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class PlayerTransaction {

    @NonNull private TransactionSourceType source;
    @NonNull private TransactionSourceType destination;
}
