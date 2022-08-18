package com.org.walk.user.mapper;

import com.org.walk.user.UserEntity;
import com.org.walk.user.dto.UserDto;
import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends EntityMapper<UserDto, UserEntity> {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

}
