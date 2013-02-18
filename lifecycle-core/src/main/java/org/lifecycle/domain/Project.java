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
    private User owner;
    @ManyToMany
    private Set<User> readers;
    @ManyToMany
    private Set<User> editors;

    public Project(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Project withReader(User user){
        this.readers = initializeIfNull(this.readers);
        this.readers.add(user);
        return this;
    }

    public Project withEditor(User user){
        this.editors = initializeIfNull(this.editors);
        this.editors.add(user);
        return this;
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

    public Set<User> getReaders() {
        return readers;
    }

    public Set<User> getEditors() {
        return editors;
    }
}
