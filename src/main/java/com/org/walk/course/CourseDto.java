package com.org.walk.course;

import com.org.walk.file.FileEntity;
import com.org.walk.user.UserEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private long courseId;

    private long coordinates_id;

    private String courseName;

    private String courseKeyword;

    private long userId;

    private Character isDeleted;

    private String updater;

    private Date updatedAt;

    private UserEntity user;

    private CoordinatesEntity coordinates;

    private Set<FileEntity> files;

    @QueryProjection
    public CourseDto(long courseId, long coordinates_id, String courseName, String courseKeyword, long userId, Character isDeleted, String updater, Date updatedAt) {
        this.courseId = courseId;
        this.coordinates_id = coordinates_id;
        this.courseName = courseName;
        this.courseKeyword = courseKeyword;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.updater = updater;
        this.updatedAt = updatedAt;
    }
}