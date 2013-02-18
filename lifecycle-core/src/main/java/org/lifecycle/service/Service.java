package org.lifecycle.service;

import com.yammer.dropwizard.auth.oauth.OAuthProvider;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import org.lifecycle.config.Config;
import org.lifecycle.dao.UserDao;
import org.lifecycle.domain.Project;
import org.lifecycle.domain.User;
import org.lifecycle.resource.TicketResource;
import org.lifecycle.domain.Ticket;
import org.lifecycle.dao.TicketDao;
import org.lifecycle.security.OAuth2Resource;


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
        env.addProvider(new OAuthProvider<User>(null, null));
    }

    private OAuth2Resource createOAuth2Resource(Config config) {
        final UserDao userDao = new UserDao(hibernate.getSessionFactory());
        return new OAuth2Resource(userDao, config.getAuthorization());
    }

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
