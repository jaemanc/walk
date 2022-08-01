package com.org.walk.course;

import com.org.walk.file.FileEntity;
import com.org.walk.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private UserEntity user;

    private CoordinatesEntity coordinates;

    private Set<FileEntity> files;

}