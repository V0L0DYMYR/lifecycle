package org.lifecycle.domain;

import org.lifecycle.Utils;

import javax.persistence.*;
import java.util.Set;

import static org.lifecycle.Utils.initializeIfNull;

@Entity
@Table(name = "PROJECTS")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private User owner;

    public Project(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

}
