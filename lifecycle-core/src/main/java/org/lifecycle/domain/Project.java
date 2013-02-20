package org.lifecycle.domain;

import org.lifecycle.Utils;

import javax.persistence.*;
import java.util.Set;

import static org.lifecycle.Utils.initializeIfNull;

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
