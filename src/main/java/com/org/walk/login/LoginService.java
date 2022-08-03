package com.org.walk.login;


import com.org.walk.user.UserDto;

public interface LoginService {

    String login(UserDto userDto) throws Exception;

    String loginByEmail(UserDto userDto) throws Exception;

}
