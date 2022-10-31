package com.org.walk.course.dto;

import com.org.walk.file.FileEntity;
import com.org.walk.user.UserEntity;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoursePostDto {

    private long courseId;

    private long coordinatesId;

    private String courseName;

    private String courseKeyword;

    private long userId;

    private Character isDeleted;

    private String updater;

    private Date updatedAt;

    private Long distance;

    private Long time;

    private CoordinatesPostDto coordinates;

}
