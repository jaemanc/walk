package com.org.walk.user;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        UserEntity userEntity = userRepository.getUserByUserId(id);
        UserDto user = UserMapper.mapper.toDto(userEntity);

        return user;
    }

    @Override
    public UserDto postUser(UserDto userDto) throws Exception {

        UserEntity userEntity = UserMapper.mapper.toEntity(userDto);

        System.out.println(" user entity내부에서 file 의 id를 따로 조회하는듯..? " + new ObjectMapper().writeValueAsString(userEntity));

        userRepository.save(userEntity);

        return userDto = UserMapper.mapper.toDto(userEntity);

    }

    @Override
    public void putUser(UserDto userDto) throws Exception {

        Optional<UserEntity> result = userRepository.findById(userDto.getUserId());

        if (result.isPresent()){

            UserEntity userEntity = result.get();
            userEntity.updateUser(userDto);

            userRepository.save(userEntity);
        }

    }

    @Override
    public void deleteUser(UserDto userDto) throws Exception {

        userRepository.deleteById(userDto.getUserId());

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

    @Override
    public List<UserDto> getUserFiles(long id) throws Exception {

        List<UserEntity> userEntities = userRepositoryCustom.getUserFiles(id);

        List<UserDto> userDtos = UserMapper.mapper.toDtoList(userEntities);

        return userDtos;
    }
}
