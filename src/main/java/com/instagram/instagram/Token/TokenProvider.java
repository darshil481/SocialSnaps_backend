package com.instagram.instagram.Token;

import com.instagram.instagram.config.SecurityContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class TokenProvider {
    public TokenClaims getClaimsFromToken(String token){
        SecretKey key= Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());
        Claims claims= Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
//        System.out.println("claims-> "+claims);
        String userName=String.valueOf(claims.get("username"));
//        System.out.println("userName-> "+userName);
        TokenClaims tokenClaims=new TokenClaims();
        tokenClaims.setUserName(userName);
//        System.out.println("tokenClaims-> "+tokenClaims);
        return tokenClaims;
    }
}
