package org.lifecycle.resource;

import com.yammer.metrics.annotation.Timed;
import org.lifecycle.domain.Ticket;
import org.lifecycle.persistence.TicketDao;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ticket")
@Produces(MediaType.APPLICATION_JSON)
public class TicketResource {

    private TicketDao ticketDao;

    public TicketResource(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @GET
    @Timed
    public List<Ticket> get(){
        return ticketDao.findAll();
    }

    @POST
    public void save(Ticket ticket){
        ticketDao.save(ticket);
    }
}
