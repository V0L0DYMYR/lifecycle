package org.lifecycle.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Authorization {

    @JsonProperty
    private GoogleAuthorization google;

    public GoogleAuthorization getGoogle() {
        return google;
    }

    public void setGoogle(GoogleAuthorization google) {
        this.google = google;
    }
}
