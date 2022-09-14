package com.org.walk.course;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoordinateRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QCoordinatesEntity qCoordinatesEntity = QCoordinatesEntity.coordinatesEntity;
    private final QCourseEntity qCourseEntity = QCourseEntity.courseEntity;


}
