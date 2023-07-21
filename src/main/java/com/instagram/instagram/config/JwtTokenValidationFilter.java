package com.instagram.instagram.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!shouldValidateToken(request)) {
            String jwt = request.getHeader(SecurityContext.header);
            if (jwt != null) {
                try {
                    jwt = jwt.substring(7);
                    SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());
                    logger.info(jwt);

                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key) // Use the key here
                            .build().parseClaimsJws(jwt)
                            .getBody();
                    String username = String.valueOf(claims.get("username")); // Get the username from the claims
                    String authorities = (String) claims.get("authorities");


                    List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                    Authentication auth = new UsernamePasswordAuthenticationToken(username, null, auths);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } catch (Exception e) {
                    throw new BadCredentialsException("Invalid token...!");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    protected boolean shouldValidateToken(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/signin");
    }
}
