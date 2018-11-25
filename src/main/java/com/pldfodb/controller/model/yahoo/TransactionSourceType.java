package com.pldfodb.controller.model.yahoo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
public enum TransactionSourceType {
    @XmlEnumValue("team") TEAM("T"),
    @XmlEnumValue("waivers") WAIVERS("W"),
    @XmlEnumValue("freeagents") FREE_AGENTS("FA");

    private String displayText;

    private TransactionSourceType(String displayText) {
        this.displayText = displayText;
    }

    public String toString() {
        return displayText;
    }
}
