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
    UserDao userDao;
    ProjectDao projectDao;

    @BeforeClass
    public static void init() throws Exception {
        initDB("conf/lifecycle-test.json", asList(Ticket.class, User.class, Project.class));
    }

    @Before
    public void setUp() {
        startSession();
        cleanTables(asSet("TICKETS", "LABELS", "PROJECTS", "USERS"));
        ticketDao = new TicketDao(getSessionFactory());
        userDao = new UserDao(getSessionFactory());
        projectDao = new ProjectDao(getSessionFactory());
    }

    @After
    public void tearDown() {
        commitAndCloseSession();
    }

    @Test
    public void givenProjectId_whenGETTikects_ReturnTicketOnlyFromThatProject() {
        Long projectId = createProjectWithUser();
        createTicket(projectId);
        createTicket(projectId);
        commitAndOpenNewSession();
        Long projectId_2 = createProjectWithUser();
        createTicket(projectId_2);
        commitAndOpenNewSession();
        List<Ticket> tickets = ticketDao.findByProject(projectId);
        assertThat(tickets.size()).isEqualTo(2);
    }

    @Test
    public void givenUserId_whenGETTickets_ReturnProjectsOnlyThatUsers() {
        User user = createUser();
        createProject("A project", user);
        createProject("B project", user);
        commitAndOpenNewSession();

        User user_2 = createUser();
        createProject("C project", user_2);
        commitAndOpenNewSession();
        List<Ticket> tickets = ticketDao.findByUser(user);
        assertThat(tickets.size()).isEqualTo(2);
    }

    private User createUser() {
        startSession();
        User user = userDao.saveOrUpdate(newUser());
        commitAndOpenNewSession();
        return user;
    }

    private User newUser() {
        return new User(null, "1", "user@gmail.com", "Ivan Ivanov", "pic", "en");
    }

    @Test
    public void givenTicketWithNotUniqueLabes_whenSaveTicket_LabelsAreUniqueInTicket() {
        Long projectId = createProjectWithUser();
        createTicket(projectId, asSet("sprint 1", "sprint 1", "sprint 2", "sprint 1", "sprint 2"));
        List<Ticket> all = ticketDao.findAll();
        Ticket ticket = all.get(0);
        assertThat(ticket.getLabels().size()).isEqualTo(2);
    }

    @Test
    public void givenTicketWithTwoLabels_afterSecondSaveWithOneLabel_WillHasOnlyOneLabel() {
        Long projectId = createProjectWithUser();
        Ticket ticket = ticketDao.saveOrUpdate(newTicket(projectId, asSet("A", "B")));
        commitAndOpenNewSession();

        ticket = ticketDao.saveOrUpdate(new Ticket(ticket.getId(), "B Ticket", "desc", 1, projectId, asSet("A")));
        assertThat(ticket.getLabels().size()).isEqualTo(1);
    }

    private Long createProject(String name, User user) {
        return projectDao.saveOrUpdate(new Project(name, user)).getId();
    }

    @Test
    public void givenLabel_whenFindTickets_ReturnAllTicketsWithinAllRlatedLabels() {
        Long projectId = createProjectWithUser();
        createTicket(projectId, asSet("AAA"));
        createTicket(projectId, asSet("AAA"));
        createTicket(projectId, asSet("BBB"));
        commitAndBeginNewTransaction();

        List<Ticket> aTickets = ticketDao.findByLabel("AAA");

        assertThat(aTickets.size()).isEqualTo(2);
        Set<String> labels = aTickets.get(0).getLabels();
        assertThat(labels.size()).isEqualTo(1);
    }

    private Long createProjectWithUser() {
        User user = createUser();
        return projectDao.saveOrUpdate(new Project("project name", user)).getId();
    }

    private Ticket createTicket(Long projectId) {
        return createTicket(projectId, null);
    }

    private Ticket createTicket(Long projectId, Set<String> labels) {
        return ticketDao.saveOrUpdate(newTicket(projectId, labels));
    }

    private Ticket newTicket(Long projectId, Set<String> labels) {
        return new Ticket(null, "default title", "desc", 1, projectId, labels);
    }

}
