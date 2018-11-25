package com.pldfodb.controller.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum TransactionType {
    @XmlEnumValue("add") ADD,
    @XmlEnumValue("drop") DROP,
    @XmlEnumValue("add/drop") ADD_DROP,
    @XmlEnumValue("trade") TRADE
}
