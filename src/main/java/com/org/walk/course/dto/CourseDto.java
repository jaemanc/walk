package com.org.walk.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.org.walk.course.CoordinatesEntity;
import com.org.walk.file.FileEntity;
import com.org.walk.user.UserEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private long courseId;

    private long coordinatesId;

    private String courseName;

    private String courseKeyword;

    private long userId;

    private Character isDeleted;

    private String updater;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date updatedAt;

    private long fileId;

    private String userName;

    private UserEntity user;

    private CoordinatesDto coordinates;

    private Set<FileEntity> files;

    private File file;

    private String filePath;

    @QueryProjection
    public CourseDto(long courseId, long coordinatesId, String courseName, String courseKeyword, long userId, Character isDeleted, String updater, Date updatedAt) {
        this.courseId = courseId;
        this.coordinatesId = coordinatesId;
        this.courseName = courseName;
        this.courseKeyword = courseKeyword;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.updater = updater;
        this.updatedAt = updatedAt;
    }
}