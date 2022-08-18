package com.org.walk.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.user.dto.UserDto;
import com.org.walk.user.UserService;
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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


}
