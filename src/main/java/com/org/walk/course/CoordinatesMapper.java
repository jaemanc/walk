package com.org.walk.course;

import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoordinatesMapper extends EntityMapper<CoordinatesDto, CoordinatesEntity> {

    CoordinatesMapper mapper = Mappers.getMapper(CoordinatesMapper.class);
}
