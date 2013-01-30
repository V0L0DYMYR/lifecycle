package org.lifecycle.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lifecycle.domain.Ticket;
import org.lifecycle.persistence.TicketDao;
import org.lifecycle.transaction.TestUnderTransaction;

import java.util.List;

public class TicketDaoTest extends TestUnderTransaction{

    TicketDao dao;

    @Before
    public void setUp() throws Exception{
        startTransaction();
        dao = new TicketDao(getSessionFactory());
    }

    @Test
    public void saveTickets() {
        List<Ticket> all = dao.findAll();
        System.out.println(all);
    }

    @After
    public void tearDown(){
        endTransaction();
    }

}
