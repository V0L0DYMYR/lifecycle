package org.lifecycle.dao;

import org.hibernate.SessionFactory;
import org.lifecycle.domain.Project;

public class ProjectDao extends AbstractDao<Project> {

    public ProjectDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
