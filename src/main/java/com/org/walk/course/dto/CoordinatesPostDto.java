package com.org.walk.course.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoordinatesPostDto {

    private long coordinatesId;

    private String startLatitude;

    private String startLongitude;

    private String destLatitude;

    private String destLongitude;

    private String stopoverLatitude;

    private String stopoverLongitude;

    private Long requiredTime;

    private String transitRoute;

    private Long distance;


}
