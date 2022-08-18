package com.org.walk;

import com.org.walk.course.*;
import com.org.walk.course.dto.CourseDto;
import com.org.walk.course.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 자동 롤백 됨.
public class CourseDummy {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void postCourseTest() {

        List<CourseEntity> courseEntity = new ArrayList<>();

        try {
            for ( int i=0; i < 100; i++ ) {
                CourseDto courseDto = new CourseDto();
                courseDto.setCourseId(i);
                courseDto.setCourseName("courseName_"+i);
                courseDto.setCourseKeyword("courseKeyWord_"+i);
                courseDto.setIsDeleted('N');
                courseDto.setUserId(11);

                courseEntity.add(CourseMapper.mapper.toEntity(courseDto));

                System.out.println("뭑시여..?"+courseEntity.toString());
            }

            courseRepository.saveAll(courseEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}