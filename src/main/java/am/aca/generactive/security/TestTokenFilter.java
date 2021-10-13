package am.aca.generactive.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
@Component
public class TestTokenFilter extends GenericFilterBean {
    private static final String AUTHORITIES_KEY = "auth";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = resolveToken(httpServletRequest);
//        if (StringUtils.hasText(jwt) && validateToken(jwt)) {
//            Authentication authentication = getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }













//    private Authentication getAuthentication(HttpServletRequest request, String token) throws UnsupportedEncodingException {
//        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SECRET))
//                .build()
//                .verify(token);
//
//        String username = jwt.getSubject();
//
//        String authoritiesClaim = jwt
//                .getClaim("auth").asString();
//
//        Collection<? extends GrantedAuthority> authorities = Arrays
//                .stream(authoritiesClaim.split(","))
//                .filter(auth -> !auth.trim().isEmpty())
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        User principal = new User(username, "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }

//    public String createToken(Authentication authentication, boolean rememberMe) {
//        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
//
//        long now = (new Date()).getTime();
//        Date validity = new Date(now + this.tokenValidityInMilliseconds);
//
//        return Jwts
//                .builder()
//                .setSubject(authentication.getName())
//                .claim(AUTHORITIES_KEY, authorities)
//                .signWith(key, SignatureAlgorithm.HS512)
//                .setExpiration(validity)
//                .compact();
//    }
}
