package com.org.walk.course;


import com.org.walk.course.dto.CoordinatesDto;
import com.org.walk.course.dto.CourseDto;
import com.org.walk.course.dto.CoursePathDto;
import com.org.walk.course.dto.CoursePostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseService {

    List<CourseDto> getCourseList(String searchType, String searchValue, String startDate, String endDate, Pageable pageable) throws Exception;

    @Transactional
    CourseDto getCourse(long id) throws Exception;

    CourseDto postCourse(CoursePostDto courseDto) throws Exception;

    void putCourse(CourseDto courseDto) throws Exception;

    void deleteCourse(long id) throws Exception;

    String getDirectionsApi(String start, String goal, String option) throws Exception;

    CoursePathDto getWalkPathApi(String start, String goal) throws Exception;

    CoordinatesDto getCoordinates(Long id) throws Exception;

}