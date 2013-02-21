package org.lifecycle.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.lifecycle.Utils.isNotEmpty;

public class SecureTokenFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(SecureTokenFilter.class);
    private final String securityTokenName;

    public SecureTokenFilter(String securityTokenName){
        this.securityTokenName = checkNotNull(securityTokenName);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String securityToken = getSecurityToken((HttpServletRequest) request);

        if (isNotEmpty(securityToken))
            setSecurityToken((HttpServletResponse) response, securityToken);
        LOG.info("Response with SecureToken:" + securityToken);
        filterChain.doFilter(request, response);
    }

    private void setSecurityToken(HttpServletResponse response, String secureToken) {
        response.addCookie(new Cookie(securityTokenName, secureToken));
    }

    private String getSecurityToken(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (securityTokenName.equals(cookie.getName()))
                return cookie.getValue();
        }
        return "";
    }

}
