package org.lifecycle.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "PROJECTS")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "OWNER_ID")
    private Long ownerId;

    public Project(String name, Long ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }

    @JsonCreator
    public Project(@JsonProperty("id") Long id,
                   @JsonProperty("name") String name){
        this.id = id;
        this.name = name;
    }

    public Project(Project project, User owner){
        this.id = project.getId();
        this.name = getName();
        this.ownerId = owner.getId();
    }

    public Project() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

}
