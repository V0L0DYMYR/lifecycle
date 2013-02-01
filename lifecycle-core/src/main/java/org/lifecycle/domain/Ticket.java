package org.lifecycle.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TICKETS")
public class Ticket {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;
    private String title;
    private Integer priority;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TICKETS_LABELS",
            joinColumns={@JoinColumn(name="TICKET_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="LABEL_ID", referencedColumnName="ID")})
    private Set<Label> labels;

    @JsonCreator
    public Ticket(@JsonProperty("ID") Long id,
                  @JsonProperty("TITLE") String title,
                  @JsonProperty("PRIORITY") Integer priority,
                  @JsonProperty("LABELS") Set<Label> labels) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.labels = initializeIfNull(labels);
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

    public Set<Label> getLabels() {
        return returnNotNull(labels);
    }

    private Set<Label> returnNotNull(Set<Label> labels) {
        return labels == null? Collections.<Label>emptySet():labels;
    }

    public Ticket withLabel(Label label){
        labels = initializeIfNull(labels);
        labels.add(label);
        return this;
    }

    private <T> Set<T> initializeIfNull(Set<T> set) {
        return set == null ? new HashSet<T>(): set;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("priority", priority)
                .add("labels", labels)
                .toString();
    }
}
