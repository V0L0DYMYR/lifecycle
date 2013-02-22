package org.lifecycle.service;

import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import org.lifecycle.UserInjector;
import org.lifecycle.config.Config;
import org.lifecycle.dao.TicketDao;
import org.lifecycle.dao.UserDao;
import org.lifecycle.domain.Project;
import org.lifecycle.domain.Ticket;
import org.lifecycle.domain.User;
import org.lifecycle.resource.TicketResource;
import org.lifecycle.security.GoogleOAuth2Resource;


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
        env.addResource(createOAuth2Resource(config));
        env.addProvider(new UserInjector(getUserDao(), config));
    }

    private GoogleOAuth2Resource createOAuth2Resource(Config config) {
        return new GoogleOAuth2Resource(getUserDao(), config.getAuthorization());
    }

    private UserDao getUserDao() {return new UserDao(hibernate.getSessionFactory());}

    public TicketResource createTicketResource() {
        final TicketDao ticketDao = new TicketDao(hibernate.getSessionFactory());
        return new TicketResource(ticketDao);
    }

    private final HibernateBundle<Config> hibernate = new HibernateBundle<Config>(Ticket.class, User.class, Project.class) {
        @Override
        public DatabaseConfiguration getDatabaseConfiguration(Config configuration) {
            return configuration.getDatabaseConfiguration();
        }
    };
}
