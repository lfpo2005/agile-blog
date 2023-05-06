package dev.fernando.agileblog.configs.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContentSecurityPolicyFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'; "
                + "img-src 'self'; style-src 'self'; font-src 'self';");

        chain.doFilter(request, response);
    }
}
