package com.org.walk.user;

import java.util.List;

public interface UserService {

    public boolean isUser(long id) throws Exception;

    public List<UserDto> getUserList(String keyword) throws Exception;

    public UserDto getUser(long id) throws Exception;

    public UserDto postUser(UserDto chiefDto) throws Exception;

    public void putUser(UserDto chiefDto) throws Exception;

    public void deleteUser(UserDto chiefDto) throws Exception;

    public UserDto getUserByName(String userName) throws Exception;

}
