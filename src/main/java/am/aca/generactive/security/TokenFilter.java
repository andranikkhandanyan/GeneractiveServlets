package am.aca.generactive.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TokenFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = retrieveToken((HttpServletRequest) request);
        DecodedJWT decodedJWT = decodeJWT(token);

        Authentication authentication = getAuthentication(decodedJWT);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    public String retrieveToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader)) {
            throw new RuntimeException("Authorization header missing");
        }

        if (!authHeader.startsWith("Bearer")) {
            throw new RuntimeException("Wrong token Authorization header");
        }

        return authHeader.substring(7);
    }

    private DecodedJWT decodeJWT(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512("vNlPXxzlSTn3RT5uSxPQVvCdq2GZHZ8H8Y5R4Mdq"))
                .build()
                .verify(token);

        return jwt;
    }

    private Authentication getAuthentication(DecodedJWT jwt) throws UnsupportedEncodingException {
        String username = jwt.getSubject();

        String authoritiesClaim = jwt
                .getClaim("authorities").asString();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(authoritiesClaim.split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        AppUserDetails principal = new AppUserDetails(username, authorities);
        return new UsernamePasswordAuthenticationToken(principal, jwt.getToken(), authorities);
    }
}
