package com.pldfodb.controller.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerNameResource {

    @XmlElement(name = "full")
    private String fullName;
}
