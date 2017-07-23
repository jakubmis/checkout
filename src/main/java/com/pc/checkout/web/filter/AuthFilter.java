package com.pc.checkout.web.filter;

import com.pc.checkout.domain.IAuthenticationDomain;
import com.pc.checkout.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mis on 2017-07-22.
 */
@Component
public class AuthFilter extends GenericFilterBean {

    private IAuthenticationDomain iAuthenticationDomain;

    @Autowired
    public AuthFilter(IAuthenticationDomain iAuthenticationDomain) {
        super();
        this.iAuthenticationDomain = iAuthenticationDomain;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        HttpServletResponse response = ((HttpServletResponse) servletResponse);

        String authHeader = request.getHeader("Authorization");
        String requestUrl = request.getRequestURI();
        boolean api = requestUrl.startsWith("/checkout");
        boolean authenticated = iAuthenticationDomain.containsToken(new Token(authHeader));

        if (api && !authenticated) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized");
            response.getWriter().flush();
            response.getWriter().close();
        }

        filterChain.doFilter(request, response);

    }
}
