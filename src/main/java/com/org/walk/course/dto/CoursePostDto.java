package com.org.walk.course.dto;

import com.org.walk.file.FileEntity;
import com.org.walk.user.UserEntity;
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
public class CoursePostDto {

    private long courseId;

    private long coordinates_id;

    private String courseName;

    private String courseKeyword;

    private long userId;

    private Character isDeleted;

    private String updater;

    private Date updatedAt;

    private CoordinatesDto coordinates;

}
