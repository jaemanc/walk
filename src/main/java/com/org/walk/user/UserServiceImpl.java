package com.org.walk.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoryCustom userRepositoryCustom;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isUser(long id) throws Exception {

        return userRepositoryCustom.exist(id);

    }

    @Override
    public List<UserDto> getUserList(String keyword) throws Exception {

        List<UserDto> users = userRepositoryCustom.searchUserByKeyword(keyword);

        return users;
    }

    @Override
    public UserDto getUser(long id) throws Exception {

        UserEntity userEntity = userRepository.getUserById(id);

        UserDto user = UserMapper.mapper.toDto(userEntity);

        return user;
    }

    @Override
    public UserDto postUser(UserDto userDto) throws Exception {

        UserEntity userEntity = UserMapper.mapper.toEntity(userDto);

        userRepository.save(userEntity);

        return userDto = UserMapper.mapper.toDto(userEntity);

    }

    @Override
    public void putUser(UserDto userDto) throws Exception {

        Optional<UserEntity> result = userRepository.findById(userDto.getId());

        if (result.isPresent()){

            UserEntity userEntity = result.get();
            userEntity.updateUser(userDto);

            userRepository.save(userEntity);
        }

    }

    @Override
    public void deleteUser(UserDto userDto) throws Exception {

        userRepository.deleteById(userDto.getId());

    }

    @Override
    public UserDto getUserByName(String userName) throws Exception {
        UserEntity userEntity = userRepository.getUserByName(userName);

        if (ObjectUtils.isEmpty(userEntity)) {
            return null;
        }

        UserDto userDto = UserMapper.mapper.toDto(userEntity);

        return userDto;
    }
}
