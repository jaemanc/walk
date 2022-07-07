package com.org.walk.file;

import com.org.walk.user.UserDto;
import com.org.walk.user.UserEntity;
import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileMapper extends EntityMapper<FileDto, FileEntity> {

    FileMapper mapper = Mappers.getMapper(FileMapper.class);

}