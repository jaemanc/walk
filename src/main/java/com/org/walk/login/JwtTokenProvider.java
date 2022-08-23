package com.org.walk.login;

import com.org.walk.user.UserService;
import com.org.walk.user.UserServiceImpl;
import com.org.walk.user.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Component
@Service
public class JwtTokenProvider {

    @Autowired
    UserServiceImpl userService;


    private String secretKey = "Son_of_iksan";

    // 토큰 유효시간 60분
    private long tokenValidTime = 60 * 60 * 1000L;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(UserDto userDto) {

        Claims claims = Jwts.claims().setSubject(userDto.getName()); // JWT payload 에 저장되는 정보단위
        // claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();

        claims.put("id", userDto.getUserId());
        claims.put("name",userDto.getName());

        Date now = new Date();

        String jwt = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return jwt;
    }

    public String renewalJwt(String jwt) throws Exception {

        Object id = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).parseClaimsJws(jwt).getBody().get("id");

        String stringToConvert = String.valueOf(id);
        Long convertedLong = Long.parseLong(stringToConvert);
        long _id = convertedLong;

        UserDto userDto = userService.getUser(_id);

        Claims claims = Jwts.claims().setSubject(userDto.getName()); // JWT payload 에 저장되는 정보단위
        // claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        claims.put("id", _id);
        claims.put("name",userDto.getName());

        Date now = new Date();
        String renewalJwt = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return renewalJwt;
    }

    // 토큰에서 회원 정보 추출
    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public long getUserId(String token) {
        Object id = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().get("id");

        String stringToConvert = String.valueOf(id);
        Long convertedLong = Long.parseLong(stringToConvert);
        long _id = convertedLong;

        return _id;
    }


    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {

        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}