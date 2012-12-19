package org.lifecycle.resource;

import org.junit.Before;
import org.junit.Test;
import org.lifecycle.config.Config;
import org.lifecycle.service.Service;

import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

public class TicketResourceTest {

    @Before
    public void setUp() throws Exception{
        Config config = fromJson(jsonFixture("conf/lifecycle.json"), Config.class);
        Service service = new Service();

    }

    @Test
    public void saveTickets() {

    }
}
