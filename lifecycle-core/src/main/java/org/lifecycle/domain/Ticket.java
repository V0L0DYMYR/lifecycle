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

    @JsonCreator
    public Ticket(@JsonProperty("id") Long id, @JsonProperty("title") String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
