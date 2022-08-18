package com.org.walk.user;

import com.org.walk.user.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    boolean isUser(long id) throws Exception;

    List<UserDto> getUserList(String keyword) throws Exception;

    @Transactional
    UserDto getUser(long id) throws Exception;

    UserDto getUserByEmail(String userEmail) throws Exception;

    UserDto postUser(UserDto chiefDto) throws Exception;

    void putUser(UserDto chiefDto) throws Exception;

    void deleteUser(UserDto chiefDto) throws Exception;

    UserDto getUserByName(String userName) throws Exception;

    List<UserDto> getUserFiles(long id) throws Exception;

    boolean validateUser(UserDto userdto) throws Exception;
}
