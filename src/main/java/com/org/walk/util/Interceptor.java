package com.org.walk.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.org.walk.login.JwtTokenProvider;
import com.org.walk.user.UserDto;
import com.org.walk.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class Interceptor implements HandlerInterceptor {
    private Logger log_walk = LoggerFactory.getLogger("com.walk");

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(" interceptor check...!!");
        String jwt = jwtTokenProvider.resolveToken(request);

        System.out.println(" jwt : " + jwt);

        String userNm = (String) jwtTokenProvider.getUserName(jwt);

        System.out.println( " user name : " + userNm);

        UserDto userDto = userService.getUserByName(userNm);

        System.out.println(new ObjectMapper().writeValueAsString(userDto));

        if (ObjectUtils.isEmpty(userDto)) {
            System.out.println(" User Not found!!!  ");
            return false;
        }

        // 만료 시간 검증
        if ( jwtTokenProvider.validateToken(jwt)) {
            System.out.println(" token expired...");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(" post handle run... ");
    }

}
