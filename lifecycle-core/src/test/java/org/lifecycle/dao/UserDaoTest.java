package org.lifecycle.dao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.lifecycle.domain.Project;
import org.lifecycle.domain.Ticket;
import org.lifecycle.domain.User;
import org.lifecycle.unitofwork.UnitOfWorkRule;

public class UserDaoTest {

    UserDao userDao;

    @Rule
    public UnitOfWorkRule unitOfWorkRule = new UnitOfWorkRule("conf/lifecycle-test.json", User.class, Ticket.class, Project.class);

    @Before
    public void setUp(){
        userDao = new UserDao(unitOfWorkRule.getSessionFactory());
    }

    @Test
    public void givenValidUser_whenSave_willAppearInDb(){
        User user = new User(null, "1", "user@gmail.com", "Ivan Ivanov", "http://picassa.com/1", "en");
        userDao.saveOrUpdate(user);
    }
}
