package com.pldfodb.controller.model.slack;

import lombok.*;
import org.codehaus.jackson.annotate.JsonProperty;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class UrlVerificationEvent {

    @JsonProperty("token")
    private String token;

    @JsonProperty("challenge")
    private String challenge;

    @JsonProperty("type")
    private String type;
}
