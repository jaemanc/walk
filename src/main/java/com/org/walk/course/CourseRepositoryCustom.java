package com.org.walk.course;

import com.org.walk.course.dto.CourseDto;
import com.org.walk.course.dto.QCourseDto;
import com.org.walk.file.QFileEntity;
import com.org.walk.user.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
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

    public List<CourseDto> searchCourseByKeywordPaging(String searchType, String searchValue, String startDate, String endDate, Pageable pageable) {

        QCourseEntity courseEntity = QCourseEntity.courseEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if (!searchValue.equals("")) {
            if (searchType.equalsIgnoreCase("ALL") ) {
                builder.or(courseEntity.courseName.containsIgnoreCase(searchValue));
                builder.or(courseEntity.courseKeyword.containsIgnoreCase(searchValue));
            } else if (searchType.equalsIgnoreCase("KEYWORD")) {
                builder.or(courseEntity.courseKeyword.containsIgnoreCase(searchValue));
            } else if (searchType.equalsIgnoreCase("NAME")) {
                builder.or(courseEntity.courseName.containsIgnoreCase(searchValue));
            }
        }

        /*return queryFactory.
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
                .orderBy(courseEntity.createdAt.desc())
                .fetch();*/

        return queryFactory
                .select(Projections.fields(CourseDto.class
                    ,qCourseEntity.user.name.as("userName")
                    ,qCourseEntity.courseId
                    ,qCourseEntity.coordinates_id
                    ,qCourseEntity.courseName
                    ,qCourseEntity.courseKeyword
                    ,qCourseEntity.userId
                    ,qCourseEntity.isDeleted
                    ,qCourseEntity.updater
                    ,qCourseEntity.updatedAt
                ))
                .from(qCourseEntity)
                .join(qUserEntity).on(qCourseEntity.userId.eq(qUserEntity.userId))
                .fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(courseEntity.createdAt.desc())
                .fetch();

    }

    public CourseEntity getCourse(long courseId) {

            return queryFactory.
                select(qCourseEntity)
                .from(qCourseEntity)
                .leftJoin(qCourseEntity.coordinates, qCoordinatesEntity)
                .fetchJoin()
                .distinct()
                .where(qCourseEntity.courseId.eq(courseId))
                .fetchOne();
    }

}

