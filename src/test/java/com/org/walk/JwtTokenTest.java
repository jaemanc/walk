package com.org.walk;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class JwtTokenTest {

    public static void main(String[] args){

        String secretKey = "Son_of_iksan";

        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsImlkIjoxLCJuYW1lIjoidGVzdDEiLCJpYXQiOjE2NTc5NTQyMjIsImV4cCI6MTY1Nzk1NzgyMn0.7j_h6o2s6s-WaL1bcQ2omNAEWm96l83q1TMSOoHVFEQ";

        String value = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwt).getBody().getSubject();

        Object id = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwt).getBody().get("id");


        System.out.println( " id to long :: ");

        String stringToConvert = String.valueOf(id);
        Long convertedLong = Long.parseLong(stringToConvert);

        long iddd = convertedLong;

        System.out.println(iddd);


        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwt);

        System.out.println(claims.getBody().getExpiration().toString() + " @@@@@@" );

        System.out.println(id.toString());

        System.out.println(value);


    }

}
