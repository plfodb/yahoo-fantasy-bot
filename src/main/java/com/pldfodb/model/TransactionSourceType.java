package com.pldfodb.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
public enum TransactionSourceType {
    @XmlEnumValue("team") TEAM,
    @XmlEnumValue("waivers") WAIVERS,
    @XmlEnumValue("freeagents") FREE_AGENTS
}
