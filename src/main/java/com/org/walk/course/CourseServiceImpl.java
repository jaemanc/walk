package com.org.walk.course;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepositoryCustom courseRepositoryCustom;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseDto> getCourseList(String keyword, Pageable pageable) throws Exception {

        List<CourseDto> courseList = courseRepositoryCustom.searchCourseByKeywordPaging(keyword,pageable);

        return courseList;
    }

    @Override
    public CourseDto getCourse(long id) throws Exception {
        return null;
    }

    @Override
    public CourseDto postCourse(CourseDto courseDto) throws Exception {

        CourseEntity courseEntity = CourseMapper.mapper.toEntity(courseDto);



        return null;
    }

    @Override
    public void putCourse(CourseDto courseDto) throws Exception {

    }

    @Override
    public void deleteCourse(long id) throws Exception {

    }
}
