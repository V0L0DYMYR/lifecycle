package org.lifecycle.service;

import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.lifecycle.config.Config;
import org.lifecycle.resource.TicketResource;

public class Service extends com.yammer.dropwizard.Service<Config> {

    public static void main(String[] arg) throws Exception {
        new Service().run(arg);
    }

    @Override
    public void initialize(Bootstrap<Config> configBootstrap) {
        configBootstrap.setName("/");
    }

    @Override
    public void run(Config config, Environment environment) throws Exception {
             environment.addResource(TicketResource.class);
    }
}
