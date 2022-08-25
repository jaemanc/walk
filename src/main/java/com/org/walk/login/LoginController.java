package com.org.walk.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.user.dto.UserDto;
import com.org.walk.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

@Api(tags = {"LoginController"})
@Controller
@RequestMapping("/login")
public class LoginController {

    private final Logger log_error = LogManager.getLogger("com.error");
    private final Logger log_walk = LogManager.getLogger("com.walk");

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("")
    @ApiOperation(value = "user login", notes = "사용자 로그인")
    public ResponseEntity<LoginDto> login(
            @RequestBody UserDto userdto
    ) {

        String jwt = "";
        LoginDto loginDto = null;
        HttpHeaders httpHeaders;

        try {
            System.out.println( " 로그인 요청 값 : " + new ObjectMapper().writeValueAsString(userdto));
            jwt = loginService.loginByEmail(userdto);

            httpHeaders = new HttpHeaders();
            httpHeaders.add("authrozation", jwt);

            loginDto = loginService.postLoginUser(userdto);

        } catch (Exception e) {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pinrtStream = new PrintStream(out);
            e.printStackTrace(pinrtStream);
            System.out.println(out.toString());
            log_error.error(e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<LoginDto>(loginDto, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/verification")
    @ApiOperation(value = " jwt 갱신", notes = " 토큰 만료 시간 갱신")
    public ResponseEntity<String> renewalJwt(
            @RequestBody Map<String, String> jwtMap
    ) {
        String renewalJwt = "";
      String jwt = jwtMap.get("jwt");
        try {
            // 만료된 jwt는 interceptor 에서 걸러지기 때문에,
            // 만료 이전의 jwt만 갱신하여 새로 발급한다.
            renewalJwt = jwtTokenProvider.renewalJwt(jwt);
        } catch (ExpiredJwtException t ) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pinrtStream = new PrintStream(out);
            t.printStackTrace(pinrtStream);
            System.out.println(out.toString());
            log_error.error(t.getStackTrace());

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch ( Exception e) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pinrtStream = new PrintStream(out);
            e.printStackTrace(pinrtStream);
            log_error.error(e.getStackTrace());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(renewalJwt, HttpStatus.OK);
    }

}