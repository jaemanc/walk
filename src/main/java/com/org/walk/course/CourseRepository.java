package com.org.walk.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    CourseEntity getCourseEntityByCourseId(Long courseId);

}
