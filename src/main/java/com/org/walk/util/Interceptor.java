package com.org.walk.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.user.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.org.walk.login.JwtTokenProvider;
import com.org.walk.user.UserDto;
import com.org.walk.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class Interceptor implements HandlerInterceptor {

    private Logger log_walk = LoggerFactory.getLogger("com.walk");

    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Resource(name = "userServiceImpl")
    UserServiceImpl userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        System.out.println(" jwt value : " + jwt);

        long userId = jwtTokenProvider.getUserId(jwt);


        System.out.println(" header jwt : " + jwt + " user id : " + userId);

        UserDto userDto = userService.getUser(userId);

        if (ObjectUtils.isEmpty(userDto)) {
            System.out.println(" User Not found!!!  ");
            return false;
        }

        // 만료 시간 검증
        if ( !jwtTokenProvider.validateToken(jwt)) {
            System.out.println(" token expired...");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println(" post handle run... ");
    }

}
