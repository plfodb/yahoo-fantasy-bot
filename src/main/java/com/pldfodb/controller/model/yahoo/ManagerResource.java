package com.pldfodb.controller.model.yahoo;

import com.pldfodb.model.Manager;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlRootElement(name = "manager")
@XmlAccessorType(XmlAccessType.FIELD)
public class ManagerResource {

    @XmlElement(name = "manager_id")
    public Integer managerId;

    @XmlElement(name = "nickname")
    public String nickname;

    public Manager getManager() {
        return new Manager(managerId, nickname);
    }
}
