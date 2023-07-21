package com.instagram.instagram.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtTokenGeneraterFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       if (shouldGenerateToken(request)) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) { // Check if authentication is NOT null
                SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());

                String jwt = Jwts.builder()
                        .setIssuer("instagram")
                        .setIssuedAt(new Date())
                        .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                        .claim("username", authentication.getName())
                        .setExpiration(new Date(new Date().getTime() + 300000000))
                        .signWith(key).compact();
                System.out.println("token----->"+jwt);
                response.setHeader(SecurityContext.header, jwt);
            }
        }
        filterChain.doFilter(request, response);
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authorities = new HashSet<>();
             for (GrantedAuthority authority : collection) {
            authorities.add(authority.getAuthority());
        }
        return String.join(",", authorities);
    }
    //true when the ServletPath of the request is not equal to "/signin" and false otherwise.
    protected boolean shouldGenerateToken(HttpServletRequest request) throws ServletException{
        return request.getServletPath().equals("/signin");
    }
}
