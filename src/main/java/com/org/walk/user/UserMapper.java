package com.org.walk.user;

import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

@Mapper
public interface UserMapper extends EntityMapper<UserDto, UserEntity> {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

}
