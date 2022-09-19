package com.org.walk.file.mapper;

import com.org.walk.file.FileEntity;
import com.org.walk.file.dto.FileDto;
import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper extends EntityMapper<FileDto, FileEntity> {

    FileMapper mapper = Mappers.getMapper(FileMapper.class);

}