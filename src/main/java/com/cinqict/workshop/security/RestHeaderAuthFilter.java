package com.cinqict.workshop.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Optional;

public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    private static final String API_SECRET_HEADER = "Api-Secret";
    private static final String API_KEY_HEADER = "Api-Key";

    public RestHeaderAuthFilter(final RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) {
        doFilterImpl((HttpServletRequest) req, (HttpServletResponse) res, chain);
    }

    @SneakyThrows
    private void doFilterImpl(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) {
        try {
            Optional.ofNullable(attemptAuthentication(request, response))
                    .ifPresentOrElse(
                            authResult -> successfulAuthentication(request, response, chain, authResult),
                            () -> nextInChain(request, response, chain)
                    );
        } catch (final AuthenticationException e) {
            unsuccessfulAuthentication(request, response, e);
        }
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        final String username = StringUtils.hasText(request.getHeader(API_SECRET_HEADER)) ? request.getHeader(API_SECRET_HEADER) : "";
        final String password = StringUtils.hasText(request.getHeader(API_KEY_HEADER)) ? request.getHeader(API_KEY_HEADER) : "";

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        return (StringUtils.hasText(username)) ? getAuthenticationManager().authenticate(token) : null;
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) {
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed) throws IOException {
        SecurityContextHolder.clearContext();
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @SneakyThrows
    private void nextInChain(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) {
        chain.doFilter(request, response);
    }
}
