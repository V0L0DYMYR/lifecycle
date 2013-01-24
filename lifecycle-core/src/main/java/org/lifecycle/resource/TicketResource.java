package org.lifecycle.resource;

import com.yammer.dropwizard.hibernate.UnitOfWork;
import org.lifecycle.domain.Ticket;
import org.lifecycle.persistence.TicketDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ticket")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {

    private TicketDao ticketDao;

    public TicketResource(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @GET
    @UnitOfWork
    public List<Ticket> get(){
        return ticketDao.findAll();
    }

    @POST
    @UnitOfWork
    public void save(Ticket ticket){
        ticketDao.saveOrUpdate(ticket);
    }

    @PUT @Path("{id}")
    @UnitOfWork
    public void amend(Ticket ticket){
        ticketDao.saveOrUpdate(ticket);
    }

    @DELETE
    @Path("{id}")
    @UnitOfWork
    public void delete(@PathParam("id") Long id){
        ticketDao.delete(id);
    }
}
