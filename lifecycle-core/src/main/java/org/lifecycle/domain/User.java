package org.lifecycle.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Set;

/*
9223372036854775807

 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "GOOGLE_ID")
    private String googleId;
    private String email;
    @Column(name = "FULL_NAME")
    private String fullName;
    private String picture;
    private String locale;
    @ManyToMany
    private Set<Project> projects;

    @JsonCreator
    public User(@JsonProperty("ID") Long id,
                @JsonProperty("GOOGLE_ID") String googleId,
                @JsonProperty("EMAIL") String email,
                @JsonProperty("FULL_NAME") String fullName,
                @JsonProperty("PICTURE") String picture,
                @JsonProperty("LOCALE") String locale){
        this.id = id;
        this.googleId = googleId;
        this.email = email;
        this.fullName = fullName;
        this.picture = picture;
        this.locale = locale;
    }

    public Long getId() {
        return id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPicture() {
        return picture;
    }

    public String getLocale() {
        return locale;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("full name", fullName)
                .toString();
    }
}
