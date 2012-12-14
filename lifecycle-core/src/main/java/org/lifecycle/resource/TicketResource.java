package org.lifecycle.resource;

import com.yammer.metrics.annotation.Timed;
import org.lifecycle.domain.Ticket;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ticket")
@Produces(MediaType.APPLICATION_JSON)
public class TicketResource {

    @GET
    @Timed
    public Ticket getTicket(){
        return new Ticket(10L, "my first description");
    }
}
