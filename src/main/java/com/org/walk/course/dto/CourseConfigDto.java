package com.org.walk.course.dto;

import lombok.*;

@Getter
@Setter
public class CourseConfigDto {

    private String clientId;

    private String clientSecret;

    private String userHomePath;

    public CourseConfigDto(String clientId, String clientSecret, String userHomePath) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userHomePath = System.getProperty("user.home");
    }

    public CourseConfigDto() {
        this.userHomePath = System.getProperty("user.home");
    }

}
