package org.lifecycle.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;
    private String title;
    private Integer priority;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tickets_labels",
            joinColumns={@JoinColumn(name="ticket_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="label_id", referencedColumnName="id")})
    private Set<Label> labels;

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

    public Set<Label> getLabels() {
        return labels;
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
