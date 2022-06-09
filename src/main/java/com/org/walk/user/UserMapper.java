package com.org.walk.user;

import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends EntityMapper<UserDto, UserEntity> {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

}
