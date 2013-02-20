package org.lifecycle.resource;

import org.junit.*;
import org.lifecycle.dao.TicketDao;
import org.lifecycle.domain.Project;
import org.lifecycle.domain.Ticket;
import org.lifecycle.domain.User;
import org.lifecycle.unitofwork.UnitOfWorkRule;

public class ProjectResourceTest {

    ProjectResource projectResource;

    @Rule
    public UnitOfWorkRule unitOfWorkRule = new UnitOfWorkRule("conf/lifecycle-test.json", User.class, Ticket.class, Project.class);

    @Before
    public void setUp(){
        TicketDao ticketDao = new TicketDao(unitOfWorkRule.getSessionFactory());
    }

    @Test
    public void givenProjectId_whenGETTikects_ReturnTicketOnlyFromThatproject(){

    }

    @Test
    public void givenUserId_whenGETProjects_ReturnProjectsAvailableForThatUser(){

    }
}
