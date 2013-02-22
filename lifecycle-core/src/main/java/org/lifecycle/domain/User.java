package org.lifecycle.domain;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "GOOGLE_ID")
    private String googleId;
    private String email;
    @Column(name = "FULL_NAME")
    private String fullName;
    private String picture;
    private String locale;
    @ManyToMany
    @JoinTable(name = "USERS_PROJECTS",
            joinColumns = {@JoinColumn(name = "USERS_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PROJECTS_ID")}
    )
    private Set<Project> projects;
    @Column(name = "SECURITY_TOKEN")
    private String securityToken;

    public User(Long id, String googleId, String email, String fullName, String picture, String locale) {
        this.id = id;
        this.googleId = googleId;
        this.email = email;
        this.fullName = fullName;
        this.picture = picture;
        this.locale = locale;
    }

    public User(User other, String securityToken) {
        this(other);
        this.securityToken = securityToken;
    }

    public User(User other) {
        this.id = other.id;
        this.googleId = other.googleId;
        this.email = other.email;
        this.fullName = other.fullName;
        this.picture = other.picture;
        this.locale = other.locale;
        this.projects = other.projects;
        this.securityToken = other.securityToken;
    }

    public User() {}

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

    public String getSecurityToken() {
        return securityToken;
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
