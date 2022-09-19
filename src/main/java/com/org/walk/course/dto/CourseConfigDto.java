package com.org.walk.course.dto;

import lombok.*;

@Getter
@Setter
public class CourseConfigDto {

    private String clientId;

    private String clientSecret;

    private String userHomePath;

    private String endPoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String region;

    private String path;

    public CourseConfigDto(String clientId, String clientSecret, String userHomePath
            , String endPoint, String accessKey, String secretKey
            , String bucketName, String region, String path) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userHomePath = System.getProperty("user.home");
        this.endPoint = endPoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        this.region = region;
        this.path = path;
    }

    public CourseConfigDto() {
        this.userHomePath = System.getProperty("user.home");
    }

}
