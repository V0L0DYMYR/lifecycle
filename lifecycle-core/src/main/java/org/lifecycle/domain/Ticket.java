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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private Integer priority;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="LABELS",
            joinColumns=@JoinColumn(name="TICKET_ID"))
    @Column(name = "label", length = 255, columnDefinition = "bpchar")
    private Set<String> labels;

    @JsonCreator
    public Ticket(@JsonProperty("ID") Long id,
                  @JsonProperty("TITLE") String title,
                  @JsonProperty("PRIORITY") Integer priority,
                  @JsonProperty("LABELS") Set<String> labels) {
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

    public Set<String> getLabels() {
        return returnNotNull(labels);
    }

    private <T> Set<T> returnNotNull(Set<T> labels) {
        return labels == null? Collections.<T>emptySet():labels;
    }

    public Ticket withLabel(String label){
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
