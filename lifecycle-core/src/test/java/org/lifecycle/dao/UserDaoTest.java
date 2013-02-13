package org.lifecycle.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lifecycle.domain.Ticket;
import org.lifecycle.domain.User;
import org.lifecycle.transaction.TestUnderTransaction;

public class UserDaoTest extends TestUnderTransaction{

    UserDao userDao;

    @BeforeClass
    public static void init() throws Exception{
        initDB("conf/lifecycle-test.json",  asList(Ticket.class, User.class));
    }

    @Before
    public void setUp(){
        startSession();
        cleanTables(asSet("TICKETS", "LABELS", "USERS"));
        userDao = new UserDao(getSessionFactory());
    }

    @Test
    public void givenValidUser_whenSave_willAppearInDb(){
        User user = new User(null, "1", "user@gmail.com", "Ivan Ivanov", "http://picassa.com/1", "en");
        userDao.saveOrUpdate(user);
    }

    @After
    public void tearDown(){
          rollbakAndCloseSession();
    }
}
