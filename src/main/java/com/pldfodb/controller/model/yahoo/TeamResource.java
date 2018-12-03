package com.pldfodb.controller.model.yahoo;

import com.pldfodb.model.Manager;
import com.pldfodb.model.Team;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamResource {

    @XmlElement(name = "team_id")
    protected Integer teamId;

    @XmlElement(name = "name")
    protected String name;

    @XmlElement(name = "clinched_playoffs")
    protected Integer clinchedPlayoffs;

    @XmlElementWrapper(name = "managers")
    @XmlElement(name = "manager")
    protected List<ManagerResource> managerResources = new ArrayList<>();

    public Team getTeam() {
        List<Manager> managers = managerResources.stream().map(ManagerResource::getManager).collect(Collectors.toList());
        return new Team(teamId, name, clinchedPlayoffs != null && clinchedPlayoffs > 0 ? true : false, managers);
    }
}
