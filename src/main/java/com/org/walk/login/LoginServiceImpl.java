package com.org.walk.login;

import com.org.walk.user.UserDto;
import com.org.walk.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(UserDto userDto) throws Exception {

        UserDto user = userService.getUser(userDto.getUserId());

        if (ObjectUtils.isEmpty(user)) {
            return null;
        }

        String jwt = "";

        if (ObjectUtils.isEmpty(user)) {
            return null;
        }

        jwt = jwtTokenProvider.createToken(user);

        return jwt;
    }

    @Override
    public String loginByEmail(UserDto userDto) throws Exception {
        UserDto user = userService.getUserByEmail(userDto.getEmail());
        String jwt = "";

        if (ObjectUtils.isEmpty(user)) {
            return null;
        }

        jwt = jwtTokenProvider.createToken(user);

        return jwt;

    }
}
