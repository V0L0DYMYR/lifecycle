package org.lifecycle.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

import javax.validation.constraints.NotNull;

public class Config extends Configuration {

   // @NotNull
    @JsonProperty
    private String dbUrl;

    public String getDbUrl() {
        return dbUrl;
    }
}
