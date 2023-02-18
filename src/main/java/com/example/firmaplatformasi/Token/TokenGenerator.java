package com.example.firmaplatformasi.Token;


import com.example.firmaplatformasi.Entity.Lavozim;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TokenGenerator {
    String parol="0000";
    public String token(String username, Lavozim lavozim){


        long vaqt=24*24*60*100;
        Date muddat=new Date(System.currentTimeMillis()+vaqt);

        String tokenn = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(muddat)
                .claim("roles",lavozim.getLavozimNomi())
                .signWith(SignatureAlgorithm.HS512, parol)
                .compact();

        return  tokenn;
    }

    public boolean tokenCheck(String token){
        Jwts
                .parser()
                .setSigningKey(parol)
                .parseClaimsJws(token);
        return true;
    }

    public String getUsername(String token){
        String username = Jwts
                .parser()
                .setSigningKey(parol)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }
}
