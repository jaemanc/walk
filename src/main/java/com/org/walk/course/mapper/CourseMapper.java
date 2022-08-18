package com.org.walk.course.mapper;

import com.org.walk.course.CourseEntity;
import com.org.walk.course.dto.CourseDto;
import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper extends EntityMapper<CourseDto, CourseEntity> {

    CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

}
