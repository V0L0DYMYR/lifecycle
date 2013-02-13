package org.lifecycle.dao;

import org.hibernate.SessionFactory;
import org.lifecycle.domain.User;

public class UserDao extends AbstractDao<User> {

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
