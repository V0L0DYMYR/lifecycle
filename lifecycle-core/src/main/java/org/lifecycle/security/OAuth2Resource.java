package org.lifecycle.security;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.eclipse.jetty.util.ajax.JSON;
import org.lifecycle.config.Authorization;
import org.lifecycle.config.GoogleAuthorization;
import org.lifecycle.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/oauth2")
public class OAuth2Resource {

    private static final String ACCESS_TOKEN = "access_token";
    private Logger log =  LoggerFactory.getLogger(OAuth2Resource.class);
    private final Authorization config;
    private final GoogleAuthorization googleConfig;
    private final UserDao userDao;

    public OAuth2Resource(UserDao userDao, Authorization config){
        this.config = config;
        this.userDao = userDao;
        this.googleConfig = config.getGoogle();
    }

    @GET
    @Path("/google")
    public Response googleCallback(@QueryParam("state") String state, @QueryParam("code") String code){
        log.info("OAuth2 callback from Google - state:{}, code:{}", state, code);
        String token = requestForAccessToken(code);
        requestForUserInfo(token);
        return Response.ok().build();
    }

    private void requestForUserInfo(String token) {
        ClientResponse response = getWebResource(googleConfig.getUserInfoUrl())
                .queryParam(ACCESS_TOKEN, token)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        String userInfo = response.getEntity(String.class);
        log.info("User Info:"+userInfo);
    }

    private WebResource getWebResource(String url) {
        Client client = new Client().create();
        return client.resource(url);
    }

    private String requestForAccessToken(String googleAccessCode) {
        ClientResponse response = getWebResource(googleConfig.getOauth2Url())
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(ClientResponse.class, getGoogleParamsForAccessToken(googleAccessCode));
        return parseAccessToken(response.getEntity(String.class));
    }

    private MultivaluedMap<String, String> getGoogleParamsForAccessToken(String code) {
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("code", code);
        params.add("client_id", googleConfig.getClientId());
        params.add("client_secret", googleConfig.getClientSecret());
        params.add("redirect_uri", googleConfig.getRedirectUri());
        params.add("grant_type", googleConfig.getGrantType());
        return params;
    }

    private String parseAccessToken(String response) {
        log.info("Authorization response:"+response);
        Map<String, String> responseMap = (Map<String, String>) JSON.parse(response);
        String accessToken = responseMap.get(ACCESS_TOKEN);
        log.info("OAuth2 Google token:" + accessToken);
        return accessToken;
    }

}
