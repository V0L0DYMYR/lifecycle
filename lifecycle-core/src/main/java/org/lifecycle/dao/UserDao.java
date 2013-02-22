package org.lifecycle.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.lifecycle.domain.User;

public class UserDao extends AbstractDao<User> {

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User findBySecurityToken(String securityToken) {
        return singleResult(criteria().add(Restrictions.eq("securityToken", securityToken)));
    }
}
