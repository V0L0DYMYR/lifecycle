package org.lifecycle.json;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.lifecycle.config.Config;
import org.lifecycle.config.GoogleAuthorization;
import org.lifecycle.domain.Ticket;

import static com.yammer.dropwizard.testing.JsonHelpers.*;

public class JsonTest {

    @Test
    public void jsonTicket() throws Exception{
        fromJson(jsonFixture("json/Ticket.json"), Ticket.class);
        asJson(new Ticket(null, "Title","desc", 1, null).withLabel("sprint_1"));
    }

    @Test
    public void jsonToConfig() throws Exception{
        Config config = fromJson(jsonFixture("conf/lifecycle.json"), Config.class);
        GoogleAuthorization google = config.getAuthorization().getGoogle();
        Assertions.assertThat(google.getOauth2Url()).isNotEmpty();
    }
}