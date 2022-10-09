package com.org.walk.course.dto;

import com.org.walk.file.FileEntity;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoordinatesDto {

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
