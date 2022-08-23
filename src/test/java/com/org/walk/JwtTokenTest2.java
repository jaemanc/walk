package com.org.walk;

import io.jsonwebtoken.Jwts;

import java.util.Base64;

public class JwtTokenTest2 {
    public static void main(String[] args) {

        String secretKey = "Son_of_iksan";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXI1IiwiaWQiOjI0LCJuYW1lIjoidGVzdGVyNSIsImlhdCI6MTY2MTI0Mzg0NCwiZXhwIjoxNjYxMjQ3NDQ0fQ.98dXxd3OjJlAvQN8-dQo-UOgPRWOfxVfOT_v4c0ysmw";

        // 만들 때 두번 bytes를 걸어서 그런듯..?
        // Object id = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()).getBytes()).parseClaimsJws(token).getBody().get("id");
        Object id = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).parseClaimsJws(token).getBody().get("id");

        System.out.println(id.toString());

    }
}
