package com.example.springboot.common.utils;

import com.example.springboot.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {

    //过期时间
    @Value("${Jwt.time}")
    private String time;
    //密钥
    @Value("${Jwt.secret}")
    private String secret;
    //主题
    @Value("${Jwt.subject}")
    public String SUBJECT;

    public String createToken(User user) {

        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("userid", user.getUserid())
                .claim("role", user.getRole())
                .claim("perms", user.getPerms())
                .setSubject(SUBJECT)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(time)))
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return jwtToken;
    }

    public Claims checkToken(String token) {
        try {
            final Claims claims = Jwts.parser()
                                .setSigningKey(secret)
                                .parseClaimsJws(token)
                                .getBody();
            return claims;
            } catch (Exception e) {
            return null;
        }
    }
}
