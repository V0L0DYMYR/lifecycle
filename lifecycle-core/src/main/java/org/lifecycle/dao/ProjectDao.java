package org.lifecycle.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.lifecycle.domain.Project;
import org.lifecycle.domain.User;

import java.util.List;

public class ProjectDao extends AbstractDao<Project> {

    public ProjectDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Project> findByUser(User user) {
        return list(criteria()
                .add(Restrictions.eq("ownerId", user.getId())));
    }
}
