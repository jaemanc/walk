package com.org.walk.course;

import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper extends EntityMapper<CourseDto, CourseEntity> {

    CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

}
