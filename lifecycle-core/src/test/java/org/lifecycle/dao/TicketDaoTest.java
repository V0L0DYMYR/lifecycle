package org.lifecycle.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lifecycle.domain.Label;
import org.lifecycle.domain.Ticket;
import org.lifecycle.persistence.TicketDao;
import org.lifecycle.transaction.TestUnderTransaction;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class TicketDaoTest extends TestUnderTransaction{

    TicketDao dao;

    @Before
    public void setUp() throws Exception{
        startTransaction("conf/lifecycle-test.json");
        dao = new TicketDao(getSessionFactory());
    }

    @Test
    public void givenTicketWithNotUniqueLabes_whenSaveTicket_LabelsAreUniqueInTicket() {
        dao.saveOrUpdate(new Ticket(null, "First Ticket", 1)
                .withLabel(new Label(null, "sprint 1"))
                .withLabel(new Label(null, "sprint 1"))
                .withLabel(new Label(null, "sprint 2"))
                .withLabel(new Label(null, "sprint 1"))
                .withLabel(new Label(null, "sprint 2")));
        List<Ticket> all = dao.findAll();
        Ticket ticket = all.get(0);
        assertThat(ticket.getLabels().size()).isEqualTo(2);
    }

    @Test
    public void findTicketsByLabel(){
        Ticket ticket = dao.saveOrUpdate(new Ticket(null, "A", 1)
                .withLabel(new Label(null, "Label 1")));
        Label label = ticket.getLabels().iterator().next();
        dao.findByLabel(label);
    }

    @After
    public void tearDown(){
        endTransaction();
    }

}
