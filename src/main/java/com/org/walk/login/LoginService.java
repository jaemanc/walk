package com.org.walk.login;


import com.org.walk.user.UserDto;

public interface LoginService {

    public String login(UserDto userDto) throws Exception;

    public String loginByEmail(UserDto userDto) throws Exception;

}
