package com.org.walk.course;

import com.org.walk.file.QFileEntity;
import com.org.walk.user.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.org.walk.course.QCourseEntity;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QUserEntity qUserEntity = QUserEntity.userEntity;
    private final QCoordinatesEntity qCoordinatesEntity = QCoordinatesEntity.coordinatesEntity;
    private final QCourseEntity qCourseEntity = QCourseEntity.courseEntity;
    private final QFileEntity qFileEntity = QFileEntity.fileEntity;

    public List<CourseDto> searchCourseByKeywordPaging(String keyword, Pageable pageable) {

        QCourseEntity courseEntity = QCourseEntity.courseEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(keyword)) {
            builder.or(courseEntity.courseName.containsIgnoreCase(keyword));
            builder.or(courseEntity.courseKeyword.containsIgnoreCase(keyword));
        }

        return queryFactory.
                select(new QCourseDto(
                        courseEntity.courseId
                        ,courseEntity.coordinates_id
                        ,courseEntity.courseName
                        ,courseEntity.courseKeyword
                        ,courseEntity.userId
                        ,courseEntity.isDeleted
                        ,courseEntity.updater
                        ,courseEntity.updatedAt
                ))
                .from(courseEntity)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }


}
