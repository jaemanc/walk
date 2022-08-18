package com.org.walk.util;

import com.org.walk.user.UserServiceImpl;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.org.walk.login.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        if (StringUtils.isEmpty(jwt)) {
            System.out.println("The value of jwt in the request header is null... ");

            throw new IllegalAccessException(" jwt value cannot be null!!!");
        }

        long userId = jwtTokenProvider.getUserId(jwt);

        System.out.println(" header jwt : " + jwt + " user id : " + userId);

        // UserDto userDto = userService.getUser(userId);

        if (!userService.isUser(userId)) {
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
