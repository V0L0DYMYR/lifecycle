package org.lifecycle.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lifecycle.domain.Project;
import org.lifecycle.domain.Ticket;
import org.lifecycle.domain.User;
import org.lifecycle.transaction.TestUnderTransaction;

import java.util.List;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

public class TicketDaoTest extends TestUnderTransaction {

    TicketDao ticketDao;
    private UserDao userDao;

    @BeforeClass
    public static void init() throws Exception {
        initDB("conf/lifecycle-test.json", asList(Ticket.class, User.class, Project.class));
    }

    @Before
    public void setUp() {
        startSession();
        cleanTables(asSet("TICKETS", "LABELS"));
        ticketDao = new TicketDao(getSessionFactory());
        userDao = new UserDao(getSessionFactory());
    }

    @After
    public void tearDown() {
        rollbakAndCloseSession();
    }

    @Test
    public void givenProjectId_whenGETTikects_ReturnTicketOnlyFromThatproject() {
        User user = createUser();
        List<Ticket> tickets = ticketDao.findByUser(user);
    }

    private User createUser() {
        startSession();
        User user = userDao.saveOrUpdate(new User(null, "1", "user@gmail.com", "Ivan Ivanov", "pic", "en"));
        commitAndOpenNewSession();
        return user;
    }

    @Test
    public void givenTicketWithNotUniqueLabes_whenSaveTicket_LabelsAreUniqueInTicket() {
        ticketDao.saveOrUpdate(newTicket(asSet("sprint 1", "sprint 1", "sprint 2", "sprint 1", "sprint 2")));
        List<Ticket> all = ticketDao.findAll();
        Ticket ticket = all.get(0);
        assertThat(ticket.getLabels().size()).isEqualTo(2);
    }

    @Test
    public void givenTicketWithTwoLabels_afterSecondSaveWithOneLabel_WillHasOnlyOneLabel() {
        Ticket ticket = ticketDao.saveOrUpdate(newTicket(asSet("A", "B")));
        commitAndOpenNewSession();

        ticket = ticketDao.saveOrUpdate(new Ticket(ticket.getId(), "B Ticket", "desc", 1, asSet("A")));
        assertThat(ticket.getLabels().size()).isEqualTo(1);
    }

    @Test
    public void givenLabel_whenFindTickets_ReturnAllTicketsWithinAllRlatedLabels() {
        ticketDao.saveOrUpdate(newTicket(asSet("AAA")));
        ticketDao.saveOrUpdate(newTicket(asSet("AAA")));
        ticketDao.saveOrUpdate(newTicket(asSet("BBB")));
        commitAndBeginNewTransaction();

        List<Ticket> aTickets = ticketDao.findByLabel("AAA");

        assertThat(aTickets.size()).isEqualTo(2);
        Set<String> labels = aTickets.get(0).getLabels();
        assertThat(labels.size()).isEqualTo(1);
    }

    private Ticket newTicket() {
        return new Ticket(null, "A", "desc", 1, null);
    }

    private Ticket newTicket(Set<String> labels) {
        return new Ticket(null, "default title", "desc", 1, labels);
    }

}
