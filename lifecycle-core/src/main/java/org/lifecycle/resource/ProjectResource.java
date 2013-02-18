package org.lifecycle.resource;

import org.lifecycle.dao.TicketDao;

import javax.ws.rs.Path;

@Path("/project")
public class ProjectResource {

    public ProjectResource(TicketDao ticketDao) {

    }
}
