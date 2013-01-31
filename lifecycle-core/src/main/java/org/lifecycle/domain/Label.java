package org.lifecycle.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.persistence.*;

@Entity
@Table(name = "labels")
public class Label {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;
    @Column(nullable = false)
    private String text;

    @JsonCreator
    public Label(@JsonProperty("id") Long id,@JsonProperty("text") String text) {
        this.id = id;
        this.text = text;
    }

    public Label() {
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(text == null) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;

        Label label = (Label) o;

        if (!text.equals(label.getText())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("text", text)
                .toString();
    }
}
