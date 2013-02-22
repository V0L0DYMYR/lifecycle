package org.lifecycle.resource;

import com.yammer.dropwizard.hibernate.UnitOfWork;
import org.lifecycle.dao.TicketDao;
import org.lifecycle.domain.Ticket;
import org.lifecycle.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ticket")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {

    private Logger LOG = LoggerFactory.getLogger(TicketResource.class);
    private TicketDao ticketDao;

    public TicketResource(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @GET
    @UnitOfWork
    public List<Ticket> get(@Context User user) {
        LOG.info(user.toString());
        return ticketDao.findAll();
    }

    @GET
    @Path("/label/{label}")
    @UnitOfWork
    public List<Ticket> getWithLabel(@PathParam("label") String label) {
        return ticketDao.findByLabel(label);
    }

    @POST
    @UnitOfWork
    public Ticket save(Ticket ticket) {
        return ticketDao.saveOrUpdate(ticket);
    }

    @PUT
    @Path("{id}")
    @UnitOfWork
    public Ticket amend(Ticket ticket) {
        return ticketDao.saveOrUpdate(ticket);
    }

    @DELETE
    @Path("{id}")
    @UnitOfWork
    public void delete(@PathParam("id") Long id) {
        ticketDao.delete(id);
    }
}
