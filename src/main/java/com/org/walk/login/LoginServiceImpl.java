package com.org.walk.login;

import com.org.walk.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositoryCustom userRepositoryCustom;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public String loginToJwt(UserDto userDto) throws Exception {

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

    @Override
    public LoginDto postLoginUser(UserDto userdto) throws Exception {

        userdto = userService.getUserByEmail(userdto.getEmail());
        userdto.setLoginYn('Y');

        UserEntity userEntity = UserMapper.mapper.toEntity(userdto);
        userRepository.saveAndFlush(userEntity);

        LoginDto loginDto = new LoginDto(userEntity.getUserId()
                ,userEntity.getName(),userEntity.getLoginYn()
                ,userEntity.getLastLogin());

        return loginDto;
    }
}
