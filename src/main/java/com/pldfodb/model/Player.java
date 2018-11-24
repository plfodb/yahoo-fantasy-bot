package com.pldfodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class Player {

    @NonNull private String name;
    @NonNull private String position;
}
