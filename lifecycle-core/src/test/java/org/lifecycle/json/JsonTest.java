package org.lifecycle.json;

import org.junit.Test;
import org.lifecycle.domain.Label;
import org.lifecycle.domain.Ticket;

import java.util.HashSet;

import static com.yammer.dropwizard.testing.JsonHelpers.*;

public class JsonTest {

    @Test
    public void jsonTicket() throws Exception{
        fromJson(jsonFixture("json/Ticket.json"), Ticket.class);
        asJson(new Ticket(null, "Title", 1, new HashSet<Label>(){{add(new Label(1L, "sprint_1"));}}));
    }
}