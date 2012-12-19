package org.lifecycle.service;

import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import org.lifecycle.config.Config;
import org.lifecycle.domain.Ticket;
import org.lifecycle.persistence.TicketDao;
import org.lifecycle.resource.TicketResource;


public class Service extends com.yammer.dropwizard.Service<Config> {

    public static void main(String[] arg) throws Exception {
        new Service().run(arg);
    }

    @Override
    public void initialize(Bootstrap<Config> configBootstrap) {
        configBootstrap.setName("/");
        configBootstrap.addBundle(hibernate);
    }

    @Override
    public void run(Config config, Environment env) throws Exception {
        env.addResource(createTicketResource());

    }

    public TicketResource createTicketResource() {
        final TicketDao ticketDao = new TicketDao(hibernate.getSessionFactory());
        return new TicketResource(ticketDao);
    }

    private final HibernateBundle<Config> hibernate = new HibernateBundle<Config>(Ticket.class) {
        @Override
        public DatabaseConfiguration getDatabaseConfiguration(Config configuration) {
            return configuration.getDatabaseConfiguration();
        }
    };
}
