package org.lifecycle.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.lifecycle.Utils.initializeIfNull;
import static org.lifecycle.Utils.returnNotNull;

@Entity
@Table(name = "TICKETS")
public class Ticket {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "PROJECT_ID")
    private Long projectId;
    @Column(nullable = false)
    private String title;
    private String description;
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
                  @JsonProperty("DESCRIPTION") String description,
                  @JsonProperty("PRIORITY") Integer priority,
                  @JsonProperty("PROJECT_ID") Long projectId,
                  @JsonProperty("LABELS") Set<String> labels) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.priority = priority;
        this.labels = initializeIfNull(labels);
        this.description = description;
    }

    public Ticket(){}

    public Long getProjectId() {
        return projectId;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
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


    public Ticket withLabel(String label){
        labels = initializeIfNull(labels);
        labels.add(label);
        return this;
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
