package org.lifecycle.resource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lifecycle.dao.TicketDao;
import org.lifecycle.domain.Project;
import org.lifecycle.domain.Ticket;
import org.lifecycle.domain.User;
import org.lifecycle.transaction.TestUnderTransaction;

public class ProjectResourceTest extends TestUnderTransaction {

    ProjectResource projectResource;

    @BeforeClass
    public static void init() throws Exception{
        initDB("conf/lifecycle-test.json",  asList(Ticket.class, User.class, Project.class));
    }

    @Before
    public void setUp(){
        startSession();
        cleanTables(asSet("TICKETS", "LABELS", "USERS", "PROJECTS"));
        TicketDao ticketDao = new TicketDao(getSessionFactory());
        projectResource = new ProjectResource(ticketDao);
    }

    @After
    public void tearDown(){
        rollbakAndCloseSession();
    }

    @Test
    public void givenProjectId_whenGETTikects_ReturnTicketOnlyFromThatproject(){

    }

    @Test
    public void givenUserId_whenGETProjects_ReturnProjectsAvailableForThatUser(){

    }
}
