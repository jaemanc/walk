package com.org.walk.util;

import com.org.walk.login.JwtTokenProvider;
import com.org.walk.user.UserDto;
import com.org.walk.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwt = jwtTokenProvider.resolveToken(request);

        String user_nm = (String) jwtTokenProvider.getUserName(jwt);

        UserDto userDto = userService.getUserByName(user_nm);

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


}
