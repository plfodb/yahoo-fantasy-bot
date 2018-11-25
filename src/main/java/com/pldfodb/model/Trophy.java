package com.pldfodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Trophy {

    @NotNull private String id;
    @NotNull private String title;
    private int grade;
    private String description;

    private static final Set<String> UNIQUE_TROPHIES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("all-time-single-week-high-point")));

    public boolean isUnique() {
        return UNIQUE_TROPHIES.contains(id);
    }
}
