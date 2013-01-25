package org.lifecycle.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;
    private String title;
    private Integer priority;

    @JsonCreator
    public Ticket(@JsonProperty("id") Long id, @JsonProperty("title") String title, @JsonProperty("priority") Integer priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
    }

    public Ticket(){}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPriority() {
        return priority;
    }
}
