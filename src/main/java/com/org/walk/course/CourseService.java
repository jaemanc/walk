package com.org.walk.course;


import com.org.walk.course.dto.CourseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseService {

    List<CourseDto> getCourseList(String keyword, Pageable pageable) throws Exception;

    @Transactional
    CourseDto getCourse(long id) throws Exception;

    CourseDto postCourse(CourseDto courseDto) throws Exception;

    void putCourse(CourseDto courseDto) throws Exception;

    void deleteCourse(long id) throws Exception;



}
