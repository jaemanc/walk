package com.org.walk.file.mapper;

import com.org.walk.file.FileEntity;
import com.org.walk.file.dto.FileDto;
import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileMapper extends EntityMapper<FileDto, FileEntity> {

    FileMapper mapper = Mappers.getMapper(FileMapper.class);

}