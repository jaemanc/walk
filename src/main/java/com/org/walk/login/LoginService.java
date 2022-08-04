package com.org.walk.login;


import com.org.walk.user.UserDto;

public interface LoginService {

    String loginToJwt(UserDto userDto) throws Exception;

    String loginByEmail(UserDto userDto) throws Exception;

    LoginDto postLoginUser(UserDto userdto) throws Exception;

}
