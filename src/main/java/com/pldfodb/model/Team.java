package com.pldfodb.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Team {

    @NonNull private Integer id;
    @NonNull private String name;
    @NonNull private boolean clinchedPlayoffs;
    @NonNull List<Manager> managers;
}
